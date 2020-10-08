package ru.padawans.moviesapp.api

import okhttp3.ResponseBody
import retrofit2.http.*

interface UserLoginInterface {

    @GET("getToken")
    @FormUrlEncoded
    fun getToken(@Body info: User): retrofit2.Call<ResponseBody>


    @POST("registerUser")
    @FormUrlEncoded
    fun registerUser(
        @Body info: User
    ): retrofit2.Call<ResponseBody>

}