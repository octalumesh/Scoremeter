package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class StadiumMatchesResponse(

	@field:SerializedName("matchDetails")
	val matchDetails: List<MatchDetailsItem?>? = null
)

data class MatchDetailsMap(

	@field:SerializedName("match")
	val match: List<MatchItem?>? = null,

	@field:SerializedName("key")
	val key: String? = null,

	@field:SerializedName("seriesId")
	val seriesId: Int? = null
)

data class StadiumMatchesVenueInfo(

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("timezone")
	val timezone: String? = null,

	@field:SerializedName("ground")
	val ground: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class StadiumMatchesMatchInfo(

	@field:SerializedName("endDate")
	val endDate: String? = null,

	@field:SerializedName("seriesName")
	val seriesName: String? = null,

	@field:SerializedName("team1")
	val team1: StadiumMatchesTeam1? = null,

	@field:SerializedName("matchDesc")
	val matchDesc: String? = null,

	@field:SerializedName("team2")
	val team2: StadiumMatchesTeam2? = null,

	@field:SerializedName("seriesStartDt")
	val seriesStartDt: String? = null,

	@field:SerializedName("seriesEndDt")
	val seriesEndDt: String? = null,

	@field:SerializedName("matchFormat")
	val matchFormat: String? = null,

	@field:SerializedName("seriesId")
	val seriesId: Int? = null,

	@field:SerializedName("venueInfo")
	val venueInfo: StadiumMatchesVenueInfo? = null,

	@field:SerializedName("isTimeAnnounced")
	val isTimeAnnounced: Boolean? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("matchId")
	val matchId: Int? = null,

	@field:SerializedName("startDate")
	val startDate: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class MatchDetailsItem(

	@field:SerializedName("matchDetailsMap")
	val matchDetailsMap: MatchDetailsMap? = null
)

data class StadiumMatchesTeam1(

	@field:SerializedName("teamName")
	val teamName: String? = null,

	@field:SerializedName("imageId")
	val imageId: Int? = null,

	@field:SerializedName("teamId")
	val teamId: Int? = null,

	@field:SerializedName("teamSName")
	val teamSName: String? = null
)

data class MatchItem(

	@field:SerializedName("matchInfo")
	val matchInfo: StadiumMatchesMatchInfo? = null
)

data class StadiumMatchesTeam2(

	@field:SerializedName("teamName")
	val teamName: String? = null,

	@field:SerializedName("imageId")
	val imageId: Int? = null,

	@field:SerializedName("teamId")
	val teamId: Int? = null,

	@field:SerializedName("teamSName")
	val teamSName: String? = null
)
