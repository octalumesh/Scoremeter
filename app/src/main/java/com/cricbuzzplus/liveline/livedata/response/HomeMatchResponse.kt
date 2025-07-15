package com.cricbuzzplus.liveline.livedata.response

import com.google.gson.annotations.SerializedName

data class HomeMatchResponse(

	@field:SerializedName("HomeMatchResponse")
	val homeMatchResponse: List<HomeMatchResponseItem?>? = null
)

data class HomeMatchResponseItem(

	@field:SerializedName("fav_team")
	val favTeam: String? = null,

	@field:SerializedName("venue")
	val venue: String? = null,

	@field:SerializedName("team_a_short")
	val teamAShort: String? = null,

	@field:SerializedName("matchs")
	val matchs: String? = null,

	@field:SerializedName("team_a_img")
	val teamAImg: String? = null,

	@field:SerializedName("team_b_id")
	val teamBId: Int? = null,

	@field:SerializedName("series_id")
	val seriesId: Int? = null,

	@field:SerializedName("session")
	val session: Any? = null,

	@field:SerializedName("team_a_id")
	val teamAId: Int? = null,

	@field:SerializedName("match_id")
	val matchId: Int? = null,

	@field:SerializedName("s_min")
	val sMin: String? = null,

	@field:SerializedName("max_rate")
	val maxRate: String? = null,

	@field:SerializedName("match_time")
	val matchTime: String? = null,

	@field:SerializedName("team_b_img")
	val teamBImg: String? = null,

	@field:SerializedName("match_status")
	val matchStatus: String? = null,

	@field:SerializedName("match_date")
	val matchDate: String? = null,

	@field:SerializedName("team_b")
	val teamB: String? = null,

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("toss")
	val toss: String? = null,

	@field:SerializedName("team_a")
	val teamA: String? = null,

	@field:SerializedName("series")
	val series: String? = null,

	@field:SerializedName("match_type")
	val matchType: String? = null,

	@field:SerializedName("s_max")
	val sMax: String? = null,

	@field:SerializedName("team_b_short")
	val teamBShort: String? = null,

	@field:SerializedName("min_rate")
	val minRate: String? = null,

	@field:SerializedName("s_ovr")
	val sOvr: String? = null,

    @field:SerializedName("team_a_scores")
    val teamAScores: String? = null,

    @field:SerializedName("team_b_scores")
    val teamBScores: String? = null,


    @field:SerializedName("team_a_over")
    val teamAOver: String? = null,

    @field:SerializedName("team_b_over")
    val teamBOver: String? = null,

	@field:SerializedName("team_a_score")
	val teamAScore: TeamAScore? = null,

	@field:SerializedName("team_b_score")
	val teamBScore: TeamBScore? = null
)

data class JsonMember1(

	@field:SerializedName("over")
	val over: String? = null,

	@field:SerializedName("score")
	val score: Int? = null,

	@field:SerializedName("wicket")
	val wicket: Int? = null
)

data class TeamAScore(

	@field:SerializedName("1")
	val jsonMember1: JsonMember1? = null,

	@field:SerializedName("3")
	val jsonMember3: JsonMember3? = null,

	@field:SerializedName("2")
	val jsonMember2: JsonMember2? = null,

	@field:SerializedName("4")
	val jsonMember4: JsonMember4? = null

)

data class JsonMember2(

	@field:SerializedName("over")
	val over: String? = null,

	@field:SerializedName("score")
	val score: Int? = null,

	@field:SerializedName("wicket")
	val wicket: Int? = null
)

data class JsonMember3(

	@field:SerializedName("over")
	val over: String? = null,

	@field:SerializedName("score")
	val score: Int? = null,

	@field:SerializedName("wicket")
	val wicket: Int? = null
)

data class JsonMember4(

	@field:SerializedName("over")
	val over: String? = null,

	@field:SerializedName("score")
	val score: Int? = null,

	@field:SerializedName("wicket")
	val wicket: Int? = null

)

data class TeamBScore(

	@field:SerializedName("2")
	val jsonMember2: JsonMember2? = null,

	@field:SerializedName("1")
	val jsonMember1: JsonMember1? = null,

	@field:SerializedName("3")
	val jsonMember3: JsonMember3? = null,

	@field:SerializedName("4")
	val jsonMember4: JsonMember4? = null
)
