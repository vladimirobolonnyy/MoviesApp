package ru.padawans.network.model.movie.details

import com.google.gson.annotations.SerializedName
import ru.padawans.domain.model.movie.details.BelongsToCollection


class BelongsToCollectionDto(

	@field:SerializedName("backdrop_path")
	val backdropPath: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("poster_path")
	val posterPath: String? = null
){
	fun convert(): BelongsToCollection {
		return BelongsToCollection(backdropPath?: "",name?: "",id?: -1,posterPath?: "")
	}
}
