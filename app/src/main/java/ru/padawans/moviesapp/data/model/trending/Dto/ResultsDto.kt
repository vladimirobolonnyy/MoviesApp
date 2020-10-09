package ru.padawans.moviesapp.data.model.trending.Dto

import com.google.gson.annotations.SerializedName
import ru.padawans.moviesapp.data.model.trending.ResultsTrending
import ru.padawans.moviesapp.data.model.upcoming.Results


class ResultsDto (

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
	fun convert():ResultsTrending{

			return ResultsTrending(popularity!!, voteCount!!, video!!, posterPath!!, id!!, adult!!,
				backdropPath!!, originalLanguage!!, originalTitle!!,
				genreIds!!, title!!, voteAverage!!, overview!!, releaseDate!!)


	}
 }