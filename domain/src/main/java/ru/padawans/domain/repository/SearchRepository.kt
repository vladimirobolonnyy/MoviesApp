package ru.padawans.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.padawans.domain.model.search.Movie

interface SearchRepository {
    fun movieListSearch(query: String): Flow<PagingData<Movie>>
}
