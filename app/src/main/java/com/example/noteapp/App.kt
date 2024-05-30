package com.example.noteapp

import android.app.Application
import androidx.room.Room
import com.example.noteapp.data.AppDataBase

class App : Application() {
    companion object {
        var appDatabase: AppDataBase? = null
    }

    override fun onCreate() {
        super.onCreate()
        getInstance()
    }

    fun getInstance(): AppDataBase? {
        if (appDatabase == null) {
            appDatabase = applicationContext?.let {
                Room.databaseBuilder(
                    it.applicationContext,
                    AppDataBase::class.java,
                    "note.database"
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
            }
        }
        return appDatabase
    }
}