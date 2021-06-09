package com.example.notepad_mvvm.dataClass

import androidx.annotation.ColorInt
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "filter_table")
data class FilterData(
    var tagName : String,
    @ColorInt var tagColor : Int,
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
) : Serializable
