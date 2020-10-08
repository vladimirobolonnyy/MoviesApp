package ru.padawans.moviesapp

import android.app.Application
import ru.padawans.moviesapp.di.AppComponent
import ru.padawans.moviesapp.di.AppModule
import ru.padawans.moviesapp.di.DaggerAppComponent
import ru.padawans.moviesapp.di.NetworkModule

class MovieApplication: Application() {

    companion object{
       lateinit var appComponent: AppComponent
    }


    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .build()

    }
}