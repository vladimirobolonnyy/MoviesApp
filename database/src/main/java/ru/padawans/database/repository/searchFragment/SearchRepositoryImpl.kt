package ru.padawans.database.repository.searchFragment

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.padawans.network.api.MovieApi
import ru.padawans.domain.model.search.Movie
import ru.padawans.domain.repository.SearchRepository
import ru.padawans.network.di.NetworkModule


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
