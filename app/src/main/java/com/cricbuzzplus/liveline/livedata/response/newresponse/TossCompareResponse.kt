package com.cricbuzzplus.liveline.livedata.response.newresponse

import com.google.gson.annotations.SerializedName

data class TossCompareResponse(

	@field:SerializedName("team_a")
	val teamA: TossTeamA? = null,

	@field:SerializedName("team_b")
	val teamB: TossTeamB? = null
)

data class TossTeamA(

	@field:SerializedName("flag")
	val flag: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("short_name")
	val shortName: String? = null,

	@field:SerializedName("team_id")
	val teamId: String? = null,

	@field:SerializedName("toss")
	val toss: List<String?>? = null
)

data class TossTeamB(

	@field:SerializedName("flag")
	val flag: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("short_name")
	val shortName: String? = null,

	@field:SerializedName("team_id")
	val teamId: String? = null,

	@field:SerializedName("toss")
	val toss: List<String?>? = null
)
