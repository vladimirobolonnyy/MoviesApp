package ru.padawans.moviesapp.data.repository.mainfragment

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.padawans.moviesapp.data.model.main.MovieGeneralInfo


class TrendingRepositoryImpl(private val pagingSource: PagingSource = PagingSource(ContentTypes.TRENDING)) :
    TrendingRepository {


    override fun getTrendingMovies(page: Int): Flow<List<MovieGeneralInfo>> {
        return getData(pagingSource, page)
    }
}