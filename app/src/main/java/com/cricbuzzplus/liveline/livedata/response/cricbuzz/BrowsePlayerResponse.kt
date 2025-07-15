package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class BrowsePlayerResponse(

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("player")
	val player: List<PlayerItem?>? = null
)

data class PlayerItem(

	@field:SerializedName("teamName")
	val teamName: String? = null,

	@field:SerializedName("faceImageId")
	val faceImageId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
