package ru.padawans.moviesapp.api.repository

import com.google.gson.annotations.SerializedName

data class TokenResponse(
@SerializedName("success") val success : Boolean,
@SerializedName("expires_at") val expires_at : String,
@SerializedName("request_token") val request_token : String) {

    fun convert(): TokenResults {
        return TokenResults(request_token)
    }
}

