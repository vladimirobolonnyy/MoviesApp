package ru.padawans.moviesapp.data.model.movie.dto.trailers

import com.google.gson.annotations.SerializedName
import ru.padawans.domain.model.movie.trailers.TrailerItem

data class TrailerItemDto(

	@field:SerializedName("site")
	val site: String? = null,

	@field:SerializedName("size")
	val size: Int? = null,

	@field:SerializedName("iso_3166_1")
	val iso31661: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("iso_639_1")
	val iso6391: String? = null,

	@field:SerializedName("key")
	val key: String? = null
) {
    fun convert(): TrailerItem {
        return TrailerItem(
			site ?: "",
			size ?: 0,
			iso31661 ?: "",
			name ?: "",
			id ?: "",
			type ?: "",
			iso6391 ?: "",
			key ?: ""
		)
    }
}