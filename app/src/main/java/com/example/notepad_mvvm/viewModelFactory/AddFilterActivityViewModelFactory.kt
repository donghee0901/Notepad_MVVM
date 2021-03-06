package com.example.notepad_mvvm.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notepad_mvvm.dataClass.FilterData
import com.example.notepad_mvvm.database.FilterDatabase
import com.example.notepad_mvvm.database.FilterRepository
import com.example.notepad_mvvm.viewModel.AddFilterActivityViewModel

class AddFilterActivityViewModelFactory(
    private val repository: FilterRepository,
    private val data: FilterData?
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddFilterActivityViewModel(repository, data) as T
    }
}