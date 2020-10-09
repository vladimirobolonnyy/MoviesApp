package ru.padawans.moviesapp.data.model.upcoming.Dto

import com.google.gson.annotations.SerializedName
import ru.padawans.moviesapp.data.model.upcoming.Dates


class DatesDto (

	@SerializedName("maximum") val maximum : String?,
	@SerializedName("minimum") val minimum : String?
){
	fun converter():Dates{
		return Dates(maximum!!, minimum!!)
	}
 }