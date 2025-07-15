package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class OversNewResponse(

	@field:SerializedName("matchHeaders")
	val matchHeaders: MatchHeaders? = null,

	@field:SerializedName("miniscore")
	val miniscore: OversNewMiniscore? = null,

	@field:SerializedName("responseLastUpdated")
	val responseLastUpdated: String? = null,

	@field:SerializedName("overSepList")
	val overSepList: OverSepList? = null
)

data class PerformanceItem(

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("runs")
	val runs: Int? = null,

	@field:SerializedName("wickets")
	val wickets: Int? = null
)

data class MomPlayers(
	@field:SerializedName("player")
	val player: List<OversNewPlayerItem?>? = null
)

data class OversNewPlayerItem(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("faceImageId")
	val faceImageId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class OversNewTossResults(

	@field:SerializedName("decision")
	val decision: String? = null,

	@field:SerializedName("tossWinnerName")
	val tossWinnerName: String? = null,

	@field:SerializedName("tossWinnerId")
	val tossWinnerId: Int? = null
)

data class MatchHeaders(

	@field:SerializedName("alertType")
	val alertType: String? = null,

	@field:SerializedName("mosPlayers")
	val mosPlayers: MosPlayers? = null,

	@field:SerializedName("seriesName")
	val seriesName: String? = null,

	@field:SerializedName("team1")
	val team1: OversNewTeam1? = null,

	@field:SerializedName("matchDesc")
	val matchDesc: String? = null,

	@field:SerializedName("team2")
	val team2: OversNewTeam2? = null,

	@field:SerializedName("matchFormat")
	val matchFormat: String? = null,

	@field:SerializedName("seriesId")
	val seriesId: Int? = null,

	@field:SerializedName("matchEndTimeStamp")
	val matchEndTimeStamp: String? = null,

	@field:SerializedName("momPlayers")
	val momPlayers: MomPlayers? = null,

	@field:SerializedName("tossResults")
	val tossResults: OversNewTossResults? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("matchStartTimestamp")
	val matchStartTimestamp: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("teamDetails")
	val teamDetails: TeamDetails? = null
)

data class InningsScoreItem(

	@field:SerializedName("batTeamId")
	val batTeamId: Int? = null,

	@field:SerializedName("balls")
	val balls: Int? = null,

	@field:SerializedName("overs")
	val overs: Any? = null,

	@field:SerializedName("batTeamShortName")
	val batTeamShortName: String? = null,

	@field:SerializedName("runs")
	val runs: Int? = null,

	@field:SerializedName("wickets")
	val wickets: Int? = null,

	@field:SerializedName("inningsId")
	val inningsId: Int? = null
)

data class OversNewBatsmanStriker(

	@field:SerializedName("balls")
	val balls: Int? = null,

	@field:SerializedName("nickName")
	val nickName: String? = null,

	@field:SerializedName("strkRate")
	val strkRate: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class BatTeamScore(

	@field:SerializedName("teamScore")
	val teamScore: Int? = null,

	@field:SerializedName("teamId")
	val teamId: Int? = null,

	@field:SerializedName("teamWkts")
	val teamWkts: Int? = null
)

data class OverSepList(

	@field:SerializedName("overSep")
	val overSep: List<OverSepItem?>? = null
)

data class OversNewBatsmanNonStriker(

	@field:SerializedName("balls")
	val balls: Int? = null,

	@field:SerializedName("nickName")
	val nickName: String? = null,

	@field:SerializedName("strkRate")
	val strkRate: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("runs")
	val runs: Int? = null
)

data class OversNewBowlerStriker(

	@field:SerializedName("maidens")
	val maidens: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("economy")
	val economy: String? = null,

	@field:SerializedName("overs")
	val overs: String? = null,

	@field:SerializedName("wickets")
	val wickets: Int? = null,

	@field:SerializedName("runs")
	val runs: Int? = null
)

data class MosPlayers(
	@field:SerializedName("player")
	val player: List<OversNewPlayerItem?>? = null
)

data class OversNewBowlerNonStriker(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("economy")
	val economy: String? = null,

	@field:SerializedName("overs")
	val overs: String? = null,

	@field:SerializedName("wickets")
	val wickets: Int? = null,

	@field:SerializedName("runs")
	val runs: Int? = null
)

data class OversNewTeam1(

	@field:SerializedName("teamName")
	val teamName: String? = null,

	@field:SerializedName("teamId")
	val teamId: Int? = null,

	@field:SerializedName("teamSName")
	val teamSName: String? = null
)

data class OversNewMiniscore(

	@field:SerializedName("crr")
	val crr: Any? = null,

	@field:SerializedName("partnership")
	val partnership: String? = null,

	@field:SerializedName("bowlerNonStriker")
	val bowlerNonStriker: OversNewBowlerNonStriker? = null,

	@field:SerializedName("udrs")
	val udrs: Udrs? = null,

	@field:SerializedName("inningsNbr")
	val inningsNbr: String? = null,

	@field:SerializedName("responseLastUpdated")
	val responseLastUpdated: String? = null,

	@field:SerializedName("oversRem")
	val oversRem: String? = null,

	@field:SerializedName("batsmanStriker")
	val batsmanStriker: OversNewBatsmanStriker? = null,

	@field:SerializedName("curOvsStats")
	val curOvsStats: String? = null,

	@field:SerializedName("performance")
	val performance: List<PerformanceItem?>? = null,

	@field:SerializedName("inningsScores")
	val inningsScores: InningsScores? = null,

	@field:SerializedName("batTeamScore")
	val batTeamScore: BatTeamScore? = null,

	@field:SerializedName("batsmanNonStriker")
	val batsmanNonStriker: OversNewBatsmanNonStriker? = null,

	@field:SerializedName("lastWkt")
	val lastWkt: String? = null,

	@field:SerializedName("inningsId")
	val inningsId: Int? = null,

	@field:SerializedName("bowlerStriker")
	val bowlerStriker: OversNewBowlerStriker? = null,

	@field:SerializedName("custStatus")
	val custStatus: String? = null
)

data class OverSepItem(

	@field:SerializedName("score")
	val score: Int? = null,

	@field:SerializedName("overNum")
	val overNum: Any? = null,

	@field:SerializedName("battingTeamName")
	val battingTeamName: String? = null,

	@field:SerializedName("overSummary")
	val overSummary: String? = null,

	@field:SerializedName("ovrBatNames")
	val ovrBatNames: List<String?>? = null,

	@field:SerializedName("wickets")
	val wickets: Int? = null,

	@field:SerializedName("runs")
	val runs: Int? = null,

	@field:SerializedName("ovrBowlNames")
	val ovrBowlNames: List<String?>? = null,

	@field:SerializedName("inningsId")
	val inningsId: Int? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null
)

data class Udrs(

	@field:SerializedName("team2Remaining")
	val team2Remaining: Int? = null,

	@field:SerializedName("team2Id")
	val team2Id: Int? = null,

	@field:SerializedName("team1Remaining")
	val team1Remaining: Int? = null,

	@field:SerializedName("team1Id")
	val team1Id: Int? = null,

	@field:SerializedName("team1Successful")
	val team1Successful: Int? = null
)

data class TeamDetails(

	@field:SerializedName("bowlTeamId")
	val bowlTeamId: Int? = null,

	@field:SerializedName("batTeamId")
	val batTeamId: Int? = null,

	@field:SerializedName("batTeamName")
	val batTeamName: String? = null,

	@field:SerializedName("bowlTeamName")
	val bowlTeamName: String? = null
)

data class InningsScores(

	@field:SerializedName("inningsScore")
	val inningsScore: List<InningsScoreItem?>? = null
)

data class OversNewTeam2(

	@field:SerializedName("teamName")
	val teamName: String? = null,

	@field:SerializedName("teamId")
	val teamId: Int? = null,

	@field:SerializedName("teamSName")
	val teamSName: String? = null
)
