package ru.padawans.domain.di

import ru.padawans.domain.repository.*

interface DataProvider {
    fun getMainMoviesRepository(contentType:String):MainMoviesRepository
    fun getMovieInfoRepository():MovieInfoRepository
    fun getSearchRepository():SearchRepository
}