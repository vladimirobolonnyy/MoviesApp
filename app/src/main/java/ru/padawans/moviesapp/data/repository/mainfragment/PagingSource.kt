package ru.padawans.moviesapp.data.repository.mainfragment

import android.util.Log
import ru.padawans.moviesapp.data.Storage
import ru.padawans.moviesapp.data.api.MovieApi
import ru.padawans.moviesapp.data.cache.Cache
import ru.padawans.domain.model.main.MovieGeneralInfo
import ru.padawans.moviesapp.data.model.main.db.MovieGeneralInfoEntity
import ru.padawans.moviesapp.data.model.main.db.UpcomingMoviesEntity
import ru.padawans.moviesapp.data.model.main.dto.UpcomingMoviesDto
import ru.padawans.moviesapp.di.NetworkModule
import ru.padawans.moviesapp.di.StorageModule

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

        if (movies != null && movies.size > 0) {
            addDataToDatabase(
                response.convertToDB(contentType),
                (response.results!!.map { it.convertToEntity() })
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
            } else if (response.results.isNotEmpty()) {
                Log.d("TAG", "isDataInDatabaseValid: noo")
                storage.clear(contentType, page, database)

                addDataToDatabase(
                    response.convertToDB(contentType),
                    (response.results!!.map { it.convertToEntity() })
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