package ru.padawans.data.di

import ru.padawans.data.main.NowPlayingRepositoryImpl
import ru.padawans.data.main.TrendingRepositoryImpl
import ru.padawans.data.main.UpcomingRepositoryImpl
import ru.padawans.data.movie.MovieInfoRepositoryImpl
import ru.padawans.data.search.SearchRepositoryImpl
import ru.padawans.domain.di.DataProvider
import ru.padawans.domain.repository.*

object DataModuleImpl : DataProvider {

    private val nowPlayingRepository by lazy { NowPlayingRepositoryImpl() }
    private val upcomingRepository by lazy { UpcomingRepositoryImpl() }
    private val trendingRepository by lazy { TrendingRepositoryImpl() }


    override fun getNowPlayingRepository(): NowPlayingRepository {
        // если надо на каждый запрос зависимости возвращать новый объект
        // return  NowPlayingRepositoryImpl()

        // если надо на каждый запрос зависимости возвращать одинаковый объект
        // аналог @Singleton в даггере
        return nowPlayingRepository
    }

    override fun getUpcomingRepository(): UpcomingRepository {
       return  upcomingRepository
    }

    override fun getTrendingRepository(): TrendingRepository {
        return trendingRepository
    }


    override fun getMovieInfoRepository(): MovieInfoRepository {
       return MovieInfoRepositoryImpl()
    }

    override fun getSearchRepository(): SearchRepository {
        return SearchRepositoryImpl()
    }
}