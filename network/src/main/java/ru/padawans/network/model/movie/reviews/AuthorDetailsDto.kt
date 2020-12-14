package ru.padawans.network.model.movie.reviews

import com.google.gson.annotations.SerializedName
import ru.padawans.domain.model.movie.reviews.AuthorDetails
import ru.padawans.network.model.getAvatarUrl
import ru.padawans.network.utils.Resolutions

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

		 val path:String = getAvatarUrl(avatarPath, Resolutions.REVIEW)

		 return AuthorDetails(
			 path,
			 name ?: "",
			 rating ?: -1.0,
			 username ?: ""
		 )
	 }
 }