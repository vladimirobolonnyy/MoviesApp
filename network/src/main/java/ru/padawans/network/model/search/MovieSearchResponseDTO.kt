package ru.padawans.network.model.search

import com.google.gson.annotations.SerializedName
import ru.padawans.domain.model.search.MovieSearchResponse

class MovieSearchResponseDTO(
    @SerializedName("page")
    val page: Int? = null,

    @SerializedName("total_results")
    val totalResults: Int? = null,

    @SerializedName("total_pages")
    val totalPages: Int? = null,

    @SerializedName("results")
    val results: List<MovieDTO> = emptyList(),
) {
    fun convert(): MovieSearchResponse {
        val resultsModel = results.map { t ->
            t.convert()
        }
        return MovieSearchResponse(page ?: 0, totalResults ?: 0, totalPages ?: 0, resultsModel)
    }
}