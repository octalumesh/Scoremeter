package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class SeriesSquadMemberResponse(

	@field:SerializedName("player")
	val player: List<PlayerItemSquad?>? = null
)

data class PlayerItemSquad(

	@field:SerializedName("bowlingStyle")
	val bowlingStyle: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("imageId")
	val imageId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("battingStyle")
	val battingStyle: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("isHeader")
	val isHeader: Boolean? = null,

	@field:SerializedName("captain")
	val captain: Boolean? = null
)
