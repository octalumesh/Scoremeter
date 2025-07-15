package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class MatchByIdResponse(

	@field:SerializedName("venueInfo")
	val venueInfo: MatchByIdVenueInfo? = null,

	@field:SerializedName("matchInfo")
	val matchInfo: MatchByIdMatchInfo? = null,

	@field:SerializedName("broadcastInfo")
	val broadcastInfo: List<BroadcastInfoItem?>? = null
)

data class Umpire3(
	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,
)

data class PlayerDetailsItem(

	@field:SerializedName("bowlingStyle")
	val bowlingStyle: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("nickName")
	val nickName: String? = null,

	@field:SerializedName("faceImageId")
	val faceImageId: Int? = null,

	@field:SerializedName("keeper")
	val keeper: Boolean? = null,

	@field:SerializedName("teamId")
	val teamId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("battingStyle")
	val battingStyle: String? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("captain")
	val captain: Boolean? = null,

	@field:SerializedName("substitute")
	val substitute: Boolean? = null
)

data class MatchByIdMatchInfo(

	@field:SerializedName("venue")
	val venue: Venue? = null,

	@field:SerializedName("playersOfTheSeries")
	val playersOfTheSeries: List<Any?>? = null,

	@field:SerializedName("HYSEnabled")
	val hYSEnabled: Int? = null,

	@field:SerializedName("matchDescription")
	val matchDescription: String? = null,

	@field:SerializedName("year")
	val year: Int? = null,

	@field:SerializedName("matchType")
	val matchType: String? = null,

	@field:SerializedName("matchFormat")
	val matchFormat: String? = null,

	@field:SerializedName("referee")
	val referee: Referee? = null,

	@field:SerializedName("domestic")
	val domestic: Boolean? = null,

	@field:SerializedName("result")
	val result: Result? = null,

	@field:SerializedName("livestreamEnabled")
	val livestreamEnabled: Boolean? = null,

	@field:SerializedName("tossResults")
	val tossResults: TossResults? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("matchId")
	val matchId: Int? = null,

	@field:SerializedName("alertType")
	val alertType: String? = null,

	@field:SerializedName("playersOfTheMatch")
	val playersOfTheMatch: List<PlayersOfTheMatch?>? = null,

	@field:SerializedName("matchCompleteTimestamp")
	val matchCompleteTimestamp: Long? = null,

	@field:SerializedName("team1")
	val team1: MatchByIdTeam1? = null,

	@field:SerializedName("team2")
	val team2: MatchByIdTeam2? = null,

	@field:SerializedName("isFantasyEnabled")
	val isFantasyEnabled: Boolean? = null,

	@field:SerializedName("livestreamEnabledGeo")
	val livestreamEnabledGeo: List<Any?>? = null,

	@field:SerializedName("dayNight")
	val dayNight: Boolean? = null,

	@field:SerializedName("series")
	val series: Series? = null,

	@field:SerializedName("umpire1")
	val umpire1: Umpire1? = null,

	@field:SerializedName("umpire3")
	val umpire3: Umpire3? = null,

	@field:SerializedName("isMatchNotCovered")
	val isMatchNotCovered: Boolean? = null,

	@field:SerializedName("umpire2")
	val umpire2: Umpire2? = null,

	@field:SerializedName("complete")
	val complete: Boolean? = null,

	@field:SerializedName("revisedTarget")
	val revisedTarget: RevisedTarget? = null,

	@field:SerializedName("matchStartTimestamp")
	val matchStartTimestamp: Long? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class PlayersOfTheMatch(

	@field:SerializedName("teamName")
	val teamName: String? = null,

	@field:SerializedName("nickName")
	val nickName: String? = null,

	@field:SerializedName("faceImageId")
	val faceImageId: Int? = null,

	@field:SerializedName("keeper")
	val keeper: Boolean? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("captain")
	val captain: Boolean? = null,

	@field:SerializedName("substitute")
	val substitute: Boolean? = null
)

data class Result(

	@field:SerializedName("winningTeam")
	val winningTeam: String? = null,

	@field:SerializedName("winByRuns")
	val winByRuns: Boolean? = null,

	@field:SerializedName("winByInnings")
	val winByInnings: Boolean? = null
)

data class BroadcasterItem(

	@field:SerializedName("value")
	val value: String? = null,

	@field:SerializedName("broadcastType")
	val broadcastType: String? = null
)

data class MatchByIdVenueInfo(

	@field:SerializedName("established")
	val established: Int? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("groundWidth")
	val groundWidth: Double? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("timezone")
	val timezone: String? = null,

	@field:SerializedName("floodlights")
	val floodlights: Boolean? = null,

	@field:SerializedName("profile")
	val profile: String? = null,

	@field:SerializedName("capacity")
	val capacity: String? = null,

	@field:SerializedName("curator")
	val curator: String? = null,

	@field:SerializedName("otherSports")
	val otherSports: Any? = null,

	@field:SerializedName("knownAs")
	val knownAs: Any? = null,

	@field:SerializedName("ends")
	val ends: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("homeTeam")
	val homeTeam: String? = null,

	@field:SerializedName("ground")
	val ground: String? = null,

	@field:SerializedName("groundLength")
	val groundLength: Double? = null
)

data class Series(

	@field:SerializedName("endDate")
	val endDate: Long? = null,

	@field:SerializedName("seriesType")
	val seriesType: String? = null,

	@field:SerializedName("seriesFolder")
	val seriesFolder: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("odiSeriesResult")
	val odiSeriesResult: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("t20SeriesResult")
	val t20SeriesResult: String? = null,

	@field:SerializedName("tournament")
	val tournament: Boolean? = null,

	@field:SerializedName("startDate")
	val startDate: Long? = null,

	@field:SerializedName("testSeriesResult")
	val testSeriesResult: String? = null
)

data class TossResults(

	@field:SerializedName("tossWinnerId")
	val tossWinnerId: Any? = null,

	@field:SerializedName("decision")
	val decision: String? = null,

	@field:SerializedName("tossWinnerName")
	val tossWinnerName: String? = null
)

data class Umpire2(
	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,
)

data class MatchByIdTeam2(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("playerDetails")
	val playerDetails: List<PlayerDetailsItem?>? = null,

	@field:SerializedName("shortName")
	val shortName: String? = null
)

data class RevisedTarget(

	@field:SerializedName("reason")
	val reason: String? = null
)

data class MatchByIdTeam1(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("playerDetails")
	val playerDetails: List<PlayerDetailsItem?>? = null,

	@field:SerializedName("shortName")
	val shortName: String? = null
)

data class Umpire1(
	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,
)

data class Referee(
	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,
)

data class Venue(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("timezone")
	val timezone: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null
)

data class BroadcastInfoItem(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("broadcaster")
	val broadcaster: List<BroadcasterItem?>? = null
)
