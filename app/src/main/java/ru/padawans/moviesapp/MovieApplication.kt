package ru.padawans.moviesapp

import android.app.Application
import androidx.room.Room
import ru.padawans.data.di.DataModuleImpl
import ru.padawans.database.Storage
import ru.padawans.database.MovieDatabase
import ru.padawans.database.di.StorageModule
import ru.padawans.domain.di.DataProvider
import ru.padawans.domain.di.MainDependencyProvider


class MovieApplication: Application(),MainDependencyProvider {

    override fun onCreate() {
        super.onCreate()
        val db = Room.databaseBuilder(
            applicationContext,
            MovieDatabase::class.java, "database"
        ).build()

        StorageModule.storage = Storage(db)
    }

    override val getDataProvider: DataProvider
        get() = DataModuleImpl
}