package ru.padawans.moviesapp.data.model.movie.dto.cast

import com.google.gson.annotations.SerializedName
import ru.padawans.moviesapp.data.model.movie.cast.CrewItem
import ru.padawans.moviesapp.utils.Resolutions
import ru.padawans.moviesapp.data.model.getAvatarUrl

class CrewItemDto(

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

	@field:SerializedName("department")
	val department: String? = null,

	@field:SerializedName("job")
	val job: String? = null
){
	fun convert():CrewItem{

		val path:String = getAvatarUrl(profilePath,Resolutions.CREDITS)

		return CrewItem(
			gender ?: -1,
			creditId ?: "",
			knownForDepartment ?: "",
			originalName ?: "",
			popularity?:-1.0,
			name?: "",
			path,
			id?:-1,
			adult?:false,
			department?: "",
			job?: ""
		)
	}
}