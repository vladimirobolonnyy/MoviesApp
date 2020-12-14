package ru.padawans.network.model.movie.details

import com.google.gson.annotations.SerializedName
import ru.padawans.domain.model.movie.details.ProductionCompaniesItem

class ProductionCompaniesItemDto(

	@field:SerializedName("logo_path")
	val logoPath: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("origin_country")
	val originCountry: String? = null
) {
    fun convert(): ProductionCompaniesItem {
        return ProductionCompaniesItem(logoPath ?: "", name ?: "", id ?: -1, originCountry ?: "")
    }
}