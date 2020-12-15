package ru.padawans.database.repository.mainfragment

import kotlinx.coroutines.flow.*
import ru.padawans.domain.model.main.MovieGeneralInfo
import ru.padawans.domain.repository.NowPlayingRepository


class NowPlayingRepositoryImpl(private val pagingSource: PagingSource = PagingSource(ContentTypes.NOW_PLAYING)) :
    NowPlayingRepository {


    override fun getNowPlayingMovies(page: Int): Flow<List<MovieGeneralInfo>> {
        return getData(pagingSource, page)
    }
}