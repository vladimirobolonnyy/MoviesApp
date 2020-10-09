package ru.padawans.moviesapp.data.repository.mainFragment

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.functions.Function3
import ru.padawans.moviesapp.data.model.InteractorModel

class MainFragmentInteractor {
    private val upcomingRepository = UpcomingRepositoryImpl()
    private val nowPlayingRepository = NowPlayingRepositoryImpl()
    private val trendingRepository = TrendingRepositoryImpl()

    fun invoke(apiKey: String, language: String, page: Int): Observable<InteractorModel> {
        val call: Observable<InteractorModel> =
/*.fromSingle(  upcomingRepository.getUpcomingMovies(apiKey, language, page))
.z*/
            Observable.zip(
                upcomingRepository.getUpcomingMovies(apiKey, language, page).toObservable(),
                nowPlayingRepository.getNowPlayingMovies(apiKey, language, page).toObservable(),
                trendingRepository.getTrendingMovies(apiKey, page).toObservable(),
                Function3 { t, t1, t2 -> Triple(t,t1,t2) })
                .map { (upcoming, nowPlaying,trending) -> InteractorModel(upcoming, nowPlaying,trending) }

        return call
    }
}
