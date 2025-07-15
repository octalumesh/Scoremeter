package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class TeamPlayersResponse(

	@field:SerializedName("player")
	val player: List<TeamPlayerItem?>? = null
)

data class TeamPlayerItem(

	@field:SerializedName("bowlingStyle")
	val bowlingStyle: String? = null,

	@field:SerializedName("imageId")
	val imageId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("battingStyle")
	val battingStyle: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
