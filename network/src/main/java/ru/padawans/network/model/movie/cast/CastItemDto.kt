package ru.padawans.network.model.movie.cast

import com.google.gson.annotations.SerializedName
import ru.padawans.domain.model.movie.cast.CastItem
import ru.padawans.network.model.getAvatarUrl
import ru.padawans.network.utils.Resolutions

class CastItemDto(

    @field:SerializedName("cast_id")
    val castId: Int? = null,

    @field:SerializedName("character")
    val character: String? = null,

    @field:SerializedName("gender")
    val gender: Int? = null,

    @field:SerializedName("credit_id")
    val creditId: String? = null,

    @field:SerializedName("known_for_department")
    val knownForDepartment: String? = null,

    @field:SerializedName("original_name")
    val originalName: String? = null,

    @field:SerializedName("popularity")
    val popularity: Double? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("profile_path")
    val profilePath: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("adult")
    val adult: Boolean? = null,

    @field:SerializedName("order")
    val order: Int? = null
) {
    fun convert(): CastItem {

        val path:String = getAvatarUrl(profilePath, Resolutions.CREDITS)

        return CastItem(
            castId ?: -1,
            character ?: "",
            gender ?: -1,
            creditId?:"",
            knownForDepartment?:"",
            originalName?:"",
            popularity?:-1.0,
            name?:"",
            path,
            id?:-1,
            adult?:false,
            order?:-1
        )
    }
}