package ru.padawans.data.di

import ru.padawans.data.main.NowPlayingRepositoryImpl
import ru.padawans.domain.di.DataProvider
import ru.padawans.domain.repository.NowPlayingRepository

object DataModuleImpl : DataProvider {

    private val nowPlayingRepository by lazy { NowPlayingRepositoryImpl() }

    override fun getNowPlayingRepository(): NowPlayingRepository {
        // если надо на каждый запрос зависимости возвращать новый объект
        // return  NowPlayingRepositoryImpl()

        // если надо на каждый запрос зависимости возвращать одинаковый объект
        // аналог @Singleton в даггере
        return nowPlayingRepository
    }
}