package ru.padawans.data.main

import android.util.Log
import ru.padawans.database.Storage
import ru.padawans.network.api.MovieApi
import ru.padawans.database.cache.Cache
import ru.padawans.domain.model.main.MovieGeneralInfo
import ru.padawans.database.model.main.MovieGeneralInfoEntity
import ru.padawans.database.model.main.UpcomingMoviesEntity
import ru.padawans.network.model.main.UpcomingMoviesDto
import ru.padawans.network.di.NetworkModule
import ru.padawans.database.di.StorageModule
import ru.padawans.domain.ContentTypes

class PagingSource(
    val contentType: String,
    private val movieApi: MovieApi = NetworkModule.api,
    private val storage: Storage = StorageModule.storage
) {

    fun dataFromCache(page: Int): List<MovieGeneralInfo>? {
        val cache = Cache.get(contentType + page)
        return cache as List<MovieGeneralInfo>?
    }

    fun dataFromDatabase(page: Int): List<MovieGeneralInfo> {
        val database = getDataFromDatabase(contentType, page).map { it.convert() }
        return if (database.size != 0) {
            database
        } else {
            emptyList()
        }
    }

    suspend fun dataFromServer(page: Int): List<MovieGeneralInfo> {
        val response = getResponse(page)
        val movies = response.results?.map { it.convert() }
        Log.d("TAG", "loadUpcomingMovies: from server")

        if (movies != null && movies.isNotEmpty()) {
            addDataToDatabase(
                UpcomingMoviesEntity(response.convert(),contentType),
                response.results!!.map { MovieGeneralInfoEntity(it.convert()) }
            )
            Cache.set(contentType + page, movies, 60)
        }
        return movies!!
    }


    private suspend fun addDataToDatabase(
        upcomingMoviesEntity: UpcomingMoviesEntity,
        movieGeneralInfoEntitys: List<MovieGeneralInfoEntity>
    ) {
        Log.d("TAG", "addDataToDatabase: ")
        storage.insertUpcomingMovies(
            upcomingMoviesEntity = upcomingMoviesEntity,
            movieGeneralInfoEntitys = movieGeneralInfoEntitys
        )
    }

    private fun getDataFromDatabase(contentType: String, page: Int): List<MovieGeneralInfoEntity> {
        return storage.getMovieGeneralInfo(contentType, page)
    }

    suspend fun isDataInDatabaseValid(page: Int): List<MovieGeneralInfo> {
        val response = getResponse(page)
        val movies = response.results?.map { it.convert() }
        val database = getDataFromDatabase(contentType, page)

        if (movies?.size!! > 0) {
            if (movies == database.map { it.convert() }) {
                Log.d("TAG", "isDataInDatabaseValid: yes")
                return emptyList()
            } else if (!response.results.isNullOrEmpty()) {
                Log.d("TAG", "isDataInDatabaseValid: noo")
                storage.clear(contentType, page, database)

                addDataToDatabase(
                    UpcomingMoviesEntity(response.convert(),contentType),
                    response.results!!.map { MovieGeneralInfoEntity(it.convert()) }
                )
                return movies
            } else {
                return database.map { it.convert() }
            }
        } else {
            return database.map { it.convert() }
        }
    }

    private suspend fun getResponse(page: Int): UpcomingMoviesDto {
        return when (contentType) {
            ContentTypes.UPCOMING -> {
                 movieApi.getUpcomingMovies("en-us", page)
            }
            ContentTypes.TRENDING -> {
                movieApi.getTrendingMovie(page)
            }
            else -> {
                movieApi.getNowPlayingMovies("en-us", page)
            }
        }
    }
}