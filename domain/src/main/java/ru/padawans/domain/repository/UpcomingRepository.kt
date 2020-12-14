package ru.padawans.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.padawans.domain.model.main.MovieGeneralInfo


interface UpcomingRepository {
    fun getUpcomingMovies(page:Int): Flow<List<MovieGeneralInfo>>
}