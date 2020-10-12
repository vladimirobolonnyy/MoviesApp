package ru.padawans.moviesapp.data.model.search.Dto

import com.google.gson.annotations.SerializedName
import ru.padawans.moviesapp.data.model.search.Response

class ResponseDTO(
    @SerializedName("page")
    val page: Int? = null,

    @SerializedName("total_results")
    val totalResults: Int? = null,

    @SerializedName("total_pages")
    val totalPages: Int? = null,

    @SerializedName("results")
    val results: List<MovieDTO>? = null
) {

    fun convert(): Response {
        val resultsModel = results?.map { t ->
            t.convert()
        }
        return Response(page ?: 0, totalResults ?: 0, totalPages ?: 0, resultsModel)
    }
}
