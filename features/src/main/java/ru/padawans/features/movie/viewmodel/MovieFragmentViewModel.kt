package ru.padawans.features.movie.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.padawans.domain.model.main.MovieGeneralInfo
import ru.padawans.domain.model.movie.cast.CastsModel
import ru.padawans.domain.model.movie.details.MovieDetails
import ru.padawans.domain.model.movie.reviews.ResultsItem
import ru.padawans.domain.model.movie.trailers.TrailerResponse
import ru.padawans.domain.di.DataProvider
import ru.padawans.domain.repository.MovieInfoRepository
import ru.padawans.features.ErrorMessage
import ru.padawans.features.movie.view.MovieFragment
import kotlin.Exception

class MovieFragmentViewModel(
    provider: DataProvider, movieId: Int,
    private val movieInfoRepository: MovieInfoRepository = provider.getMovieInfoRepository()
) : ViewModel() {

    private val _infoData = MutableLiveData<MovieDetails>()
    val infoData = _infoData

    private val _trailersData = MutableLiveData<List<String>>()
    val trailersData = _trailersData

    private val _reviewsData = MutableLiveData<List<ResultsItem>>()
    val reviewsData = _reviewsData

    private val _similarData = MutableLiveData<List<MovieGeneralInfo>>()
    val similarData = _similarData
    private val newSimilar = mutableListOf<MovieGeneralInfo>()

    private val _castData = MutableLiveData<CastsModel>()
    val castData = _castData

    private val _similarMovieId = MutableLiveData<Int>()
    val similarMovieId = _similarMovieId

    private val _error = MutableLiveData<Int>()
    val error = _error


    private var reviewPage: Int = 1
    private var totalReviewPage: Int = 0

    private var similarPage: Int = 1
    private var totalSimilarPage: Int = 0


    init {
        getDetails(movieId)
        getTrailers(movieId)
        getCast(movieId)
        getReviews(movieId, reviewPage)
        getSimilar(movieId, similarPage)
    }

    private fun getDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                _infoData.value = movieInfoRepository.getDetails(movieId)
            } catch (e: Exception) {
                _error.value = ErrorMessage.getErrorMessage(e)
            }
        }
    }

    private fun getTrailers(movieId: Int) {
        viewModelScope.launch {
            try {
                _trailersData.value = getYouTubeKeys(movieInfoRepository.getMovieTrailer(movieId))
            } catch (e: Exception) {
                _error.value = ErrorMessage.getErrorMessage(e)
            }

        }
    }

    private fun getReviews(movieId: Int, page: Int) {
        viewModelScope.launch {
            try {
                val reviews = movieInfoRepository.getReviews(movieId, page)
                totalReviewPage = reviews.totalPages
                _reviewsData.value = reviews.results
            } catch (e: Exception) {
                _error.value = ErrorMessage.getErrorMessage(e)
            }

        }
    }

    private fun getSimilar(movieId: Int, page: Int) {
        viewModelScope.launch {
            try {
                val similar = movieInfoRepository.getSimilar(movieId, page)
                Log.d("Main", "getSimilar: " + page)
                totalSimilarPage = similar.totalPages
                newSimilar.addAll(similar.results)
                _similarData.value = newSimilar
            } catch (e: Exception) {
                _error.value = ErrorMessage.getErrorMessage(e)
            }
        }
    }

    private fun getCast(movieId: Int) {
        viewModelScope.launch {
            try {
                _castData.value = movieInfoRepository.getCasts(movieId)
            } catch (e: Exception) {
                _error.value = ErrorMessage.getErrorMessage(e)
            }
        }
    }

    private fun getYouTubeKeys(trailerResponse: TrailerResponse): List<String> {
        val keys: MutableList<String> = mutableListOf()
        trailerResponse.results.forEach {
            if (it.site == "YouTube" && it.type == "Trailer") {
                it.key?.let { it1 -> keys.add(it1) }
            }
        }
        return keys
    }


    fun onSimilarItemClick(movieId: Int) {
        Log.d("Main", "onItemClick: ")
        _similarMovieId.value = movieId
    }

    private fun loadMoreSimilar(movieId: Int) {
        similarPage++
        if (totalSimilarPage >= similarPage) {
            Log.d("Main", "loadMoreSimilar: " + similarPage)
            getSimilar(movieId, similarPage)
        }
    }

    private fun loadMoreReviews(movieId: Int) {
        reviewPage++
        if (totalReviewPage >= reviewPage) {
            getReviews(movieId, reviewPage)
        }
    }

    fun loadOnScroll(
        pastVisibleItems: Int,
        visibleItemCount: Int,
        totalItemCount: Int,
        contentType: String,
        movieId: Int
    ) {
        val loadIndent = 5
        if ((visibleItemCount + pastVisibleItems) >= totalItemCount - loadIndent) {
            when (contentType) {
                MovieFragment.REVIEWS -> {
                    loadMoreReviews(movieId)
                }
                MovieFragment.SIMILAR -> {
                    loadMoreSimilar(movieId)
                }
            }
        }
    }

    fun refreshAll(movieId: Int) {
        reviewPage = 1
        similarPage = 1
        totalSimilarPage = 0
        totalReviewPage = 0
        newSimilar.clear()
        getDetails(movieId)
        getTrailers(movieId)
        getCast(movieId)
        getReviews(movieId, reviewPage)
        getSimilar(movieId, similarPage)

    }

}