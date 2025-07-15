package com.cricbuzzplus.liveline.livedata.response

import com.google.gson.annotations.SerializedName

data class MatchOddsResponse(

	@field:SerializedName("MatchOddsResponse")
	val matchOddsResponse: List<MatchOddsResponseItem?>? = null
)

data class MatchOddsResponseItem(

	@field:SerializedName("score")
	val score: String? = null,

	@field:SerializedName("date_time")
	val dateTime: String? = null,

	@field:SerializedName("inning")
	val inning: Int? = null,

	@field:SerializedName("s_min")
	val sMin: String? = null,

	@field:SerializedName("match_odd_id")
	val matchOddId: Int? = null,

	@field:SerializedName("max_rate")
	val maxRate: String? = null,

	@field:SerializedName("s_max")
	val sMax: String? = null,

	@field:SerializedName("team")
	val team: String? = null,

	@field:SerializedName("fav_team")
	val favTeam: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("overs")
	val overs: String? = null,

	@field:SerializedName("runs")
	val runs: String? = null,

	@field:SerializedName("min_rate")
	val minRate: String? = null,

	var newover: Int? = -1
)
