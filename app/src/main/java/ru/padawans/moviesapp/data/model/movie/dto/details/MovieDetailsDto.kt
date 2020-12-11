package ru.padawans.moviesapp.data.model.movie.dto.details

import com.google.gson.annotations.SerializedName
import ru.padawans.moviesapp.data.model.movie.details.*
import ru.padawans.moviesapp.data.model.movie.details.MovieDetails
import java.lang.NullPointerException

class MovieDetailsDto(

	@field:SerializedName("original_language")
	val originalLanguage: String? = null,

	@field:SerializedName("imdb_id")
	val imdbId: String? = null,

	@field:SerializedName("video")
	val video: Boolean? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("backdrop_path")
	val backdropPath: String? = null,

	@field:SerializedName("revenue")
	val revenue: Long? = null,

	@field:SerializedName("genres")
	val genres: List<GenresItemDto?>? = null,

	@field:SerializedName("popularity")
	val popularity: Double? = null,

	@field:SerializedName("production_countries")
	val productionCountries: List<ProductionCountriesItemDto?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("vote_count")
	val voteCount: Int? = null,

	@field:SerializedName("budget")
	val budget: Int? = null,

	@field:SerializedName("overview")
	val overview: String? = null,

	@field:SerializedName("original_title")
	val originalTitle: String? = null,

	@field:SerializedName("runtime")
	val runtime: Int? = null,

	@field:SerializedName("poster_path")
	val posterPath: String? = null,

	@field:SerializedName("spoken_languages")
	val spokenLanguages: List<SpokenLanguagesItemDto?>? = null,

	@field:SerializedName("production_companies")
	val productionCompanies: List<ProductionCompaniesItemDto?>? = null,

	@field:SerializedName("release_date")
	val releaseDate: String? = null,

	@field:SerializedName("vote_average")
	val voteAverage: Double? = null,

	@field:SerializedName("belongs_to_collection")
	val belongsToCollection: BelongsToCollectionDto? = null,

	@field:SerializedName("tagline")
	val tagline: String? = null,

	@field:SerializedName("adult")
	val adult: Boolean? = null,

	@field:SerializedName("homepage")
	val homepage: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) {
    fun convert(): MovieDetails {
        val genresList: List<GenresItem>
        if (genres != null) {
            genresList = genres.map { it!!.convert() }
        } else {
            genresList = emptyList()
        }
        val countriesItem: List<ProductionCountriesItem>
        if (productionCountries != null) {
            countriesItem = productionCountries.map { it!!.convert() }
        } else {
            countriesItem = emptyList()
        }
        val laguages: List<SpokenLanguagesItem>
        if (spokenLanguages != null) {
            laguages = spokenLanguages.map { it!!.convert() }
        } else {
            laguages = emptyList()
        }
        val companies: List<ProductionCompaniesItem>
        if (productionCompanies != null) {
            companies = productionCompanies.map { it!!.convert() }
        } else {
            companies = emptyList()
        }
        val collections: BelongsToCollection
        if (belongsToCollection != null) {
            collections = belongsToCollection.convert()
        } else {
            collections = BelongsToCollection("", "", -1, "")
        }

        return MovieDetails(
			originalLanguage ?: "",
			imdbId ?: "",
			video ?: false,
			title ?: "",
			backdropPath ?: "",
			revenue ?: -1,
			genresList,
			popularity ?: 0.0,
			countriesItem,
			id ?: -1,
			voteCount ?: -1,
			budget ?: 0,
			overview ?: "",
			originalTitle ?: "",
			runtime ?: 0,
			posterPath ?: "",
			laguages,
			companies,
			releaseDate ?: "",
			voteAverage ?: 0.0,
			collections,
			tagline ?: "",
			adult ?: false,
			homepage ?: "",
			status ?: ""
		)
    }


}