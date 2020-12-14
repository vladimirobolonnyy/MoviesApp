package ru.padawans.moviesapp.data.model.main.dto

import com.google.gson.annotations.SerializedName
import ru.padawans.domain.model.main.MovieGeneralInfo
import ru.padawans.moviesapp.data.model.main.db.MovieGeneralInfoEntity


class MovieGeneralInfoDto (

	@SerializedName("popularity") val popularity : Double?,
	@SerializedName("vote_count") val voteCount : Int?,
	@SerializedName("video") val video : Boolean?,
	@SerializedName("poster_path") val posterPath : String?,
	@SerializedName("id") val id : Int?,
	@SerializedName("adult") val adult : Boolean?,
	@SerializedName("backdrop_path") val backdropPath : String?,
	@SerializedName("original_language") val originalLanguage : String?,
	@SerializedName("original_title") val originalTitle : String?,
	@SerializedName("genre_ids") val genreIds : List<Int>?,
	@SerializedName("title") val title : String?,
	@SerializedName("vote_average") val voteAverage : Double?,
	@SerializedName("overview") val overview : String?,
	@SerializedName("release_date") val releaseDate : String?
){
	fun convert(): MovieGeneralInfo {
			return MovieGeneralInfo(popularity?:0.0, voteCount?:0, video!!, posterPath ?: "N/A", id?:0, adult!!,
				backdropPath?:"N/A", originalLanguage?:"N/A", originalTitle?:"N/A",
				genreIds?: listOf(0), title?:"N/A", voteAverage?:0.0, overview?:"N/A", releaseDate?:"N/A")
	}

	fun convertToEntity(): MovieGeneralInfoEntity {
		return MovieGeneralInfoEntity(
			popularity = popularity ?: 0.0,
			voteCount = voteCount ?: 0,
			video = video!!,
			posterPath = posterPath ?: "N/A",
			id = id ?: 0,
			adult = adult!!,
			backdropPath = backdropPath ?: "N/A",
			originalLanguage = originalLanguage ?: "N/A",
			originalTitle = originalTitle ?: "N/A",
			genreIds = genreIds ?: listOf(0),
			title = title ?: "N/A",
			voteAverage = voteAverage ?: 0.0,
			overview = overview ?: "N/A",
			releaseDate = releaseDate ?: "N/A"
		)
	}

 }