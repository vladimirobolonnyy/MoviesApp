package ru.padawans.moviesapp.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.padawans.moviesapp.BuildConfig
import ru.padawans.moviesapp.data.api.MovieApi
import ru.padawans.moviesapp.utils.ApiInterceptor


object NetworkModule {
    val api = provideApiService()
    private fun provideApiService(): MovieApi {
        val clientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        clientBuilder.addInterceptor(loggingInterceptor)
        clientBuilder.addInterceptor(ApiInterceptor())
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        return retrofit.create(MovieApi::class.java)
    }
}