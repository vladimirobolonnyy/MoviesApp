package ru.padawans.domain.repository

import ru.padawans.domain.model.main.UpcomingMovies
import ru.padawans.domain.model.movie.cast.CastsModel
import ru.padawans.domain.model.movie.details.MovieDetails
import ru.padawans.domain.model.movie.reviews.MovieReviews
import ru.padawans.domain.model.movie.trailers.TrailerResponse

interface MovieInfoRepository {
    suspend fun getMovieTrailer(movieId:Int): TrailerResponse
    suspend fun getSimilar(movieId:Int,page:Int): UpcomingMovies
    suspend fun getReviews(movieID:Int,page: Int): MovieReviews
    suspend fun getDetails(movieId:Int): MovieDetails
    suspend fun getCasts(movieId:Int): CastsModel
}