package ru.padawans.moviesapp.data.repository.movieFragment

import ru.padawans.moviesapp.data.api.MovieApi
import ru.padawans.moviesapp.data.cache.Cache
import ru.padawans.domain.model.main.UpcomingMovies
import ru.padawans.domain.model.movie.cast.CastsModel
import ru.padawans.domain.model.movie.details.MovieDetails
import ru.padawans.domain.model.movie.reviews.MovieReviews
import ru.padawans.domain.model.movie.trailers.TrailerResponse
import ru.padawans.domain.repository.MovieInfoRepository
import ru.padawans.moviesapp.di.NetworkModule

class MovieInfoRepositoryImpl(private val movieApi: MovieApi = NetworkModule.api) :
    MovieInfoRepository {

    override suspend fun getMovieTrailer(movieId: Int): TrailerResponse {
        val cache = Cache.get(MOVIE_TRAILER+movieId)
        if (cache!=null){
            return cache as TrailerResponse
        }else{
            val response = movieApi.getTrailer(movieId, "en-us").convert()
            Cache.set(MOVIE_TRAILER+movieId,response,360)
            return response
        }
    }

    override suspend fun getSimilar(movieId:Int, page:Int): UpcomingMovies {
        val cache = Cache.get(MOVIE_SIMILAR +movieId+page)
        if (cache!=null){
            return cache as UpcomingMovies
        }else{
            val response  = movieApi.getSimilarMovies(movieId,"en-us",page).convert()
            Cache.set(MOVIE_SIMILAR +movieId+page,response,360)
            return response
        }
    }

    override suspend fun getReviews(movieID: Int, page: Int): MovieReviews {
        val cache = Cache.get(MOVIE_REVIEWS + movieID+page)
        if (cache != null) {
            return cache as MovieReviews
        } else {
            val response = movieApi.getMovieReviews(movieID, "en-us", page).convert()
            Cache.set(MOVIE_REVIEWS + movieID+page, response, 360)
            return response
        }
    }

    override suspend fun getDetails(movieId: Int): MovieDetails {
        val cache = Cache.get(MOVIE_DETAILS + movieId)
        if (cache != null) {
            return cache as MovieDetails
        } else {
            val response =  movieApi.getMovieDetail(movieId,"en-us").convert()
            Cache.set(MOVIE_DETAILS +movieId,response,360)
            return response
        }
    }

    override suspend fun getCasts(movieId: Int): CastsModel {
        val cache = Cache.get(MOVIE_CASTS + movieId)
        if (cache != null) {
            return cache as CastsModel
        } else {
            val response = movieApi.getCasts(movieId, "en-us").convert()
            Cache.set(MOVIE_CASTS + movieId, response, 360)
            return response
        }
    }


    companion object{
        const val MOVIE_TRAILER = "MOVIE_TRAILER"
        const val MOVIE_SIMILAR = "MOVIE_SIMILAR"
        const val MOVIE_REVIEWS = "MOVIE_REVIEWS"
        const val MOVIE_DETAILS = "MOVIE_DETAILS"
        const val MOVIE_CASTS = "MOVIE_CASTS"
    }

}