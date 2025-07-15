package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class StadiumInfoResponse(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("imageId")
	val imageId: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("timezone")
	val timezone: String? = null,

	@field:SerializedName("knownAs")
	val knownAs: String? = null,

	@field:SerializedName("ends")
	val ends: String? = null,

	@field:SerializedName("floodlights")
	val floodlights: Boolean? = null,

	@field:SerializedName("profile")
	val profile: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("homeTeam")
	val homeTeam: String? = null,

	@field:SerializedName("ground")
	val ground: String? = null,

	@field:SerializedName("capacity")
	val capacity: String? = null
)
