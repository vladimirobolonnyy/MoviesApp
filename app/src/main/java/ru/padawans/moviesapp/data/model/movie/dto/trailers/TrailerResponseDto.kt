package ru.padawans.moviesapp.data.model.movie.dto.trailers

import com.google.gson.annotations.SerializedName
import ru.padawans.domain.model.movie.trailers.TrailerItem
import ru.padawans.domain.model.movie.trailers.TrailerResponse

data class TrailerResponseDto(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("results")
	val results: List<TrailerItemDto?>? = null
){
	fun convert():TrailerResponse{
		val trailers:List<TrailerItem>
		if (results!=null){
			trailers = results.map { it!!.convert() }
		}else{
			trailers = emptyList()
		}

		return TrailerResponse(id?: -1,trailers)
	}
}