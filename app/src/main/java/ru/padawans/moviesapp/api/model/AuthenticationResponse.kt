package ru.padawans.moviesapp.api.model

import com.google.gson.annotations.SerializedName

data class AuthenticationResponse(
@SerializedName("success") val success : Boolean,
@SerializedName("session_id") val session_id : String)
{
    fun convert(): AuthenticationResponse {
        return AuthenticationResponse(success,session_id)
    }
}