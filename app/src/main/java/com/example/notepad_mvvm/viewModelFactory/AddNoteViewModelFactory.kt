package com.example.notepad_mvvm.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notepad_mvvm.dataClass.NoteData
import com.example.notepad_mvvm.database.NoteRepository
import com.example.notepad_mvvm.viewModel.AddNoteViewModel

class AddNoteViewModelFactory(private val noteRepository: NoteRepository, private val data : NoteData?) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddNoteViewModel(noteRepository, data) as T
    }
}