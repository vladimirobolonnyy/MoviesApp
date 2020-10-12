package ru.padawans.moviesapp.data.repository.mainFragment

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.padawans.moviesapp.data.api.MovieApi
import ru.padawans.moviesapp.data.model.search.Response
import ru.padawans.moviesapp.di.provideApiService

class SearchRepositoryImpl(private val api: MovieApi = provideApiService()) : SearchRepository {

    override fun movieListSearch(query: String): Single<Response> {
        return movieListByPageSearch(1, query)
    }

    override fun movieListByPageSearch(page: Int, query: String): Single<Response> {
        return api.searchMovies(query = query, page = page)
            .map { it.convert() }
            .subscribeOn(Schedulers.io())
    }
}
