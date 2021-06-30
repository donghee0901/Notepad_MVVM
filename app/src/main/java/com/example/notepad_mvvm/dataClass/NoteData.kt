package com.example.notepad_mvvm.dataClass

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "note_table")
data class  NoteData(
    var title : String,
    var context : String,
    var filter : List<FilterData>,
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null
) : Serializable
