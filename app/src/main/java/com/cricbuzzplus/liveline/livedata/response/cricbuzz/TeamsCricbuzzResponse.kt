package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class TeamsCricbuzzResponse(

	@field:SerializedName("appIndex")
	val appIndex: TeamsAppIndex? = null,

	@field:SerializedName("list")
	val list: List<ListItem?>? = null
)

data class ListItem(

	@field:SerializedName("teamName")
	val teamName: String? = null,

	@field:SerializedName("imageId")
	val imageId: Int? = null,

	@field:SerializedName("teamId")
	val teamId: Int? = null,

	@field:SerializedName("countryName")
	val countryName: String? = null,

	@field:SerializedName("teamSName")
	val teamSName: String? = null,

	@field:SerializedName("belongsTo")
	val belongsTo: String? = null
)

data class TeamsAppIndex(

	@field:SerializedName("webURL")
	val webURL: String? = null,

	@field:SerializedName("seoTitle")
	val seoTitle: String? = null
)
