package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class TeamScheduleResponse(

	@field:SerializedName("teamMatchesData")
	val teamMatchesData: List<TeamMatchesDataItem?>? = null
)

data class ScheduleMatchInfo(

	@field:SerializedName("endDate")
	val endDate: String? = null,

	@field:SerializedName("seriesName")
	val seriesName: String? = null,

	@field:SerializedName("team1")
	val team1: ScheduleTeam1? = null,

	@field:SerializedName("matchDesc")
	val matchDesc: String? = null,

	@field:SerializedName("team2")
	val team2: ScheduleTeam2? = null,

	@field:SerializedName("seriesStartDt")
	val seriesStartDt: String? = null,

	@field:SerializedName("seriesEndDt")
	val seriesEndDt: String? = null,

	@field:SerializedName("matchFormat")
	val matchFormat: String? = null,

	@field:SerializedName("seriesId")
	val seriesId: Int? = null,

	@field:SerializedName("venueInfo")
	val venueInfo: ScheduleVenueInfo? = null,

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

data class ScheduleVenueInfo(

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("timezone")
	val timezone: String? = null,

	@field:SerializedName("ground")
	val ground: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class ScheduleMatchItem(

	@field:SerializedName("matchScore")
	val matchScore: MatchScore? = null,

	@field:SerializedName("matchInfo")
	val matchInfo: ScheduleMatchInfo? = null
)

data class TeamMatchesDataItem(

	@field:SerializedName("matchDetailsMap")
	val matchDetailsMap: ScheduleMatchDetailsMap? = null
)

data class ScheduleMatchDetailsMap(

	@field:SerializedName("match")
	val match: List<ScheduleMatchItem?>? = null,

	@field:SerializedName("key")
	val key: String? = null,

	@field:SerializedName("seriesId")
	val seriesId: Int? = null
)

data class ScheduleTeam1(

	@field:SerializedName("teamName")
	val teamName: String? = null,

	@field:SerializedName("imageId")
	val imageId: Int? = null,

	@field:SerializedName("teamId")
	val teamId: Int? = null,

	@field:SerializedName("teamSName")
	val teamSName: String? = null
)

data class ScheduleTeam2(

	@field:SerializedName("teamName")
	val teamName: String? = null,

	@field:SerializedName("imageId")
	val imageId: Int? = null,

	@field:SerializedName("teamId")
	val teamId: Int? = null,

	@field:SerializedName("teamSName")
	val teamSName: String? = null
)
