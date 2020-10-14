package ru.padawans.moviesapp.data.repository.mainFragment

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.padawans.moviesapp.data.SearchMoviesPagingSource
import ru.padawans.moviesapp.data.api.MovieApi
import ru.padawans.moviesapp.data.model.search.Movie
import ru.padawans.moviesapp.di.provideApiService

class SearchRepositoryImpl(private val api: MovieApi = provideApiService()) : SearchRepository {

    override fun movieListSearch(query: String): Flow<PagingData<Movie>> {
        return Pager(config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false
        ), pagingSourceFactory = { SearchMoviesPagingSource(api, query) }).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}
