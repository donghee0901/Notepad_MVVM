package com.example.notepad_mvvm.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.notepad_mvvm.ItemTouchHelperManager.registerSwiper
import com.example.notepad_mvvm.adapter.FilterListAdapterEvent
import com.example.notepad_mvvm.adapter.FilterListAdapter
import com.example.notepad_mvvm.dataClass.FilterData
import com.example.notepad_mvvm.database.FilterDao
import com.example.notepad_mvvm.database.FilterDatabase
import com.example.notepad_mvvm.database.FilterRepository
import com.example.notepad_mvvm.viewModel.FilterListActivityViewModel
import com.example.notepad_mvvm.databinding.ActivityFilterListBinding
import com.example.notepad_mvvm.viewModelFactory.FilterListActivityViewModelFactory

class FilterListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilterListBinding
    private lateinit var viewModel: FilterListActivityViewModel
    private lateinit var viewModelFactory: FilterListActivityViewModelFactory
    private lateinit var filterAdapter: FilterListAdapter
    private lateinit var dao : FilterDao
    private lateinit var filterRepository: FilterRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterListBinding.inflate(layoutInflater)

        dao = FilterDatabase.getInstance(application).getFilterDao()
        filterRepository = FilterRepository(dao)
        viewModelFactory = FilterListActivityViewModelFactory(filterRepository)

        viewModel =
            ViewModelProvider(this, viewModelFactory).get(FilterListActivityViewModel::class.java)

        binding.apply {
            filterListViewModel = viewModel
            lifecycleOwner = this@FilterListActivity
        }

        val event = object : FilterListAdapterEvent {
            override fun itemClickEvent(filterData: FilterData) {
                val intent = Intent(this@FilterListActivity, AddFilterActivity::class.java).apply {
                    putExtra("selectData", filterData)
                }
                setResult(RESULT_OK, intent)
                finish()
            }
        }

        filterAdapter = FilterListAdapter(event)
        binding.addFilterView.adapter = filterAdapter

        viewModel.filterData.observe(this) {
            filterAdapter.list.submitList(it)
        }

        binding.addTagButton.setOnClickListener {
            startActivity(Intent(this, AddFilterActivity::class.java))
        }

        binding.addFilterView.registerSwiper{ viewHolder, _ ->
            viewModel.deleteData(filterAdapter.list.currentList[viewHolder.adapterPosition])
        }
        setContentView(binding.root)
    }
}