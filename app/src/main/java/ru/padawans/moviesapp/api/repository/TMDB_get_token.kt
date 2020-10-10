package ru.padawans.moviesapp.api.repository

import retrofit2.Call

interface TMDB_get_token {
    fun getLoginResponse(apikey:String): Call<TokenResponse>
}