package com.example.noteapp

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.noteapp.data.AppDataBase

class App : Application() {

    companion object {
        @Volatile
        private var appDatabase: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return appDatabase ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "note.database"
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                appDatabase = instance
                instance
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        appDatabase = getInstance(this)
    }
}