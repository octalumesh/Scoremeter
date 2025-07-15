package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class ScheduleMatchResponse(

	@field:SerializedName("startDt")
	val startDt: List<String?>? = null,

	@field:SerializedName("matchScheduleMap")
	val matchScheduleMap: List<MatchScheduleMapItem?>? = null
)

data class ScheduleAdWrapper(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("longDate")
	val longDate: String? = null,

	@field:SerializedName("matchScheduleList")
	val matchScheduleList: List<MatchScheduleListItem?>? = null
)

data class MatchScheduleMapItem(

	@field:SerializedName("scheduleAdWrapper")
	val scheduleAdWrapper: ScheduleAdWrapper? = null
)

data class ScheduleMatchTeam2(

	@field:SerializedName("teamName")
	val teamName: String? = null,

	@field:SerializedName("imageId")
	val imageId: Int? = null,

	@field:SerializedName("teamId")
	val teamId: Int? = null,

	@field:SerializedName("teamSName")
	val teamSName: String? = null,

	@field:SerializedName("isFullMember")
	val isFullMember: Boolean? = null
)

data class MatchInfoItem(

	@field:SerializedName("endDate")
	val endDate: String? = null,

	@field:SerializedName("venueInfo")
	val venueInfo: ScheduleMatchVenueInfo? = null,

	@field:SerializedName("team1")
	val team1: ScheduleMatchTeam1? = null,

	@field:SerializedName("matchDesc")
	val matchDesc: String? = null,

	@field:SerializedName("team2")
	val team2: ScheduleMatchTeam2? = null,

	@field:SerializedName("matchFormat")
	val matchFormat: String? = null,

	@field:SerializedName("matchId")
	val matchId: Int? = null,

	@field:SerializedName("seriesId")
	val seriesId: Int? = null,

	@field:SerializedName("startDate")
	val startDate: String? = null
)

data class MatchScheduleListItem(

	@field:SerializedName("seriesCategory")
	val seriesCategory: String? = null,

	@field:SerializedName("seriesName")
	val seriesName: String? = null,

	@field:SerializedName("matchInfo")
	val matchInfo: List<MatchInfoItem?>? = null,

	@field:SerializedName("seriesId")
	val seriesId: Int? = null,

	@field:SerializedName("seriesHomeCountry")
	val seriesHomeCountry: Int? = null
)

data class ScheduleMatchTeam1(

	@field:SerializedName("teamName")
	val teamName: String? = null,

	@field:SerializedName("imageId")
	val imageId: Int? = null,

	@field:SerializedName("teamId")
	val teamId: Int? = null,

	@field:SerializedName("teamSName")
	val teamSName: String? = null,

	@field:SerializedName("isFullMember")
	val isFullMember: Boolean? = null
)

data class ScheduleMatchVenueInfo(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("timezone")
	val timezone: String? = null,

	@field:SerializedName("ground")
	val ground: String? = null
)
