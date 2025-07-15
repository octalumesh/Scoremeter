package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class LiveCommentryCricResponse(

	@field:SerializedName("enableNoContent")
	val enableNoContent: Boolean? = null,

	@field:SerializedName("commentaryList")
	val commentaryList: List<CommentaryListItem?>? = null,

	@field:SerializedName("commentarySnippetList")
	val commentarySnippetList: List<Any?>? = null,

	@field:SerializedName("miniscore")
	val miniscore: Miniscore? = null,

	@field:SerializedName("matchVideos")
	val matchVideos: List<Any?>? = null,

	@field:SerializedName("responseLastUpdated")
	val responseLastUpdated: Int? = null,

	@field:SerializedName("page")
	val page: String? = null,

	@field:SerializedName("matchHeader")
	val matchHeader: CommentaryMatchHeader? = null
)

data class BowlerStriker(

	@field:SerializedName("bowlOvs")
	val bowlOvs: Any? = null,

	@field:SerializedName("bowlId")
	val bowlId: Int? = null,

	@field:SerializedName("bowlName")
	val bowlName: String? = null,

	@field:SerializedName("bowlNoballs")
	val bowlNoballs: Int? = null,

	@field:SerializedName("bowlEcon")
	val bowlEcon: Any? = null,

	@field:SerializedName("bowlMaidens")
	val bowlMaidens: Int? = null,

	@field:SerializedName("bowlWkts")
	val bowlWkts: Int? = null,

	@field:SerializedName("bowlRuns")
	val bowlRuns: Int? = null,

	@field:SerializedName("bowlWides")
	val bowlWides: Int? = null
)

data class CommentaryTossResults(

	@field:SerializedName("decision")
	val decision: String? = null,

	@field:SerializedName("tossWinnerName")
	val tossWinnerName: String? = null,

	@field:SerializedName("tossWinnerId")
	val tossWinnerId: Int? = null
)

data class LatestPerformanceItem(

	@field:SerializedName("wkts")
	val wkts: Int? = null,

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("runs")
	val runs: Int? = null
)

data class BatTeam(

	@field:SerializedName("teamScore")
	val teamScore: Int? = null,

	@field:SerializedName("teamId")
	val teamId: Int? = null,

	@field:SerializedName("teamWkts")
	val teamWkts: Int? = null
)

data class CommentaryResult(

	@field:SerializedName("winningMargin")
	val winningMargin: Int? = null,

	@field:SerializedName("winningTeam")
	val winningTeam: String? = null,

	@field:SerializedName("winByRuns")
	val winByRuns: Boolean? = null,

	@field:SerializedName("winningteamId")
	val winningteamId: Int? = null,

	@field:SerializedName("winByInnings")
	val winByInnings: Boolean? = null,

	@field:SerializedName("resultType")
	val resultType: String? = null
)

data class OverSeparator(

	@field:SerializedName("overNum")
	val overNum: Any? = null,

	@field:SerializedName("batNonStrikerIds")
	val batNonStrikerIds: List<Int?>? = null,

	@field:SerializedName("batNonStrikerRuns")
	val batNonStrikerRuns: Int? = null,

	@field:SerializedName("batStrikerNames")
	val batStrikerNames: List<String?>? = null,

	@field:SerializedName("batNonStrikerBalls")
	val batNonStrikerBalls: Int? = null,

	@field:SerializedName("bowlOvers")
	val bowlOvers: Any? = null,

	@field:SerializedName("bowlWickets")
	val bowlWickets: Int? = null,

	@field:SerializedName("score")
	val score: Int? = null,

	@field:SerializedName("batNonStrikerNames")
	val batNonStrikerNames: List<String?>? = null,

	@field:SerializedName("o_summary")
	val oSummary: String? = null,

	@field:SerializedName("bowlMaidens")
	val bowlMaidens: Int? = null,

	@field:SerializedName("batStrikerIds")
	val batStrikerIds: List<Int?>? = null,

	@field:SerializedName("batStrikerBalls")
	val batStrikerBalls: Int? = null,

	@field:SerializedName("bowlNames")
	val bowlNames: List<String?>? = null,

	@field:SerializedName("batTeamName")
	val batTeamName: String? = null,

	@field:SerializedName("bowlRuns")
	val bowlRuns: Int? = null,

	@field:SerializedName("batStrikerRuns")
	val batStrikerRuns: Int? = null,

	@field:SerializedName("event")
	val event: String? = null,

	@field:SerializedName("wickets")
	val wickets: Int? = null,

	@field:SerializedName("runs")
	val runs: Int? = null,

	@field:SerializedName("bowlIds")
	val bowlIds: List<Int?>? = null,

	@field:SerializedName("inningsId")
	val inningsId: Int? = null,

	@field:SerializedName("timestamp")
	val timestamp: Long? = null
)

