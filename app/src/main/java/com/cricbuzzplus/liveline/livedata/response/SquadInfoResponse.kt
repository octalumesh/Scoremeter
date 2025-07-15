package com.cricbuzzplus.liveline.livedata.response

import com.google.gson.annotations.SerializedName

data class SquadInfoResponse(

	@field:SerializedName("team_a")
	val teamA: TeamA? = null,

	@field:SerializedName("team_b")
	val teamB: TeamB? = null
)

data class TeamB(

	@field:SerializedName("flag")
	val flag: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("short_name")
	val shortName: String? = null,

	@field:SerializedName("player")
	val player: List<PlayerItem?>? = null
)

data class PlayerItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("player_id")
	val playerId: String? = null,

	@field:SerializedName("play_role")
	val playRole: String? = null,

	@field:SerializedName("name")
	val name: String? = null
)

data class TeamA(

	@field:SerializedName("flag")
	val flag: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("short_name")
	val shortName: String? = null,

	@field:SerializedName("player")
	val player: List<PlayerItem?>? = null
)
