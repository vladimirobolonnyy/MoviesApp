package ru.padawans.network.model.movie.reviews

import com.google.gson.annotations.SerializedName
import ru.padawans.domain.model.movie.reviews.MovieReviews
import ru.padawans.domain.model.movie.reviews.ResultsItem
import java.lang.NullPointerException

class MovieReviewsDto(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("results")
	val results: List<ResultsItemDto?>? = null,

	@field:SerializedName("total_results")
	val totalResults: Int? = null
) {
    fun convert(): MovieReviews {
        val info: MutableList<ResultsItem> = mutableListOf()
        if (results != null) {
            info.addAll(results.map { it!!.convert() })
        } else {
            throw NullPointerException("Reviews results return null")
        }

        return MovieReviews(
			id ?: -1,
			page ?: -1,
			totalPages ?: -1,
			info,
			totalResults ?: -1
		)
    }
}