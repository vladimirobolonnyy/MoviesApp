package ru.padawans.features.main.viewmodel

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.padawans.domain.model.main.MovieGeneralInfo
import ru.padawans.domain.ContentTypes
import ru.padawans.domain.di.DataProvider
import ru.padawans.domain.repository.MainMoviesRepository
import ru.padawans.features.ErrorMessage


class MainViewModel(
    provider: DataProvider,
    private val mainMoviesRepository: MainMoviesRepository = provider.getMainMoviesRepository(ContentTypes.UPCOMING),
    private val trendingRepository: MainMoviesRepository = provider.getMainMoviesRepository(ContentTypes.TRENDING),
    private val nowPlayingRepository: MainMoviesRepository = provider.getMainMoviesRepository(ContentTypes.NOW_PLAYING),
) : ViewModel() {

    private val _movieId = MutableLiveData<Int>()
    val movieId: LiveData<Int> = _movieId

    private val _upcomingData = MutableLiveData<List<MovieGeneralInfo>>()
    val upcomingData = _upcomingData
    private val newUpcoming = mutableListOf<MovieGeneralInfo>()
    private val lastUpcoming = mutableListOf<MovieGeneralInfo>()


    private val _nowPlayingData = MutableLiveData<List<MovieGeneralInfo>>()
    val nowPlayingData = _nowPlayingData
    private val newNowPlaying = mutableListOf<MovieGeneralInfo>()
    private val lastNowPlaying = mutableListOf<MovieGeneralInfo>()

    private val _trendingData = MutableLiveData<List<MovieGeneralInfo>>()
    val trendingData = _trendingData
    private val newTrending = mutableListOf<MovieGeneralInfo>()
    private val lastTrending = mutableListOf<MovieGeneralInfo>()

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


    init {
        loadUpcomingMovies(upcomingPage)
        loadNowPlayingMovies(nowPlayingPage)
        loadTrendingMovies(trendingPage)
    }

    private fun loadUpcomingMovies(page: Int) {
        viewModelScope.launch {
            mainMoviesRepository.getMainMovies(page).catch {
                Log.d("TAG", "loadUpcomingMovies: " + "No internet " + it.javaClass.name)
                _error.value = ErrorMessage.getErrorMessage(it)
            }.collect {
                if (!it.isNullOrEmpty()) {
                    if (mainMoviesRepository.updateData){
                      newUpcoming.removeAll(lastUpcoming)
                    }

                    newUpcoming.addAll(it)
                    lastUpcoming.clear()
                    lastUpcoming.addAll(it)
                    _upcomingData.value = newUpcoming

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
            trendingRepository.getMainMovies(page).catch {
                _error.value = ErrorMessage.getErrorMessage(it)
            }.collect {
                if (!it.isNullOrEmpty()) {
                    if (trendingRepository.updateData){
                        newTrending.removeAll(lastTrending)
                    }
                    newTrending.addAll(it)
                    lastTrending.clear()
                    lastTrending.addAll(it)

                    _trendingData.value = newTrending

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
            nowPlayingRepository.getMainMovies(page).catch {
                _error.value = ErrorMessage.getErrorMessage(it)
            }.collect {
                if (!it.isNullOrEmpty()) {
                    if (nowPlayingRepository.updateData){
                        newNowPlaying.removeAll(lastNowPlaying)
                    }
                    newNowPlaying.addAll(it)
                    lastNowPlaying.clear()
                    lastNowPlaying.addAll(it)

                    _nowPlayingData.value = newNowPlaying
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
        newUpcoming.clear()
        newNowPlaying.clear()
        newTrending.clear()
        lastUpcoming.clear()
        lastNowPlaying.clear()
        lastTrending.clear()
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




