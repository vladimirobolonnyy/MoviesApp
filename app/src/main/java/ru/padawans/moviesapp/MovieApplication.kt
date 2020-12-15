package ru.padawans.moviesapp

import android.app.Application
import androidx.room.Room
import ru.padawans.database.Storage
import ru.padawans.database.MovieDatabase
import ru.padawans.database.di.StorageModule


class MovieApplication: Application() {


    override fun onCreate() {
        super.onCreate()
        val db = Room.databaseBuilder(
            applicationContext,
            MovieDatabase::class.java, "database"
        ).build()

        StorageModule.storage = Storage(db)
    }
}