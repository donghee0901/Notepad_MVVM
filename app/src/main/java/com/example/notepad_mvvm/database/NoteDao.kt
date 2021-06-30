package com.example.notepad_mvvm.database

import androidx.room.*
import com.example.notepad_mvvm.dataClass.NoteData
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao
{
    @Insert
    suspend fun insert(data : NoteData)
    @Update
    suspend fun update(data : NoteData)
    @Delete
    suspend fun delete(data : NoteData)
    @Query("select * from note_table")
    fun getData() : Flow<List<NoteData>>
}
