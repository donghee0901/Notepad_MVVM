package com.example.notepad_mvvm.database

import com.example.notepad_mvvm.dataClass.NoteData

class NoteRepository(private val dao : NoteDao) {

    val data = dao.getData()

    suspend fun insertNote(data : NoteData){
        dao.insert(data)
    }

    suspend fun updateNote(data : NoteData){
        dao.update(data)
    }

    suspend fun deleteNote(data : NoteData){
        dao.delete(data)
    }
}