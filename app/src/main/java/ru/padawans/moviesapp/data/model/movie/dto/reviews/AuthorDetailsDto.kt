package ru.padawans.moviesapp.data.model.movie.dto.reviews

import com.google.gson.annotations.SerializedName
import ru.padawans.moviesapp.data.model.movie.reviews.AuthorDetails
import ru.padawans.moviesapp.utils.Resolutions
import ru.padawans.moviesapp.data.model.getAvatarUrl

class AuthorDetailsDto(

	@field:SerializedName("avatar_path")
	val avatarPath: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("rating")
	val rating: Double? = null,

	@field:SerializedName("username")
	val username: String? = null
){
	 fun convert():AuthorDetails{

		 val path:String = getAvatarUrl(avatarPath,Resolutions.REVIEW)

		 return AuthorDetails(
			 path,
			 name ?: "",
			 rating ?: -1.0,
			 username ?: ""
		 )
	 }
 }