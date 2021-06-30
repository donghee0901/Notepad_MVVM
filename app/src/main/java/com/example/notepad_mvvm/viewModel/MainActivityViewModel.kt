package com.example.notepad_mvvm.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.notepad_mvvm.dataClass.FilterData
import com.example.notepad_mvvm.dataClass.NoteData
import com.example.notepad_mvvm.database.NoteRepository
import kotlinx.coroutines.flow.collect

class MainActivityViewModel(private val repository: NoteRepository) : ViewModel() {
    private val addFilterButton = FilterData("추가버튼", -1, -1)
    private val _selectFilter = MutableLiveData<ArrayList<FilterData>>()
    val selectFilter: LiveData<ArrayList<FilterData>>
        get() = _selectFilter

    val currentData = arrayListOf(addFilterButton)

    val noteList = liveData {
        repository.data.collect{ emit(it) }
    }

    init {
        _selectFilter.value = arrayListOf(addFilterButton)
    }

    fun deleteSelectFilter(data: FilterData) {
        currentData.remove(data)
        _selectFilter.value = ArrayList(currentData)
    }

    fun addSelectFilter(data: FilterData) {
        currentData.removeAt(currentData.lastIndex)
        currentData.addAll(arrayListOf(data, addFilterButton))

        _selectFilter.value = ArrayList(currentData.distinctBy { it.id })
    }

    suspend fun deleteNote(data: NoteData) {
        repository.deleteNote(data)
    }
}