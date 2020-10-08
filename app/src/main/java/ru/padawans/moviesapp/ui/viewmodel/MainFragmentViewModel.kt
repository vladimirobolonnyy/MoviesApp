package ru.padawans.moviesapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.padawans.moviesapp.BuildConfig
import ru.padawans.moviesapp.data.model.upcoming.Dto.UpcomingMoviesDto
import ru.padawans.moviesapp.data.model.upcoming.UpcomingMovies
import ru.padawans.moviesapp.data.repository.MainFragmentRepository
import ru.padawans.moviesapp.data.repository.MainFragmentRepositoryImpl


class MainFragmentViewModel() : ViewModel() {
    private var mainFragmentRepository:MainFragmentRepository = MainFragmentRepositoryImpl()
    private val _viewState = MutableLiveData<UpcomingMovies>()
    val viewState:LiveData<UpcomingMovies> =_viewState

    init {
        loadUpcomingMovies(BuildConfig.API_KEY,"en-US",1)
    }


    private fun loadUpcomingMovies(apiKey: String, language: String, page: Int) {
        mainFragmentRepository.getUpcomingMovies(apiKey, language, page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ r ->
              //  Log.d("Main", "loadUpcomingMovies: " + r.total_results)

                _viewState.value = r
            }, { t ->
                Log.d("Main", "something go wrong: "+ t.localizedMessage)
            })

    }

}




