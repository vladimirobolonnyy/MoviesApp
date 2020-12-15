package ru.padawans.domain.di

import ru.padawans.domain.repository.NowPlayingRepository

interface DataProvider {
    fun getNowPlayingRepository(): NowPlayingRepository
}