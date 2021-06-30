package com.example.notepad_mvvm.adapter

import com.example.notepad_mvvm.dataClass.NoteData

interface NoteListAdapterEvent {
    fun itemClickEvent(noteData : NoteData)
    fun itemDeleteEvent(noteData : NoteData)
}