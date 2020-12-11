package ru.padawans.moviesapp.utils

import android.content.Context
import retrofit2.HttpException
import ru.padawans.moviesapp.R
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.security.AccessControlContext

object ErrorMessage {

    fun getErrorMessage(exeption: Throwable): Int {
        if (exeption is UnknownHostException || exeption is SocketTimeoutException || exeption is ConnectException) {
            return R.string.internet_error
        }
        return R.string.common_error
    }
}