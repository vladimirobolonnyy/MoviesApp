package ru.padawans.moviesapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import ru.padawans.moviesapp.BuildConfig
import ru.padawans.moviesapp.data.model.InteractorModel
import ru.padawans.moviesapp.data.model.upcoming.UpcomingMovies
import ru.padawans.moviesapp.data.repository.mainFragment.*


class MainFragmentViewModel() : ViewModel() {
    private var mainFragmentInteractor = MainFragmentInteractor()
    private val _viewState = MutableLiveData<InteractorModel>()
    val viewState:LiveData<InteractorModel> =_viewState
    private val _error = MutableLiveData<String>()
    val error:LiveData<String> =_error


    init {
        loadUpcomingMovies(BuildConfig.API_KEY,"en-US",1)
    }


    private fun loadUpcomingMovies(apiKey: String, language: String, page: Int) {
        mainFragmentInteractor.invoke(apiKey, language, page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ interactorModel ->
                _viewState.value = interactorModel
            }, { throwable ->
                _error.value = throwable.localizedMessage
            })
    }
}




