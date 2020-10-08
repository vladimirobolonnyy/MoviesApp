package ru.padawans.moviesapp.data.repository

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.padawans.moviesapp.BuildConfig
import ru.padawans.moviesapp.data.api.MovieApi
import ru.padawans.moviesapp.data.model.upcoming.Dto.UpcomingMoviesDto
import ru.padawans.moviesapp.data.model.upcoming.UpcomingMovies


class MainFragmentRepositoryImpl() : MainFragmentRepository{

//@Inject
lateinit var movieApi: MovieApi

    init {
        val clientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        clientBuilder.addInterceptor(loggingInterceptor)
        val retrofit= Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        movieApi = retrofit.create(MovieApi::class.java)
    }


    override fun getUpcomingMovies(
        apiKey: String,
        language: String,
        page: Int
    ): Single<UpcomingMovies> {

        val call:Single<UpcomingMovies> = movieApi.getUpcomingMovies(apiKey,language,page)
            .map { it.convert() }
            //  call.map({it->it.convert()  }) //не работает почему то(((
                .subscribeOn(Schedulers.io())


        return call
    }
}