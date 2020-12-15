package ru.padawans.database.repository.mainfragment

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.padawans.domain.model.main.MovieGeneralInfo

fun getData(pagingSource: PagingSource, page: Int): Flow<List<MovieGeneralInfo>> {
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
                    emit(validData)
                }
            } else {
                emit(pagingSource.dataFromServer(page))
            }
        }
    }.flowOn(
        Dispatchers.IO
    )
}