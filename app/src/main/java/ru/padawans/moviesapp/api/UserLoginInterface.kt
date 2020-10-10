package ru.padawans.moviesapp.api

import retrofit2.http.*
import ru.padawans.moviesapp.api.model.AuthenticationResponse
import ru.padawans.moviesapp.api.model.LoginResponse
import ru.padawans.moviesapp.api.model.TokenResponse
import ru.padawans.moviesapp.api.model.User


interface UserLoginInterface {

    @GET("/3/authentication/token/new?")
    fun getResponse(
        @Query("api_key") apiKey:String)
            : retrofit2.Call<TokenResponse>

    @POST("/3/authentication/session/new")
    fun getSession(
        @Query("request_token") requestToken:String)
            : retrofit2.Call<AuthenticationResponse>

    @POST("/3/authentication/token/validate_with_login?")
    fun registerUser(
        @Query("api_key") apiKey:String,
        @Body info: User
    ): retrofit2.Call<LoginResponse>

}