package com.example.notepad_mvvm.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notepad_mvvm.database.NoteRepository
import com.example.notepad_mvvm.viewModel.MainActivityViewModel

class MainActivityViewModelFactory(val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(repository) as T
    }
}