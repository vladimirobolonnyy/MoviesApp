package ru.padawans.moviesapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.padawans.moviesapp.data.model.search.Movie
import ru.padawans.moviesapp.data.repository.mainFragment.SearchRepositoryImpl

class SearchFragmentViewModel(app: Application): AndroidViewModel(app){

    val liveData= MutableLiveData<List<Movie>>()
    val repository= SearchRepositoryImpl()

    private val disposable= CompositeDisposable()

    fun movieListSearch(query:String){
        disposable.add(
            repository.movieListSearch(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({s->
                    liveData.value=s.results
                },{throwable->
                    liveData.value=emptyList()
                })
        )
    }

    override fun onCleared(){
        super.onCleared()
        disposable.dispose()
    }
}
