package ru.padawans.moviesapp.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import ru.padawans.movie.UpcomingRepositoryImpl
import ru.padawans.moviesapp.data.model.main.MovieGeneralInfo
import ru.padawans.moviesapp.data.repository.mainfragment.*
import ru.padawans.moviesapp.R
import ru.padawans.moviesapp.utils.ErrorMessage


class MainFragmentViewModel() : ViewModel() {

    private val _movieId = MutableLiveData<Int>()
    val movieId: LiveData<Int> = _movieId

    private val _upcomingData = MutableLiveData<Triple<List<MovieGeneralInfo>, Int, Int>>()
    val upcomingData = _upcomingData

    private val _nowPlayingData = MutableLiveData<Triple<List<MovieGeneralInfo>, Int, Int>>()
    val nowPlayingData = _nowPlayingData

    private val _trendingData = MutableLiveData<Triple<List<MovieGeneralInfo>, Int, Int>>()
    val trendingData = _trendingData

    private val _error = MutableLiveData<Int>()
    val error = _error

    private val _refreshState = MutableLiveData<Boolean>()
    val refreshState = _refreshState

    private var upcomingPage: Int = 1
    private var nowPlayingPage: Int = 1
    private var trendingPage: Int = 1
    private var totalUpcomingPages: Int = 0
    private var totalNowPlayingPages = 0
    private var totalTrendingPages = 0

    private val upcomingRepository = UpcomingRepositoryImpl()
    private val trendingRepository = TrendingRepositoryImpl()
    private val nowPlayingRepository = NowPlayingRepositoryImpl()

    init {
        loadUpcomingMovies(upcomingPage)
        loadNowPlayingMovies(nowPlayingPage)
        loadTrendingMovies(trendingPage)
    }

    private fun loadUpcomingMovies(page: Int) {
        viewModelScope.launch {
            upcomingRepository.getUpcomingMovies(page).catch {
                Log.d("TAG", "loadUpcomingMovies: " + "No internet " + it.javaClass.name)
                _error.value = ErrorMessage.getErrorMessage(it)
            }.collect {
                if (it.isNotEmpty()) {
                    _upcomingData.value = Triple(it, it.size, page)

                } else {
                    if (totalUpcomingPages == 0) {
                        totalUpcomingPages = page - 1
                    }
                }
            }
        }
    }

    private fun loadTrendingMovies(page: Int) {
        viewModelScope.launch {
            trendingRepository.getTrendingMovies(page).catch {
                _error.value = ErrorMessage.getErrorMessage(it)
            }.collect {
                if (it.isNotEmpty()) {
                    _trendingData.value = Triple(it, it.size, page)

                } else {
                    if (totalTrendingPages == 0) {
                        totalTrendingPages = page - 1
                    }
                }
            }
        }
    }

    private fun loadNowPlayingMovies(page: Int) {
        viewModelScope.launch {
            nowPlayingRepository.getNowPlayingMovies(page).catch {
                _error.value = ErrorMessage.getErrorMessage(it)
            }.collect {
                if (it.isNotEmpty()) {
                    _nowPlayingData.value = Triple(it, it.size, page)
                } else {
                    if (totalNowPlayingPages == 0) {
                        totalNowPlayingPages = page - 1
                    }
                }
            }
        }
    }

    fun refreshAll() {
        val page = 1
        upcomingPage = 1
        trendingPage = 1
        nowPlayingPage = 1
        totalUpcomingPages = 0
        totalNowPlayingPages = 0
        totalTrendingPages = 0
        viewModelScope.launch {
            loadUpcomingMovies(page)
            loadTrendingMovies(page)
            loadNowPlayingMovies(page)
        }
        _refreshState.value = false
    }

    fun loadOnScroll(
        pastVisiblesItems: Int,
        visibleItemCount: Int,
        totalItemCount: Int,
        contentType: String
    ) {
        val loadIndent = 5
        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - loadIndent) {
            when (contentType) {
                ContentTypes.UPCOMING -> {
                    upcomingPage++
                    if (totalUpcomingPages != 0) {
                        if (totalUpcomingPages >= upcomingPage) {
                            loadUpcomingMovies(upcomingPage)
                        }
                    } else {
                        loadUpcomingMovies(upcomingPage)
                    }
                }
                ContentTypes.NOW_PLAYING -> {
                    nowPlayingPage++
                    if (totalNowPlayingPages != 0) {
                        if (totalNowPlayingPages >= nowPlayingPage) {
                            loadNowPlayingMovies(nowPlayingPage)
                        }
                    } else {
                        loadNowPlayingMovies(nowPlayingPage)
                    }
                }
                ContentTypes.TRENDING -> {
                    trendingPage++
                    if (totalTrendingPages != 0) {
                        if (totalTrendingPages >= trendingPage) {
                            loadTrendingMovies(trendingPage)
                        }
                    } else {
                        loadTrendingMovies(trendingPage)
                    }
                }
            }
        }
    }


    fun onCardClick(movieId: Int) {
        _movieId.value = movieId
    }

}




