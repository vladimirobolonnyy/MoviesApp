package ru.padawans.domain.di

import ru.padawans.domain.repository.*

interface DataProvider {
    fun getNowPlayingRepository(): NowPlayingRepository
    fun getUpcomingRepository():UpcomingRepository
    fun getTrendingRepository():TrendingRepository
    fun getMovieInfoRepository():MovieInfoRepository
    fun getSearchRepository():SearchRepository
}