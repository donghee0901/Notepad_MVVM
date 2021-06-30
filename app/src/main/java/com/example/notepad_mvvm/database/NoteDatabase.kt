package com.example.notepad_mvvm.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notepad_mvvm.dataClass.NoteData

@Database(entities = [NoteData::class], version = 2, exportSchema = false)
@TypeConverters(FilterConverters::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getNoteDao() : NoteDao

    companion object{
        var database: NoteDatabase? = null
        fun getInstance(application: Application) : NoteDatabase{
            return database?: synchronized(this){
                val db = Room.databaseBuilder(application, NoteDatabase::class.java, "note_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                database = db
                return db
            }
        }
    }
}