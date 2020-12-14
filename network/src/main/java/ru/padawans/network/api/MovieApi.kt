package ru.padawans.network.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.padawans.network.model.movie.trailers.TrailerResponseDto
import ru.padawans.network.model.search.MovieSearchResponseDTO
import ru.padawans.network.model.main.UpcomingMoviesDto
import ru.padawans.network.model.movie.cast.CastResponseDto
import ru.padawans.network.model.movie.details.MovieDetailsDto
import ru.padawans.network.model.movie.reviews.MovieReviewsDto

interface MovieApi {

    @GET("3/movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String,
        @Query("page") pageCount: Int
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


    // MovieFragment
    @GET("3/movie/{movie_id}/videos")
    suspend fun getTrailer(
        @Path("movie_id") movieID:Int,
        @Query("language") language: String
    ): TrailerResponseDto

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieID:Int,
        @Query("language") language: String
    ):MovieDetailsDto

    @GET("3/movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieID:Int,
        @Query("language") language: String,
        @Query("page") pageCount: Int
    ):MovieReviewsDto

    @GET("3/movie/{movie_id}/similar")
    suspend fun  getSimilarMovies(
        @Path("movie_id") movieID:Int,
        @Query("language") language: String,
        @Query("page") pageCount: Int
    ): UpcomingMoviesDto

    @GET("3/movie/{movie_id}/credits")
    suspend fun getCasts(
        @Path("movie_id") movieID:Int,
        @Query("language") language: String
    ):CastResponseDto
}