data class InningsScoreListItem(

	@field:SerializedName("score")
	val score: Int? = null,

	@field:SerializedName("ballNbr")
	val ballNbr: Int? = null,

	@field:SerializedName("batTeamId")
	val batTeamId: Int? = null,

	@field:SerializedName("isDeclared")
	val isDeclared: Boolean? = null,

	@field:SerializedName("batTeamName")
	val batTeamName: String? = null,

	@field:SerializedName("overs")
	val overs: Any? = null,

	@field:SerializedName("wickets")
	val wickets: Int? = null,

	@field:SerializedName("inningsId")
	val inningsId: Int? = null,

	@field:SerializedName("isFollowOn")
	val isFollowOn: Boolean? = null
)

data class CommentaryPpData(

	@field:SerializedName("pp_1")
	val pp1: CommentaryPp1? = null
)

data class Bold(

	@field:SerializedName("formatId")
	val formatId: List<String?>? = null,

	@field:SerializedName("formatValue")
	val formatValue: List<String?>? = null
)

data class Italic(

	@field:SerializedName("formatId")
	val formatId: List<String?>? = null,

	@field:SerializedName("formatValue")
	val formatValue: List<String?>? = null
)

data class MatchUdrs(

	@field:SerializedName("team2Remaining")
	val team2Remaining: Int? = null,

	@field:SerializedName("team2Id")
	val team2Id: Int? = null,

	@field:SerializedName("team1Unsuccessful")
	val team1Unsuccessful: Int? = null,

	@field:SerializedName("team2Successful")
	val team2Successful: Int? = null,

	@field:SerializedName("team1Remaining")
	val team1Remaining: Int? = null,

	@field:SerializedName("team1Id")
	val team1Id: Int? = null,

	@field:SerializedName("team2Unsuccessful")
	val team2Unsuccessful: Int? = null,

	@field:SerializedName("team1Successful")
	val team1Successful: Int? = null,

	@field:SerializedName("matchId")
	val matchId: Int? = null,

	@field:SerializedName("inningsId")
	val inningsId: Int? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null
)

data class BatsmanStriker(

	@field:SerializedName("batName")
	val batName: String? = null,

	@field:SerializedName("batId")
	val batId: Int? = null,

	@field:SerializedName("batDots")
	val batDots: Int? = null,

	@field:SerializedName("batMins")
	val batMins: Int? = null,

	@field:SerializedName("batFours")
	val batFours: Int? = null,

	@field:SerializedName("batStrikeRate")
	val batStrikeRate: Any? = null,

	@field:SerializedName("batBalls")
	val batBalls: Int? = null,

	@field:SerializedName("batRuns")
	val batRuns: Int? = null,

	@field:SerializedName("batSixes")
	val batSixes: Int? = null
)

data class BowlerNonStriker(

	@field:SerializedName("bowlOvs")
	val bowlOvs: Any? = null,

	@field:SerializedName("bowlId")
	val bowlId: Int? = null,

	@field:SerializedName("bowlName")
	val bowlName: String? = null,

	@field:SerializedName("bowlNoballs")
	val bowlNoballs: Int? = null,

	@field:SerializedName("bowlEcon")
	val bowlEcon: Any? = null,

	@field:SerializedName("bowlMaidens")
	val bowlMaidens: Int? = null,

	@field:SerializedName("bowlWkts")
	val bowlWkts: Int? = null,

	@field:SerializedName("bowlRuns")
	val bowlRuns: Int? = null,

	@field:SerializedName("bowlWides")
	val bowlWides: Int? = null
)

