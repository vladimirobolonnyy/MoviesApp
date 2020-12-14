package ru.padawans.moviesapp.data.model.movie.dto.reviews

import com.google.gson.annotations.SerializedName
import ru.padawans.domain.model.movie.reviews.AuthorDetails
import ru.padawans.domain.model.movie.reviews.ResultsItem
import java.lang.NullPointerException

class ResultsItemDto(

	@field:SerializedName("author_details")
	val authorDetailsDto: AuthorDetailsDto? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("url")
	val url: String? = null
) {
    fun convert(): ResultsItem {

        val authorDetails: AuthorDetails
        if (authorDetailsDto != null) {
            authorDetails = authorDetailsDto.convert()
        } else {
            throw NullPointerException("Author details return null")
        }

		val reviewText  = content?.replace("\r","")

        return ResultsItem(
			authorDetails,
			updatedAt ?: "",
			author ?: "",
			createdAt ?: "",
			id ?: "",
			reviewText ?: "",
			url ?: ""
		)
    }
}