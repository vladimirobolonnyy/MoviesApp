package ru.padawans.moviesapp.api

import com.google.gson.annotations.SerializedName

data class AuthenticationResponse(
@SerializedName("success") val success : Boolean,
@SerializedName("session_id") val session_id : String)