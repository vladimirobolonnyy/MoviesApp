package ru.padawans.moviesapp.api

import retrofit2.http.*


interface UserLoginInterface {

    @GET("/3/authentication/token/new?")
    fun getResponse(
        @Query("api_key") apiKey:String)
            : retrofit2.Call<TokenResponse>

    @POST("/3/authentication/token/validate_with_login?")
    @FormUrlEncoded
    fun registerUser(
        @Query("api_key") apiKey:String,
        @Body info: User
    ): retrofit2.Call<LoginResponse>

}