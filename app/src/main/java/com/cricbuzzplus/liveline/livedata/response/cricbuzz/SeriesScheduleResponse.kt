package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class SeriesScheduleResponse(

	@field:SerializedName("matchDetails")
	val matchDetails: List<SeriesScheduleMatchDetailsItem?>? = null
)

data class SeriesScheduleTeam2(

	@field:SerializedName("teamName")
	val teamName: String? = null,

	@field:SerializedName("imageId")
	val imageId: Int? = null,

	@field:SerializedName("teamId")
	val teamId: Int? = null,

	@field:SerializedName("teamSName")
	val teamSName: String? = null
)

data class SeriesScheduleMatchInfo(

	@field:SerializedName("endDate")
	val endDate: String? = null,

	@field:SerializedName("seriesName")
	val seriesName: String? = null,

	@field:SerializedName("team1")
	val team1: SeriesScheduleTeam1? = null,

	@field:SerializedName("matchDesc")
	val matchDesc: String? = null,

	@field:SerializedName("team2")
	val team2: SeriesScheduleTeam2? = null,

	@field:SerializedName("seriesStartDt")
	val seriesStartDt: String? = null,

	@field:SerializedName("seriesEndDt")
	val seriesEndDt: String? = null,

	@field:SerializedName("matchFormat")
	val matchFormat: String? = null,

	@field:SerializedName("seriesId")
	val seriesId: Int? = null,

	@field:SerializedName("venueInfo")
	val venueInfo: SeriesScheduleVenueInfo? = null,

	@field:SerializedName("isTimeAnnounced")
	val isTimeAnnounced: Boolean? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("matchId")
	val matchId: Int? = null,

	@field:SerializedName("startDate")
	val startDate: String? = null,

	@field:SerializedName("currBatTeamId")
	val currBatTeamId: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Inngs1(

	@field:SerializedName("overs")
	val overs: Any? = null,

	@field:SerializedName("runs")
	val runs: Int? = null,

	@field:SerializedName("wickets")
	val wickets: Int? = null,

	@field:SerializedName("inningsId")
	val inningsId: Int? = null,

	@field:SerializedName("isDeclared")
	val isDeclared: Boolean? = null
)

data class SeriesScheduleMatchDetailsItem(

	@field:SerializedName("matchDetailsMap")
	val matchDetailsMap: SeriesScheduleMatchDetailsMap? = null
)

data class SeriesScheduleMatchDetailsMap(

	@field:SerializedName("match")
	val match: List<SeriesScheduleMatchItem?>? = null,

	@field:SerializedName("key")
	val key: String? = null,

	@field:SerializedName("seriesId")
	val seriesId: Int? = null
)

data class Inngs2(

	@field:SerializedName("overs")
	val overs: Any? = null,

	@field:SerializedName("runs")
	val runs: Int? = null,

	@field:SerializedName("wickets")
	val wickets: Int? = null,

	@field:SerializedName("inningsId")
	val inningsId: Int? = null,

	@field:SerializedName("isDeclared")
	val isDeclared: Boolean? = null
)

data class MatchScore(

	@field:SerializedName("team1Score")
	val team1Score: Team1Score? = null,

	@field:SerializedName("team2Score")
	val team2Score: Team2Score? = null
)

data class SeriesScheduleMatchItem(

	@field:SerializedName("matchScore")
	val matchScore: MatchScore? = null,

	@field:SerializedName("matchInfo")
	val matchInfo: SeriesScheduleMatchInfo? = null
)

data class Team2Score(

	@field:SerializedName("inngs1")
	val inngs1: Inngs1? = null,

	@field:SerializedName("inngs2")
	val inngs2: Inngs2? = null
)

data class SeriesScheduleVenueInfo(

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("timezone")
	val timezone: String? = null,

	@field:SerializedName("ground")
	val ground: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class SeriesScheduleTeam1(

	@field:SerializedName("teamName")
	val teamName: String? = null,

	@field:SerializedName("imageId")
	val imageId: Int? = null,

	@field:SerializedName("teamId")
	val teamId: Int? = null,

	@field:SerializedName("teamSName")
	val teamSName: String? = null
)

data class Team1Score(

	@field:SerializedName("inngs1")
	val inngs1: Inngs1? = null,

	@field:SerializedName("inngs2")
	val inngs2: Inngs2? = null
)
