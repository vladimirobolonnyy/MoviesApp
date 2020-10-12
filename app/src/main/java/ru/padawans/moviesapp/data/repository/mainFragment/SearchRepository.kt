package ru.padawans.moviesapp.data.repository.mainFragment

import io.reactivex.rxjava3.core.Single
import ru.padawans.moviesapp.data.model.search.Response

interface SearchRepository {

    fun movieListSearch(query: String): Single<Response>
    fun movieListByPageSearch(page: Int, query: String): Single<Response>
}
