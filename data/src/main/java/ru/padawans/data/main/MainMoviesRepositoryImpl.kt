package ru.padawans.data.main

import ru.padawans.domain.repository.MainMoviesRepository


import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.padawans.domain.model.main.MovieGeneralInfo


class MainMoviesRepositoryImpl(
    contentType:String,
    private val pagingSource: PagingSource = PagingSource(contentType)
) : MainMoviesRepository {

    override fun getMainMovies(
        page: Int
    ): Flow<Pair<List<MovieGeneralInfo>,Boolean>> {
        Log.d("TAG", "getUpcomingMovies: ")
          return flow<Pair<List<MovieGeneralInfo>,Boolean>> {
            val cache = pagingSource.dataFromCache(page)
            if (cache != null) {
                emit(Pair(cache,false))
            } else {
                val database = pagingSource.dataFromDatabase(page)
                if (database.isNotEmpty()) {
                    emit(Pair(database,false))
                    val validData = pagingSource.isDataInDatabaseValid(page)
                    if (validData.isNotEmpty()) {
                        emit(Pair(validData,true))
                    }
                } else {
                    emit(Pair(pagingSource.dataFromServer(page),false))
                }
            }
        }.flowOn(
            Dispatchers.IO
        )
    }
}
