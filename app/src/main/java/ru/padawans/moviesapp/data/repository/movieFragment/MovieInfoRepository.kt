package ru.padawans.moviesapp.data.repository.movieFragment

import kotlinx.coroutines.flow.Flow
import ru.padawans.moviesapp.data.model.main.UpcomingMovies
import ru.padawans.moviesapp.data.model.movie.cast.CastsModel
import ru.padawans.moviesapp.data.model.movie.details.MovieDetails
import ru.padawans.moviesapp.data.model.movie.dto.trailers.TrailerResponseDto
import ru.padawans.moviesapp.data.model.movie.reviews.MovieReviews
import ru.padawans.moviesapp.data.model.movie.trailers.TrailerResponse

interface MovieInfoRepository {
    suspend fun getMovieTrailer(movieId:Int): TrailerResponse
    suspend fun getSimilar(movieId:Int,page:Int): UpcomingMovies
    suspend fun getReviews(movieID:Int,page: Int): MovieReviews
    suspend fun getDetails(movieId:Int): MovieDetails
    suspend fun getCasts(movieId:Int): CastsModel
}