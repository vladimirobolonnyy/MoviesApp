package ru.padawans.moviesapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.padawans.movie.UpcomingRepositoryImpl
import ru.padawans.moviesapp.data.model.upcoming.MovieGeneralInfo
import ru.padawans.moviesapp.data.repository.mainfragment.*


class MainFragmentViewModel() : ViewModel() {

    private val _movieId = MutableLiveData<Int>()
    val moviedId: LiveData<Int> = _movieId
    private val _upcomingData = MutableLiveData<List<MovieGeneralInfo>>()
    val upcomingData = _upcomingData

    private val _upcomingUpdateData = MutableLiveData<List<MovieGeneralInfo>>()
    val upcomingUpdateData = _upcomingUpdateData

    private val _nowPlayingData = MutableLiveData<List<MovieGeneralInfo>>()
    val nowPlayingData = _nowPlayingData

    private val _nowPlayingUpdateData = MutableLiveData<List<MovieGeneralInfo>>()
    val nowPlayingUpdateData = _nowPlayingUpdateData

    private val _trendingData = MutableLiveData<List<MovieGeneralInfo>>()
    val trendingData = _trendingData

    private val _trendingUpdateData = MutableLiveData<List<MovieGeneralInfo>>()
    val trendingUpdateData = _trendingUpdateData

    private val _error = MutableLiveData<String>()
    val error = _error


    //TODO потом поменять
    private val upcomingRepository = UpcomingRepositoryImpl()
    private val trendingRepository = TrendingRepositoryImpl()
    private val nowPlayingRepository = NowPlayingRepositoryImpl()


    fun loadUpcomingMovies(page: Int) {
        viewModelScope.launch {
            upcomingRepository.getUpcomingMovies(page).collect{
                Log.d("TAG", "loadUpcomingMovies: " + it.size)
                _upcomingData.value = it
                Log.d("TAG", "loadUpcomingMovies: " +upcomingRepository.isDataFromDatabase )
            }
                if (!upcomingRepository.isResponseSuccessful){
                    _error.value = "Check your internet connection \uD83D\uDE28"
                }

                if (upcomingRepository.isDataFromDatabase){
                        Log.d("TAG", "validUpcomingMovies: " )
                        upcomingRepository.validUpcomingMovies(page).collect {
                            if (it.isNotEmpty()){
                                _upcomingUpdateData.value = it
                            }

                        }

                }
           }

    }

    fun loadTrendingMovies(page: Int) {
        viewModelScope.launch {
            trendingRepository.getTrendingMovies(page).collect {
                Log.d("TAG", "loadTrendingMovies: " + it.size)
                _trendingData.value = it
            }

            if (!trendingRepository.isResponseSuccessful){
                _error.value = "Check your internet connection \uD83D\uDE28"
            }

            Log.d("TAG", "loadTrendingMovies: " +trendingRepository.isDataFromDatabase )
            if (trendingRepository.isDataFromDatabase){
                trendingRepository.validTrendingMovies(page).collect {
                    Log.d("TAG", "validTrendingMovies: " + it.size)
                    if (it.isNotEmpty()){
                        _trendingUpdateData.value = it
                    }
                }
            }
        }
    }

    fun loadNowPlayingMovies(page: Int) {
        viewModelScope.launch {
            nowPlayingRepository.getNowPlayingMovies(page).collect {
                Log.d("TAG", "loadTrendingMovies: " + it.size)
                _nowPlayingData.value = it
            }

            if (!nowPlayingRepository.isResponseSuccessful){
                _error.value = "Check your internet connection \uD83D\uDE28"
            }

            Log.d("TAG", "loadTrendingMovies: " +nowPlayingRepository.isDataFromDatabase )
            if (nowPlayingRepository.isDataFromDatabase){
                nowPlayingRepository.validNowPlayingMovies(page).collect {
                    Log.d("TAG", "validTrendingMovies: " + it.size)
                    if (it.isNotEmpty()){
                        _nowPlayingUpdateData.value = it
                    }
                }

            }
        }

    }

    fun onItemClick(movieId: Int) {
        Log.d("Main", "onItemClick: ")
        _movieId.value = movieId
    }

}




