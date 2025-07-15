package com.cricbuzzplus.liveline.livedata.response.newresponse

import com.google.gson.annotations.SerializedName

data class HeadToHeadResponse(

	@field:SerializedName("team_a")
	val teamA: HeadToHeadTeam? = null,

	@field:SerializedName("matches")
	val matches: List<MatchesItem?>? = null,

	@field:SerializedName("team_b")
	val teamB: HeadToHeadTeam? = null
)

data class HeadToHeadTeam(

	@field:SerializedName("win_count")
	val winCount: Int? = null,

	@field:SerializedName("flag")
	val flag: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("short_name")
	val shortName: String? = null,

	@field:SerializedName("team_id")
	val teamId: String? = null
)



data class TeamBScoreItem(

	@field:SerializedName("over")
	val over: String? = null,

	@field:SerializedName("score")
	val score: Int? = null,

	@field:SerializedName("wicket")
	val wicket: Int? = null
)

data class TeamAScoreItem(

	@field:SerializedName("over")
	val over: String? = null,

	@field:SerializedName("score")
	val score: Int? = null,

	@field:SerializedName("wicket")
	val wicket: Int? = null
)

data class MatchesItem(

	@field:SerializedName("win_team")
	val winTeam: String? = null,

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("team_b_score")
	val teamBScore: List<TeamBScoreItem?>? = null,

	@field:SerializedName("matchs")
	val matchs: String? = null,

	@field:SerializedName("match_id")
	val matchId: Int? = null,

	@field:SerializedName("team_a_score")
	val teamAScore: List<TeamAScoreItem?>? = null
)
