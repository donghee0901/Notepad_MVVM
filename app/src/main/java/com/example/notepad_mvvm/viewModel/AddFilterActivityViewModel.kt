package com.example.notepad_mvvm.viewModel

import android.app.Application
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notepad_mvvm.dataClass.FilterData
import com.example.notepad_mvvm.database.FilterDatabase
import com.example.notepad_mvvm.database.FilterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddFilterActivityViewModel(private val filterRepository: FilterRepository) : ViewModel() {
    val filterName = MutableLiveData<String>()
    private val _filterColor = MutableLiveData<Int>()
    val filterColor: LiveData<Int>
        get() = _filterColor

    init {
        filterName.value = ""
        _filterColor.value = Color.WHITE
    }

    fun changeFilterColor(color: Int) {
        _filterColor.value = color
    }

    fun saveFilterData() = viewModelScope.launch {
        filterRepository.insertFilter(FilterData(filterName.value!!, filterColor.value!!))
    }
}