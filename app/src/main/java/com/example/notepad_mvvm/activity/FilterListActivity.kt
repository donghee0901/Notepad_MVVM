package com.example.notepad_mvvm.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad_mvvm.R
import com.example.notepad_mvvm.adapter.FilterListAdapter
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
    private lateinit var adapter: FilterListAdapter
    private lateinit var dao : FilterDao
    private lateinit var filterRepository: FilterRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter_list)

        dao = FilterDatabase.getInstance(application).getFilterDao()
        filterRepository = FilterRepository(dao)
        viewModelFactory = FilterListActivityViewModelFactory(filterRepository)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(FilterListActivityViewModel::class.java)
        binding.apply {
            filterListViewModel = viewModel
            lifecycleOwner = this@FilterListActivity
        }

        adapter = FilterListAdapter(ArrayList())
        binding.addFilterView.adapter = adapter
        binding.addFilterView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewModel.filterData.observe(this) {
            adapter.setList(it)
        }

        binding.addTagButton.setOnClickListener {
            startActivity(Intent(this, AddFilterActivity::class.java))
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Adapter에 아이템 삭제 요청
                (binding.addFilterView.adapter as FilterListAdapter).removeItem(viewHolder.adapterPosition) { viewModel.deleteData(it) }
            }
        }).apply {
            // ItemTouchHelper에 RecyclerView 설정
            attachToRecyclerView(binding.addFilterView)
        }
    }
}