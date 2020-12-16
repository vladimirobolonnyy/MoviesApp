package ru.padawans.network.model

import ru.padawans.network.BuildConfig

fun getPosterPath(path: String?): String {
    if (path != null) {
        return BuildConfig.BASE_IMG_URL + "w780" + path
    } else {
        return ""
    }
}