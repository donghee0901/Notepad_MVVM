package com.example.notepad_mvvm.viewModel

import android.graphics.Color
import androidx.lifecycle.*
import com.example.notepad_mvvm.dataClass.FilterData
import com.example.notepad_mvvm.database.FilterRepository
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FilterListActivityViewModel(val filterRepository: FilterRepository) : ViewModel() {
    private val filterLiveData = MutableLiveData<List<FilterData>>()
    val filterData = liveData {
        filterRepository.filter.collect { emit(it) }
    }

    init {

    }

    fun deleteData(filterData: FilterData){
        viewModelScope.launch {
            filterRepository.deleteFilter(filterData)
        }
    }

}