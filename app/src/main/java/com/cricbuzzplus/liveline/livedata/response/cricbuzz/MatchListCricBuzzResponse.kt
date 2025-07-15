package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class MatchListCricBuzzResponse(

	@field:SerializedName("responseLastUpdated")
	val responseLastUpdated: String? = null,

	@field:SerializedName("filters")
	val filters: Filters? = null,

	@field:SerializedName("typeMatches")
	val typeMatches: List<TypeMatchesItem?>? = null
)

data class VenueInfo(

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("timezone")
	val timezone: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("ground")
	val ground: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null
)

data class MatchesItem(

	@field:SerializedName("matchInfo")
	val matchInfo: MatchInfo? = null
)

data class Filters(

	@field:SerializedName("matchType")
	val matchType: List<String?>? = null
)

data class MatchInfo(

	@field:SerializedName("endDate")
	val endDate: String? = null,

	@field:SerializedName("seriesName")
	val seriesName: String? = null,

	@field:SerializedName("team1")
	val team1: Team1? = null,

	@field:SerializedName("matchDesc")
	val matchDesc: String? = null,

	@field:SerializedName("team2")
	val team2: Team2? = null,

	@field:SerializedName("seriesStartDt")
	val seriesStartDt: String? = null,

	@field:SerializedName("stateTitle")
	val stateTitle: String? = null,

	@field:SerializedName("seriesEndDt")
	val seriesEndDt: String? = null,

	@field:SerializedName("matchFormat")
	val matchFormat: String? = null,

	@field:SerializedName("seriesId")
	val seriesId: Int? = null,

	@field:SerializedName("venueInfo")
	val venueInfo: VenueInfo? = null,

	@field:SerializedName("isTimeAnnounced")
	val isTimeAnnounced: Boolean? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("matchId")
	val matchId: Int? = null,

	@field:SerializedName("startDate")
	val startDate: String? = null,

	@field:SerializedName("isFantasyEnabled")
	val isFantasyEnabled: Boolean? = null
)

data class Team2(

	@field:SerializedName("teamName")
	val teamName: String? = null,

	@field:SerializedName("imageId")
	val imageId: Int? = null,

	@field:SerializedName("teamId")
	val teamId: Int? = null,

	@field:SerializedName("teamSName")
	val teamSName: String? = null
)

data class SeriesMatchesItem(

	@field:SerializedName("seriesAdWrapper")
	val seriesAdWrapper: SeriesAdWrapper? = null
)

data class SeriesAdWrapper(

	@field:SerializedName("seriesName")
	val seriesName: String? = null,

	@field:SerializedName("matches")
	val matches: List<MatchesItem?>? = null,

	@field:SerializedName("seriesId")
	val seriesId: Int? = null
)

data class TypeMatchesItem(

	@field:SerializedName("matchType")
	val matchType: String? = null,

	@field:SerializedName("seriesMatches")
	val seriesMatches: List<SeriesMatchesItem?>? = null
)

data class Team1(

	@field:SerializedName("teamName")
	val teamName: String? = null,

	@field:SerializedName("imageId")
	val imageId: Int? = null,

	@field:SerializedName("teamId")
	val teamId: Int? = null,

	@field:SerializedName("teamSName")
	val teamSName: String? = null
)
