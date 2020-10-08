package ru.padawans.moviesapp.di

import dagger.Module
import dagger.Provides
import ru.padawans.moviesapp.MovieApplication
import javax.inject.Singleton

@Module
class AppModule {
    private lateinit var application: MovieApplication

    constructor(application: MovieApplication) {
        this.application = application
    }

    @Provides
    @Singleton
    fun getApplication():MovieApplication{
        return application
    }
}