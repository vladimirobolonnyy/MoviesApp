package ru.padawans.data.di

import ru.padawans.data.main.MainMoviesRepositoryImpl
import ru.padawans.data.movie.MovieInfoRepositoryImpl
import ru.padawans.data.search.SearchRepositoryImpl
import ru.padawans.domain.di.DataProvider
import ru.padawans.domain.repository.*

object DataModuleImpl : DataProvider {

    override fun getMainMoviesRepository(contentType:String): MainMoviesRepository {
       return  MainMoviesRepositoryImpl(contentType)
    }

    override fun getMovieInfoRepository(): MovieInfoRepository {
       return MovieInfoRepositoryImpl()
    }

    override fun getSearchRepository(): SearchRepository {
        return SearchRepositoryImpl()
    }
}