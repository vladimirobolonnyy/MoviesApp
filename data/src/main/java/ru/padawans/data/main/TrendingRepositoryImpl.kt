package ru.padawans.data.main

import kotlinx.coroutines.flow.*
import ru.padawans.domain.model.main.MovieGeneralInfo
import ru.padawans.domain.ContentTypes
import ru.padawans.domain.repository.TrendingRepository


class TrendingRepositoryImpl(private val pagingSource: PagingSource = PagingSource(ContentTypes.TRENDING)) :
    TrendingRepository {


    override fun getTrendingMovies(page: Int): Flow<List<MovieGeneralInfo>> {
        return getData(pagingSource, page)
    }
}