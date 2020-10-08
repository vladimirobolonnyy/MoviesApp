package ru.padawans.moviesapp.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.padawans.moviesapp.BuildConfig
import ru.padawans.moviesapp.data.api.MovieApi
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
     fun provideClient():OkHttpClient{
        val clientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        clientBuilder.addInterceptor(loggingInterceptor)

        return clientBuilder.build()
     }

   /* @Provides
    @Singleton
    fun provideGson():Gson{
        return Gson()
    }*/

    @Provides
    @Singleton
    fun provideRetrofit( client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): MovieApi {
        return retrofit.create<MovieApi>(MovieApi::class.java)
    }
}