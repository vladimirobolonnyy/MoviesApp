package ru.padawans.moviesapp.data.repository.mainFragment

import android.util.Log
import ru.padawans.moviesapp.data.Storage
import ru.padawans.moviesapp.data.api.MovieApi
import ru.padawans.moviesapp.data.cache.Cache
import ru.padawans.moviesapp.data.model.upcoming.MovieGeneralInfo
import ru.padawans.moviesapp.data.model.upcoming.db.MovieGeneralInfoEntity
import ru.padawans.moviesapp.data.model.upcoming.db.UpcomingMoviesEntity
import ru.padawans.moviesapp.data.model.upcoming.dto.DatesDto
import ru.padawans.moviesapp.data.model.upcoming.dto.UpcomingMoviesDto
import ru.padawans.moviesapp.di.NetworkModule
import ru.padawans.moviesapp.di.StorageModule
import java.lang.Exception

class PagingSource(
    val contentType: String, ) {


    private val movieApi: MovieApi = NetworkModule.api
    private val storage: Storage = StorageModule.storage


        var isDataFromDatabase = false
        var isResponseSuccessful = true



    suspend fun getResponseUpcomingData(page: Int): List<MovieGeneralInfo> {
        val cache = Cache.get(contentType + page)
        val database = getDataFromDatabase(contentType, page).map { it.convert() }
        if (cache != null) {
            Log.d("TAG", "loadUpcomingMovies: from cache")
            return (cache as List<MovieGeneralInfo>?)!!
        } else if (database.size != 0) {
            isDataFromDatabase = true
            Log.d("TAG", "loadUpcomingMovies: from database")
            return database
        } else {
            isDataFromDatabase = false
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
            } else {
                Log.d("TAG", "isDataInDatabaseValid: noo")
                storage.clear(contentType, page, database)
                addDataToDatabase(
                    response.convertToDB(contentType),
                    (response.results!!.map { it.convertToEntity() })
                )
                return movies
            }
        } else {
            return database.map { it.convert() }
        }
    }

    private suspend fun getResponse(page: Int): UpcomingMoviesDto {
        return try {
            isResponseSuccessful = true
            when (contentType) {
                ContentTypes.UPCOMING -> {
                      movieApi.getUpcomingMovies("en-us", page/*,"DE"*/)
                }
                ContentTypes.TRENDING -> {
                    Log.d("TAG", "getTRENDINGResponse: ")
                      movieApi.getTrendingMovie(page)
                }
                else -> {
                    movieApi.getNowPlayingMovies("en-us", page)
                }
            }

        } catch (e: Exception) {
            if (isResponseSuccessful) {
                isResponseSuccessful = false
            }
            UpcomingMoviesDto(emptyList(), 0, 0, DatesDto("", ""), 0)
        }
    }


}