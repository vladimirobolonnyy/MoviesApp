package ru.padawans.moviesapp.utils

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import ru.padawans.moviesapp.BuildConfig

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url
        val requestBuilder = originalUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY).build()

        val newUrl = original.newBuilder().url(requestBuilder).build()

        return chain.proceed(newUrl)
    }
}