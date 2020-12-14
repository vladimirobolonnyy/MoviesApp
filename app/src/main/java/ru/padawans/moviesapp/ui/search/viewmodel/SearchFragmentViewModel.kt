package ru.padawans.moviesapp.ui.search.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import ru.padawans.domain.model.search.Movie
import ru.padawans.moviesapp.data.repository.searchfragment.SearchRepositoryImpl


class SearchFragmentViewModel : ViewModel() {

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<Movie>>? = null

    val liveData = MutableLiveData<List<Movie>>()
    val repository = SearchRepositoryImpl()

    fun movieListSearch(query: String): Flow<PagingData<Movie>> {
        val lastResult = currentSearchResult
        if (query == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = query
        val newResult: Flow<PagingData<Movie>> = repository.movieListSearch(query)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}
