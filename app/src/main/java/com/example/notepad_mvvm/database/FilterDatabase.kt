package com.example.notepad_mvvm.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notepad_mvvm.dataClass.FilterData

@Database(entities = [FilterData::class], version = 1, exportSchema = false)
abstract class FilterDatabase : RoomDatabase(){
    abstract fun getFilterDao() : FilterDao

    companion object{
        var database: FilterDatabase? = null
        fun getInstance(application: Application): FilterDatabase{
            return database ?: synchronized(this){
                val db = Room.databaseBuilder(application, FilterDatabase::class.java, "filter_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                database = db
                return db
            }
        }
    }
}