package com.example.notepad_mvvm.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.notepad_mvvm.adapter.SelectFilterAdapter
import com.example.notepad_mvvm.adapter.SelectFilterAdapterEvent
import com.example.notepad_mvvm.dataClass.FilterData
import com.example.notepad_mvvm.viewModel.MainActivityViewModel
import com.example.notepad_mvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var selectFilterAdapter: SelectFilterAdapter

    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == RESULT_OK) {
            val resultData: FilterData = it.data?.extras?.get("selectData") as FilterData
            viewModel.addSelectFilter(resultData)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding.lifecycleOwner = this
        binding.mainViewModel = viewModel

        setAdapter()

        viewModel.selectFilter.observe(this) {
            selectFilterAdapter.list.submitList(it)
        }

        binding.addMemoButton.setOnClickListener {
            startActivity(Intent(this, FilterListActivity::class.java))
        }
    }

    private fun setAdapter() {
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
    }


}