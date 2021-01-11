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

     override var updateData:Boolean = false

    override fun getMainMovies(
        page: Int
    ): Flow<List<MovieGeneralInfo>> {
        Log.d("TAG", "getUpcomingMovies: ")
          return flow<List<MovieGeneralInfo>> {
            val cache = pagingSource.dataFromCache(page)
            if (cache != null) {
                emit(cache)
            } else {
                val database = pagingSource.dataFromDatabase(page)
                if (database.isNotEmpty()) {
                    emit(database)
                    val validData = pagingSource.isDataInDatabaseValid(page)
                    if (validData.isNotEmpty()) {
                        updateData = true
                        emit(validData)
                    }
                } else {
                    emit(pagingSource.dataFromServer(page))
                }
            }
        }.flowOn(
            Dispatchers.IO
        ).onCompletion {
              updateData = false
          }
    }
}
