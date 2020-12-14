package ru.padawans.moviesapp.data.model.movie.dto.details

import com.google.gson.annotations.SerializedName
import ru.padawans.domain.model.movie.details.GenresItem

class GenresItemDto(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
){
	 fun convert(): GenresItem {
		 return GenresItem(name?:"",id?:-1)
	 }
 }