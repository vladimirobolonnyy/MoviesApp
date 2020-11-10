package ru.padawans.moviesapp.data.repository.searchfragment

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.padawans.moviesapp.data.api.MovieApi
import ru.padawans.moviesapp.data.model.search.Movie
import ru.padawans.moviesapp.di.NetworkModule


class SearchRepositoryImpl(private val api: MovieApi = NetworkModule.api) : SearchRepository {

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
