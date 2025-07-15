package com.cricbuzzplus.liveline.livedata.response.newresponse

import com.google.gson.annotations.SerializedName

data class TeamFormResponse(

	@field:SerializedName("team_a")
	val teamA: TeamFormTeamA? = null,

	@field:SerializedName("team_b")
	val teamB: TeamFormTeamB? = null
)

data class TeamFormTeamB(

	@field:SerializedName("flag")
	val flag: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("short_name")
	val shortName: String? = null,

	@field:SerializedName("team_id")
	val teamId: String? = null,

	@field:SerializedName("forms")
	val forms: List<String?>? = null
)

data class TeamFormTeamA(

	@field:SerializedName("flag")
	val flag: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("short_name")
	val shortName: String? = null,

	@field:SerializedName("team_id")
	val teamId: String? = null,

	@field:SerializedName("forms")
	val forms: List<String?>? = null
)
