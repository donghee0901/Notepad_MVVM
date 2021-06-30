package com.example.notepad_mvvm.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.notepad_mvvm.adapter.NoteListAdapter
import com.example.notepad_mvvm.adapter.NoteListAdapterEvent
import com.example.notepad_mvvm.adapter.SelectFilterAdapter
import com.example.notepad_mvvm.adapter.SelectFilterAdapterEvent
import com.example.notepad_mvvm.dataClass.FilterData
import com.example.notepad_mvvm.dataClass.NoteData
import com.example.notepad_mvvm.database.NoteDao
import com.example.notepad_mvvm.database.NoteDatabase
import com.example.notepad_mvvm.database.NoteRepository
import com.example.notepad_mvvm.viewModel.MainActivityViewModel
import com.example.notepad_mvvm.databinding.ActivityMainBinding
import com.example.notepad_mvvm.viewModelFactory.MainActivityViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var viewModelFactory: MainActivityViewModelFactory
    private lateinit var repository: NoteRepository
    private lateinit var dao: NoteDao
    private lateinit var selectFilterAdapter: SelectFilterAdapter
    private lateinit var noteListAdapter: NoteListAdapter

    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val resultData: FilterData = it.data?.extras?.get("selectData") as FilterData
            viewModel.addSelectFilter(resultData)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dao = NoteDatabase.getInstance(application).getNoteDao()
        repository = NoteRepository(dao)
        viewModelFactory = MainActivityViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
        binding.lifecycleOwner = this
        binding.mainViewModel = viewModel

        setAdapter()

        viewModel.selectFilter.observe(this) {
            selectFilterAdapter.list.submitList(it)
            viewModel.noteList.value?.let { noteData ->
                noteListAdapter.list.submitList(ArrayList())
                noteListAdapter.list.submitList(getFilterItemList(ArrayList(noteData), ArrayList(viewModel.currentData)))
            }
        }

        viewModel.noteList.observe(this) {
            noteListAdapter.list.submitList(ArrayList())
            noteListAdapter.list.submitList(getFilterItemList(ArrayList(it), ArrayList(viewModel.currentData)))
        }

        binding.addMemoButton.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }
    }

    private fun setAdapter() {
        setFilterAdapter()
        setNoteAdapter()
    }

    private fun setFilterAdapter() {
        val event = object : SelectFilterAdapterEvent {

            override fun itemClickEvent(filterData: FilterData) {
                viewModel.deleteSelectFilter(filterData)
            }

            override fun addItemClickEvent() {
                val intent = Intent(this@MainActivity, FilterListActivity::class.java)
                launcher.launch(intent)
            }

        }
        selectFilterAdapter = SelectFilterAdapter(event)
        binding.filter.adapter = selectFilterAdapter
//        (binding.filter.itemAnimator) = null // 필터 애니메이션 없애기
    }

    private fun setNoteAdapter() {
        val event = object : NoteListAdapterEvent {
            override fun itemClickEvent(noteData: NoteData) {
                val intent = Intent(this@MainActivity, AddNoteActivity::class.java).apply {
                    putExtra("selectData", noteData)
                }
                startActivity(intent)
            }
        }
        noteListAdapter = NoteListAdapter(event)
        binding.note.adapter = noteListAdapter
    }

    private fun getFilterItemList(
        noteData: ArrayList<NoteData>,
        filterData: ArrayList<FilterData>,
    ): List<NoteData> {
        filterData.removeAt(filterData.lastIndex)
        val list = noteData.filter { selectNoteData ->
            var isInData = true
            for (value in filterData.map { it.tagColor }) {
                isInData = selectNoteData.filter.map { it.tagColor }.contains(value)
                if (!isInData) {
                    break
                }
            }
            isInData
        }
        Log.d("LOG", list.toString())
        return list
    }
}