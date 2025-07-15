package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class HomeCricbuzzResponse(

	@field:SerializedName("endPointsLastUpdated")
	val endPointsLastUpdated: String? = null,

	@field:SerializedName("settingsLastUpdated")
	val settingsLastUpdated: String? = null,

	@field:SerializedName("adsLastUpdated")
	val adsLastUpdated: String? = null,

	@field:SerializedName("responseLastUpdated")
	val responseLastUpdated: String? = null,

	@field:SerializedName("surveyLastUpdated")
	val surveyLastUpdated: String? = null,

	@field:SerializedName("matches")
	val matches: List<MatchesItemCric?>? = null,

	@field:SerializedName("homepage")
	val homepage: List<HomepageItem?>? = null
)

data class Team1ScoreCric(

	@field:SerializedName("inngs1")
	val inngs1: Inngs1Cric? = null
)

data class Team2Cric(

	@field:SerializedName("teamName")
	val teamName: String? = null,

	@field:SerializedName("imageId")
	val imageId: Int? = null,

	@field:SerializedName("teamId")
	val teamId: Int? = null,

	@field:SerializedName("teamSName")
	val teamSName: String? = null
)

data class VenueInfoCric(

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("timezone")
	val timezone: String? = null,

	@field:SerializedName("ground")
	val ground: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class Inngs1Cric(

	@field:SerializedName("overs")
	val overs: Any? = null,

	@field:SerializedName("runs")
	val runs: Int? = null,

	@field:SerializedName("inningsId")
	val inningsId: Int? = null,

	@field:SerializedName("wickets")
	val wickets: Int? = null
)

data class Team2ScoreCric(

	@field:SerializedName("inngs1")
	val inngs1: Inngs1Cric? = null
)

data class HomepageItem(

	@field:SerializedName("stories")
	val stories: Stories? = null
)

data class Stories(

	@field:SerializedName("itemId")
	val itemId: Int? = null,

	@field:SerializedName("itemType")
	val itemType: String? = null,

	@field:SerializedName("imageId")
	val imageId: Int? = null,

	@field:SerializedName("appIndexUrl")
	val appIndexUrl: String? = null,

	@field:SerializedName("context")
	val context: String? = null,

	@field:SerializedName("cardType")
	val cardType: String? = null,

	@field:SerializedName("analyticsTag")
	val analyticsTag: String? = null,

	@field:SerializedName("headline")
	val headline: String? = null,

	@field:SerializedName("publishedTime")
	val publishedTime: String? = null,

	@field:SerializedName("intro")
	val intro: String? = null,

	@field:SerializedName("pretag")
	val pretag: String? = null,

	@field:SerializedName("planId")
	val planId: Int? = null
)

data class MatchesItemCric(

	@field:SerializedName("match")
	val match: Match? = null
)

data class Match(

	@field:SerializedName("matchScore")
	val matchScore: MatchScoreCric? = null,

	@field:SerializedName("matchInfo")
	val matchInfo: MatchInfoCric? = null
)

data class MatchScoreCric(

	@field:SerializedName("team1Score")
	val team1Score: Team1ScoreCric? = null,

	@field:SerializedName("team2Score")
	val team2Score: Team2ScoreCric? = null
)

data class Team1Cric(

	@field:SerializedName("teamName")
	val teamName: String? = null,

	@field:SerializedName("imageId")
	val imageId: Int? = null,

	@field:SerializedName("teamId")
	val teamId: Int? = null,

	@field:SerializedName("teamSName")
	val teamSName: String? = null
)

data class MatchInfoCric(

	@field:SerializedName("endDate")
	val endDate: String? = null,

	@field:SerializedName("seriesName")
	val seriesName: String? = null,

	@field:SerializedName("team1")
	val team1: Team1Cric? = null,

	@field:SerializedName("matchType")
	val matchType: String? = null,

	@field:SerializedName("matchDesc")
	val matchDesc: String? = null,

	@field:SerializedName("team2")
	val team2: Team2Cric? = null,

	@field:SerializedName("shortStatus")
	val shortStatus: String? = null,

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
	val venueInfo: VenueInfoCric? = null,

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

	@field:SerializedName("isTournament")
	val isTournament: Boolean? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("isFantasyEnabled")
	val isFantasyEnabled: Boolean? = null,

	@field:SerializedName("dayNight")
	val dayNight: Boolean? = null
)
