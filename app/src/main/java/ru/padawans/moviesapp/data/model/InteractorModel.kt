package ru.padawans.moviesapp.data.model

import ru.padawans.moviesapp.data.model.trending.TrendingMovies
import ru.padawans.moviesapp.data.model.upcoming.UpcomingMovies

class InteractorModel(
    val upcomingMovies: UpcomingMovies,
    val nowPlayingMovies: UpcomingMovies,
    val trendingMovies: TrendingMovies

)

