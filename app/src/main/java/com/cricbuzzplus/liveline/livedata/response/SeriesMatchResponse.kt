package com.cricbuzzplus.liveline.livedata.response

import com.google.gson.annotations.SerializedName

data class SeriesMatchResponse(

	@field:SerializedName("SeriesMatchResponse")
	val seriesMatchResponse: List<SeriesMatchResponseItem?>? = null
)

data class SeriesMatchResponseItem(

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

	@field:SerializedName("team_a_id")
	val teamAId: Int? = null,

	@field:SerializedName("match_id")
	val matchId: Int? = null,

	@field:SerializedName("max_rate")
	val maxRate: String? = null,

	@field:SerializedName("match_time")
	val matchTime: String? = null,

	@field:SerializedName("team_b_img")
	val teamBImg: String? = null,

	@field:SerializedName("match_date")
	val matchDate: String? = null,

	@field:SerializedName("team_b")
	val teamB: String? = null,

	@field:SerializedName("team_a")
	val teamA: String? = null,

	@field:SerializedName("date_wise")
	val dateWise: String? = null,

	@field:SerializedName("match_type")
	val matchType: String? = null,

	@field:SerializedName("team_b_short")
	val teamBShort: String? = null,

	@field:SerializedName("min_rate")
	val minRate: String? = null
)
