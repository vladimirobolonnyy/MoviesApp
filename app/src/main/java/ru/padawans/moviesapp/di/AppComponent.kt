package ru.padawans.moviesapp.di

import dagger.Component
import ru.padawans.moviesapp.MovieApplication
import ru.padawans.moviesapp.ui.MainActivity
import ru.padawans.moviesapp.ui.view.MainFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,NetworkModule::class ])
interface AppComponent {
    fun inject(mainFragment: MainFragment)
}