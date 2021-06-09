package com.example.notepad_mvvm.database

import com.example.notepad_mvvm.dataClass.FilterData
import kotlinx.coroutines.flow.Flow

class FilterRepository(private val dao : FilterDao) {

    val filter : Flow<List<FilterData>> = dao.getData()

    suspend fun insertFilter(data : FilterData){
        dao.insert(data)
    }

    suspend fun updateFilter(data : FilterData){
        dao.update(data)
    }

    suspend fun deleteFilter(data : FilterData){
        dao.delete(data)
    }

}