package ru.padawans.moviesapp.api

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.padawans.moviesapp.api.model.AuthenticationResponse
import ru.padawans.moviesapp.api.model.TokenResponse

interface UserGuestInterface {

    @GET("/3/authentication/token/new?")
    fun getResponse(
        @Query("api_key") apiKey:String)
            : retrofit2.Call<TokenResponse>

    @POST("/3/authentication/session/new")
    fun getSession(
        @Query("request_token") requestToken:String)
            : retrofit2.Call<AuthenticationResponse>
}