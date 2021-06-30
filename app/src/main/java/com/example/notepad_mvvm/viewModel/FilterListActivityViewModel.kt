package com.example.notepad_mvvm.viewModel

import android.graphics.Color
import androidx.lifecycle.*
import com.example.notepad_mvvm.dataClass.FilterData
import com.example.notepad_mvvm.database.FilterRepository
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FilterListActivityViewModel(private val filterRepository: FilterRepository) : ViewModel() {
    val filterData = liveData {
        filterRepository.filter.collect { emit(it) }
    }

    fun deleteData(filterData: FilterData){
        viewModelScope.launch {
            filterRepository.deleteFilter(filterData)
        }
    }

}