data class CommentaryPp1(

	@field:SerializedName("ppOversFrom")
	val ppOversFrom: Any? = null,

	@field:SerializedName("runsScored")
	val runsScored: Int? = null,

	@field:SerializedName("ppOversTo")
	val ppOversTo: Int? = null,

	@field:SerializedName("ppType")
	val ppType: String? = null,

	@field:SerializedName("ppId")
	val ppId: Int? = null
)

data class Miniscore(

	@field:SerializedName("batTeam")
	val batTeam: BatTeam? = null,

	@field:SerializedName("requiredRunRate")
	val requiredRunRate: Any? = null,

	@field:SerializedName("ppData")
	val ppData: CommentaryPpData? = null,

	@field:SerializedName("bowlerNonStriker")
	val bowlerNonStriker: BowlerNonStriker? = null,

	@field:SerializedName("overSummaryList")
	val overSummaryList: List<Any?>? = null,

	@field:SerializedName("responseLastUpdated")
	val responseLastUpdated: Int? = null,

	@field:SerializedName("latestPerformance")
	val latestPerformance: List<LatestPerformanceItem?>? = null,

	@field:SerializedName("overs")
	val overs: Any? = null,

	@field:SerializedName("currentRunRate")
	val currentRunRate: Any? = null,

	@field:SerializedName("matchUdrs")
	val matchUdrs: MatchUdrs? = null,

	@field:SerializedName("batsmanStriker")
	val batsmanStriker: BatsmanStriker? = null,

	@field:SerializedName("lastWicket")
	val lastWicket: String? = null,

	@field:SerializedName("remRunsToWin")
	val remRunsToWin: Int? = null,

	@field:SerializedName("matchScoreDetails")
	val matchScoreDetails: MatchScoreDetails? = null,

	@field:SerializedName("batsmanNonStriker")
	val batsmanNonStriker: BatsmanNonStriker? = null,

	@field:SerializedName("lastWicketScore")
	val lastWicketScore: Int? = null,

	@field:SerializedName("recentOvsStats")
	val recentOvsStats: String? = null,

	@field:SerializedName("partnerShip")
	val partnerShip: PartnerShip? = null,

	@field:SerializedName("event")
	val event: String? = null,

	@field:SerializedName("inningsId")
	val inningsId: Int? = null,

	@field:SerializedName("bowlerStriker")
	val bowlerStriker: BowlerStriker? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class CommentaryTeam2(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("playerDetails")
	val playerDetails: List<Any?>? = null,

	@field:SerializedName("shortName")
	val shortName: String? = null
)

data class CommentaryTeam1(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("playerDetails")
	val playerDetails: List<Any?>? = null,

	@field:SerializedName("shortName")
	val shortName: String? = null
)

data class CommentaryPlayersOfTheMatchItem(

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

data class PartnerShip(

	@field:SerializedName("balls")
	val balls: Int? = null,

	@field:SerializedName("runs")
	val runs: Int? = null
)

data class CommentaryFormats(

	@field:SerializedName("bold")
	val bold: Bold? = null,

	@field:SerializedName("italic")
	val italic: Italic? = null
)

data class CommentaryMatchHeader(

	@field:SerializedName("playersOfTheSeries")
	val playersOfTheSeries: List<Any?>? = null,

	@field:SerializedName("matchDescription")
	val matchDescription: String? = null,

	@field:SerializedName("year")
	val year: Int? = null,

	@field:SerializedName("matchType")
	val matchType: String? = null,

	@field:SerializedName("matchFormat")
	val matchFormat: String? = null,

	@field:SerializedName("seriesId")
	val seriesId: Int? = null,

	@field:SerializedName("domestic")
	val domestic: Boolean? = null,

	@field:SerializedName("result")
	val result: CommentaryResult? = null,

	@field:SerializedName("livestreamEnabled")
	val livestreamEnabled: Boolean? = null,

	@field:SerializedName("tossResults")
	val tossResults: CommentaryTossResults? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("matchId")
	val matchId: Int? = null,

	@field:SerializedName("matchTeamInfo")
	val matchTeamInfo: List<MatchTeamInfoItem?>? = null,

	@field:SerializedName("alertType")
	val alertType: String? = null,

	@field:SerializedName("playersOfTheMatch")
	val playersOfTheMatch: List<CommentaryPlayersOfTheMatchItem?>? = null,

	@field:SerializedName("matchCompleteTimestamp")
	val matchCompleteTimestamp: Long? = null,

	@field:SerializedName("team1")
	val team1: CommentaryTeam1? = null,

	@field:SerializedName("seriesName")
	val seriesName: String? = null,

	@field:SerializedName("team2")
	val team2: CommentaryTeam2? = null,

	@field:SerializedName("seriesDesc")
	val seriesDesc: String? = null,

	@field:SerializedName("dayNight")
	val dayNight: Boolean? = null,

	@field:SerializedName("isMatchNotCovered")
	val isMatchNotCovered: Boolean? = null,

	@field:SerializedName("complete")
	val complete: Boolean? = null,

	@field:SerializedName("revisedTarget")
	val revisedTarget: CommentaryRevisedTarget? = null,

	@field:SerializedName("matchStartTimestamp")
	val matchStartTimestamp: Long? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class MatchScoreDetails(

	@field:SerializedName("inningsScoreList")
	val inningsScoreList: List<InningsScoreListItem?>? = null,

	@field:SerializedName("customStatus")
	val customStatus: String? = null,

	@field:SerializedName("tossResults")
	val tossResults: CommentaryTossResults? = null,

	@field:SerializedName("isMatchNotCovered")
	val isMatchNotCovered: Boolean? = null,

	@field:SerializedName("highlightedTeamId")
	val highlightedTeamId: Int? = null,

	@field:SerializedName("matchFormat")
	val matchFormat: String? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("matchId")
	val matchId: Int? = null,

	@field:SerializedName("matchTeamInfo")
	val matchTeamInfo: List<CommentaryMatchTeamInfoItem?>? = null
)

data class CommentaryMatchTeamInfoItem(

	@field:SerializedName("battingTeamId")
	val battingTeamId: Int? = null,

	@field:SerializedName("battingTeamShortName")
	val battingTeamShortName: String? = null,

	@field:SerializedName("bowlingTeamId")
	val bowlingTeamId: Int? = null,

	@field:SerializedName("bowlingTeamShortName")
	val bowlingTeamShortName: String? = null
)

data class CommentaryRevisedTarget(

	@field:SerializedName("reason")
	val reason: String? = null
)

data class CommentaryListItem(

	@field:SerializedName("ballNbr")
	val ballNbr: Int? = null,

	@field:SerializedName("commentaryFormats")
	val commentaryFormats: CommentaryFormats? = null,

	@field:SerializedName("batTeamName")
	val batTeamName: String? = null,

	@field:SerializedName("commText")
	val commText: String? = null,

	@field:SerializedName("event")
	val event: String? = null,

	@field:SerializedName("inningsId")
	val inningsId: Int? = null,

	@field:SerializedName("timestamp")
	val timestamp: Long? = null,

	@field:SerializedName("overNumber")
	val overNumber: Any? = null,

	@field:SerializedName("overSeparator")
	val overSeparator: OverSeparator? = null
)

data class BatsmanNonStriker(

	@field:SerializedName("batName")
	val batName: String? = null,

	@field:SerializedName("batId")
	val batId: Int? = null,

	@field:SerializedName("batDots")
	val batDots: Int? = null,

	@field:SerializedName("batMins")
	val batMins: Int? = null,

	@field:SerializedName("batFours")
	val batFours: Int? = null,

	@field:SerializedName("batStrikeRate")
	val batStrikeRate: Any? = null,

	@field:SerializedName("batBalls")
	val batBalls: Int? = null,

	@field:SerializedName("batRuns")
	val batRuns: Int? = null,

	@field:SerializedName("batSixes")
	val batSixes: Int? = null
)
