package ru.padawans.moviesapp

import android.app.Application
import androidx.room.Room
import ru.padawans.moviesapp.data.Storage
import ru.padawans.moviesapp.data.database.MovieDatabase
import ru.padawans.moviesapp.di.StorageModule


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