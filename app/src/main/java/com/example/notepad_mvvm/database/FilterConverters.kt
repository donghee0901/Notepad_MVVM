package com.example.notepad_mvvm.database

import androidx.room.TypeConverter
import com.example.notepad_mvvm.dataClass.FilterData
import com.google.gson.Gson

class FilterConverters {
    @TypeConverter
    fun ListToJson(data : List<FilterData>) : String = Gson().toJson(data)
    @TypeConverter
    fun JsonToList(data : String) : List<FilterData> = Gson().fromJson(data, Array<FilterData>::class.java).toList()
}