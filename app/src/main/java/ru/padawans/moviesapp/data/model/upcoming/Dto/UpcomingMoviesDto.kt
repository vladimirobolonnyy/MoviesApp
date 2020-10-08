package ru.padawans.moviesapp.data.model.upcoming.Dto

import com.google.gson.annotations.SerializedName
import ru.padawans.moviesapp.data.model.upcoming.Dates
import ru.padawans.moviesapp.data.model.upcoming.Results
import ru.padawans.moviesapp.data.model.upcoming.UpcomingMovies


class UpcomingMoviesDto (

	@SerializedName("results") val results : List<ResultsDto>?,
	@SerializedName("page") val page : Int?,
	@SerializedName("total_results") val total_results : Int?,
	@SerializedName("dates") val dates : DatesDto?,
	@SerializedName("total_pages") val total_pages : Int? ){

	 fun convert():UpcomingMovies{
		 val resultsModel = results?.map { t->
			 t.convert() }

		 val dateModel:Dates? = dates?.converter()

			 return UpcomingMovies(resultsModel,page,total_results,dateModel,total_pages,1)
		 }
	 }



