package com.cricbuzzplus.liveline.livedata.response.newresponse

import com.google.gson.annotations.SerializedName

data class TeamCompareResponse(

	@field:SerializedName("team_a")
	val teamA: TeamCompareTeamA? = null,

	@field:SerializedName("team_b")
	val teamB: TeamCompareTeamB? = null
)

data class TeamCompareTeamA(

	@field:SerializedName("low_score")
	val lowScore: String? = null,

	@field:SerializedName("flag")
	val flag: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("short_name")
	val shortName: String? = null,

	@field:SerializedName("team_id")
	val teamId: String? = null,

	@field:SerializedName("high_score")
	val highScore: String? = null,

	@field:SerializedName("win")
	val win: Int? = null,

	@field:SerializedName("avg_score")
	val avgScore: Int? = null
)

data class TeamCompareTeamB(

	@field:SerializedName("low_score")
	val lowScore: Int? = null,

	@field:SerializedName("flag")
	val flag: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("short_name")
	val shortName: String? = null,

	@field:SerializedName("team_id")
	val teamId: String? = null,

	@field:SerializedName("high_score")
	val highScore: String? = null,

	@field:SerializedName("win")
	val win: Int? = null,

	@field:SerializedName("avg_score")
	val avgScore: Int? = null
)
