package ru.padawans.network.model

import ru.padawans.network.BuildConfig


fun getAvatarUrl(avatarPath: String?, resolution: String): String {
    return if (avatarPath != null) {
        if (avatarPath.contains("https")) {
            val index =  avatarPath.indexOf("https")
            avatarPath.removeRange(0, index)
        } else {
            BuildConfig.BASE_IMG_URL + resolution + avatarPath
        }
    } else {
        ""
    }
}