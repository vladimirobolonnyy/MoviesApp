package ru.padawans.network.model.movie.details

import com.google.gson.annotations.SerializedName
import ru.padawans.domain.model.movie.details.SpokenLanguagesItem

class SpokenLanguagesItemDto(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("iso_639_1")
	val languageCode: String? = null
){
	 fun convert(): SpokenLanguagesItem {
		 return SpokenLanguagesItem(name?: "",languageCode?: "")
	 }
 }