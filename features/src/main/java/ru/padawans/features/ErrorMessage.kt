package ru.padawans.features


import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


object ErrorMessage {

    fun getErrorMessage(exeption: Throwable): Int {
        if (exeption is UnknownHostException || exeption is SocketTimeoutException || exeption is ConnectException) {
            return R.string.internet_error
        }
        return R.string.common_error
    }
}