package ru.padawans.features.search.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import ru.padawans.domain.model.search.Movie
import ru.padawans.domain.di.DataProvider
import ru.padawans.domain.repository.SearchRepository


class SearchFragmentViewModel(
    provider: DataProvider,
    private val repository: SearchRepository = provider.getSearchRepository()
) : ViewModel() {

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<Movie>>? = null

    val liveData = MutableLiveData<List<Movie>>()

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
