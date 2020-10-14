package ru.padawans.moviesapp.data.repository.mainFragment

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.padawans.moviesapp.data.model.search.Movie

interface SearchRepository {
    fun movieListSearch(query: String): Flow<PagingData<Movie>>
}
