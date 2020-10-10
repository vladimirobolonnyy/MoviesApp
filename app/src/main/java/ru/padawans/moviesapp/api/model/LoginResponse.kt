package ru.padawans.moviesapp.api.model

import com.google.gson.annotations.SerializedName

data class LoginResponse (@SerializedName("success")val success:Boolean,
                          @SerializedName("status_code")val status_code:Int,
                          @SerializedName("status_message")val status_message:String)
{
    fun convert(): LoginResponse {
        return LoginResponse(success,status_code,status_message)
    }
}
