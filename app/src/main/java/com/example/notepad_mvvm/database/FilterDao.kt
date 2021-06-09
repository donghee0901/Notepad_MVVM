package com.example.notepad_mvvm.database

import androidx.room.*
import com.example.notepad_mvvm.dataClass.FilterData
import kotlinx.coroutines.flow.Flow

@Dao
interface FilterDao {
    @Insert
    suspend fun insert(data : FilterData)
    @Update
    suspend fun update(data : FilterData)
    @Delete
    suspend fun delete(data : FilterData)
    @Query("Select * from filter_table")
    fun getData() : Flow<List<FilterData>>
}