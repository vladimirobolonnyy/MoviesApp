package ru.padawans.moviesapp.api.repository

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.padawans.moviesapp.BuildConfig
import ru.padawans.moviesapp.api.UserGuestInterface

class TMDB_create_session(): TMDB_get_token {

    lateinit var userApi: UserGuestInterface

    init {
        val clientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        clientBuilder.addInterceptor(loggingInterceptor)
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        userApi = retrofit.create(UserGuestInterface::class.java)
    }

    override fun getLoginResponse(apikey: String): Call<TokenResponse> {
        val call: Call<TokenResponse> = userApi.getResponse(BuildConfig.API_KEY)

        return call
    }


}