package com.example.notepad_mvvm.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.notepad_mvvm.R
import com.example.notepad_mvvm.adapter.FilterListAdapter
import com.example.notepad_mvvm.adapter.SelectFilterAdapter
import com.example.notepad_mvvm.adapter.SelectFilterAdapterEvent
import com.example.notepad_mvvm.dataClass.FilterData
import com.example.notepad_mvvm.dataClass.NoteData
import com.example.notepad_mvvm.database.NoteDatabase
import com.example.notepad_mvvm.database.NoteRepository
import com.example.notepad_mvvm.databinding.ActivityAddNoteBinding
import com.example.notepad_mvvm.viewModel.AddNoteViewModel
import com.example.notepad_mvvm.viewModelFactory.AddNoteViewModelFactory

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var viewModel: AddNoteViewModel
    private lateinit var selectFilterAdapter: SelectFilterAdapter

    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val resultData: FilterData = it.data?.extras?.get("selectData") as FilterData
            viewModel.addSelectFilter(resultData)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val noteDao = NoteDatabase.getInstance(application).getNoteDao()
        val noteRepository = NoteRepository(noteDao)

        val selectData: NoteData? = intent.extras?.get("selectData") as NoteData?

        val factory = AddNoteViewModelFactory(noteRepository, selectData)
        viewModel = ViewModelProvider(this, factory).get(AddNoteViewModel::class.java)

        binding.addNoteViewModel = viewModel
        binding.lifecycleOwner = this

        binding.saveMemoButton.setOnClickListener {
            viewModel.saveNoteData()
            finish()
        }

        setFilterAdapter()
        viewModel.selectFilter.observe(this) {
            selectFilterAdapter.list.submitList(it)
        }
    }

    private fun setFilterAdapter() {
        val event = object : SelectFilterAdapterEvent {

            override fun itemClickEvent(filterData: FilterData) {
                viewModel.deleteSelectFilter(filterData)
            }

            override fun addItemClickEvent() {
                val intent = Intent(this@AddNoteActivity, FilterListActivity::class.java)
                launcher.launch(intent)
            }

        }
        selectFilterAdapter = SelectFilterAdapter(event)
        binding.inputAddFilter.adapter = selectFilterAdapter
//        (binding.filter.itemAnimator) = null // 필터 애니메이션 없애기
    }
}