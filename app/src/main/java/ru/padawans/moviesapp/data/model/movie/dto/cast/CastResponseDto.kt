package ru.padawans.moviesapp.data.model.movie.dto.cast

import com.google.gson.annotations.SerializedName
import ru.padawans.moviesapp.data.model.movie.cast.CastItem
import ru.padawans.moviesapp.data.model.movie.cast.CastsModel
import ru.padawans.moviesapp.data.model.movie.cast.CrewItem

class CastResponseDto(

	@field:SerializedName("cast")
	val castDto: List<CastItemDto?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("crew")
	val crewDto: List<CrewItemDto?>? = null
) {
    fun convert(): CastsModel {

        val castList: List<CastItem>
        if (castDto != null) {
            castList = castDto.map { it!!.convert() }
        } else {
            castList = emptyList()
        }
        val crewList: List<CrewItem>
        if (crewDto != null) {
            crewList = crewDto.map { it!!.convert() }
        } else {
            crewList = emptyList()
        }

        return CastsModel(castList,id?:-1,crewList)
    }
}