package com.example.notepad_mvvm.viewModel

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notepad_mvvm.dataClass.FilterData
import com.example.notepad_mvvm.database.FilterRepository
import kotlinx.coroutines.launch

class AddFilterActivityViewModel(
    private val filterRepository: FilterRepository,
    data: FilterData?
) : ViewModel() {
    var isUpdate = false
    var formatted = FilterData("", Color.WHITE)
    val filterColorHex = MutableLiveData<String>()
    val filterName = MutableLiveData<String>()
    private val _filterColor = MutableLiveData<Int>()
    val filterColor: LiveData<Int>
        get() = _filterColor

    init {
        data?.let {
            formatted = data
            filterColorHex.value = "#${Integer.toHexString(data.tagColor).substring(2)}"
            isUpdate = true
        }

        filterName.value = formatted.tagName
        _filterColor.value = formatted.tagColor
    }

    fun changeFilterColor(color: Int) {
        _filterColor.value = color
    }

    fun saveFilterData() = viewModelScope.launch {
        val data = FilterData(filterName.value!!, filterColor.value!!)
        if(isUpdate){
            data.id = formatted.id
            filterRepository.updateFilter(data)
        }else{
            filterRepository.insertFilter(data)
        }

    }
}