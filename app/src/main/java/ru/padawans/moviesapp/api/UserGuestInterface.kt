package ru.padawans.moviesapp.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.padawans.moviesapp.api.repository.TokenResponse

interface UserGuestInterface {

    @GET("/3/authentication/token/new?")
    fun getResponse(
        @Query("api_key") apiKey:String)
            : retrofit2.Call<TokenResponse>
}