package ru.padawans.network.model.movie.details

import com.google.gson.annotations.SerializedName
import ru.padawans.domain.model.movie.details.ProductionCountriesItem

class ProductionCountriesItemDto(

	@field:SerializedName("iso_3166_1")
	val countryCode: String? = null,

	@field:SerializedName("name")
	val name: String? = null
){
	 fun convert(): ProductionCountriesItem {
		 return ProductionCountriesItem(countryCode?: "",name?: "")
	 }

 }