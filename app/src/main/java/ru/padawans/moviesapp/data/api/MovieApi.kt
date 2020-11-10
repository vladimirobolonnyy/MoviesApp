package ru.padawans.moviesapp.data.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.padawans.moviesapp.data.model.search.Dto.MovieSearchResponseDTO
import ru.padawans.moviesapp.data.model.upcoming.dto.UpcomingMoviesDto

interface MovieApi {

    @GET("3/movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String,
        @Query("page") pageCount: Int/*,
        @Query("region") region: String*/
    ): UpcomingMoviesDto

    @GET("3/movie/now_playing")
   suspend fun getNowPlayingMovies(
        @Query("language") language: String,
        @Query("page") pageCount: Int
    ): UpcomingMoviesDto

    @GET("3/trending/movie/week")
    suspend fun getTrendingMovie(
        @Query("page") pageCount: Int
    ): UpcomingMoviesDto

    @GET("3/search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 0
    ): MovieSearchResponseDTO

}