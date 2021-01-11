package ru.padawans.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.padawans.domain.model.main.MovieGeneralInfo


interface MainMoviesRepository {
    fun getMainMovies(page:Int): Flow<List<MovieGeneralInfo>>
    var updateData:Boolean
}