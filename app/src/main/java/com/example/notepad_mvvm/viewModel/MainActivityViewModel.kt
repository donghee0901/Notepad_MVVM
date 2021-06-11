package com.example.notepad_mvvm.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notepad_mvvm.dataClass.FilterData

class MainActivityViewModel : ViewModel() {
    private val addFilterButton = FilterData("추가버튼", -1, -1)
    private val _selectFilter = MutableLiveData<ArrayList<FilterData>>()
    val selectFilter: LiveData<ArrayList<FilterData>>
        get() = _selectFilter

    private val currentData = arrayListOf(addFilterButton)

    init {
        _selectFilter.value = arrayListOf(addFilterButton)
    }

    fun deleteSelectFilter(data : FilterData){
        Log.d("TAG", "deleteSelectFilter: $currentData")
        currentData.remove(data)
        Log.d("TAG", "deleteSelectFilter: $currentData")
        _selectFilter.value = currentData
    }

    fun addSelectFilter(data : FilterData){
        Log.d("TAG", "addSelectFilter: $currentData")
        currentData.removeAt(currentData.lastIndex)
        currentData.addAll(arrayListOf(data, addFilterButton))
        Log.d("TAG", "addSelectFilter: $currentData")
        _selectFilter.value = currentData
    }
}