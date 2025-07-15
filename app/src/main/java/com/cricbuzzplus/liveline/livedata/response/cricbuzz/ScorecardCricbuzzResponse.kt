package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class ScorecardCricbuzzResponse(

	@field:SerializedName("isMatchComplete")
	val isMatchComplete: Boolean? = null,

	@field:SerializedName("videos")
	val videos: List<Any?>? = null,

	@field:SerializedName("responseLastUpdated")
	val responseLastUpdated: Int? = null,

	@field:SerializedName("matchHeader")
	val matchHeader: MatchHeader? = null,

	@field:SerializedName("scoreCard")
	val scoreCard: List<ScoreCardItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class ScorecardResult(

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

data class Pat6(

	@field:SerializedName("bat2fours")
	val bat2fours: Int? = null,

	@field:SerializedName("bat2balls")
	val bat2balls: Int? = null,

	@field:SerializedName("bat2Sixers")
	val bat2Sixers: Int? = null,

	@field:SerializedName("bat2Twos")
	val bat2Twos: Int? = null,

	@field:SerializedName("bat1Ones")
	val bat1Ones: Int? = null,

	@field:SerializedName("bat1Boundaries")
	val bat1Boundaries: Int? = null,

	@field:SerializedName("bat1fours")
	val bat1fours: Int? = null,

	@field:SerializedName("bat1Threes")
	val bat1Threes: Int? = null,

	@field:SerializedName("bat2sixes")
	val bat2sixes: Int? = null,

	@field:SerializedName("bat1Name")
	val bat1Name: String? = null,

	@field:SerializedName("bat1Runs")
	val bat1Runs: Int? = null,

	@field:SerializedName("bat1balls")
	val bat1balls: Int? = null,

	@field:SerializedName("totalBalls")
	val totalBalls: Int? = null,

	@field:SerializedName("bat2Threes")
	val bat2Threes: Int? = null,

	@field:SerializedName("bat1Fives")
	val bat1Fives: Int? = null,

	@field:SerializedName("bat1Sixers")
	val bat1Sixers: Int? = null,

	@field:SerializedName("bat2Runs")
	val bat2Runs: Int? = null,

	@field:SerializedName("bat2Ones")
	val bat2Ones: Int? = null,

	@field:SerializedName("bat1sixes")
	val bat1sixes: Int? = null,

	@field:SerializedName("bat2Name")
	val bat2Name: String? = null,

	@field:SerializedName("bat1Id")
	val bat1Id: Int? = null,

	@field:SerializedName("bat2Fives")
	val bat2Fives: Int? = null,

	@field:SerializedName("totalRuns")
	val totalRuns: Int? = null,

	@field:SerializedName("bat2Boundaries")
	val bat2Boundaries: Int? = null,

	@field:SerializedName("bat2Id")
	val bat2Id: Int? = null,

	@field:SerializedName("bat1Twos")
	val bat1Twos: Int? = null
)

data class ScorecardTeam2(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("playerDetails")
	val playerDetails: List<Any?>? = null,

	@field:SerializedName("shortName")
	val shortName: String? = null
)

data class MatchHeader(

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
	val result: ScorecardResult? = null,

	@field:SerializedName("livestreamEnabled")
	val livestreamEnabled: Boolean? = null,

	@field:SerializedName("tossResults")
	val tossResults: ScorecardTossResults? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("matchId")
	val matchId: Int? = null,

	@field:SerializedName("matchTeamInfo")
	val matchTeamInfo: List<MatchTeamInfoItem?>? = null,

	@field:SerializedName("alertType")
	val alertType: String? = null,

	@field:SerializedName("playersOfTheMatch")
	val playersOfTheMatch: List<PlayersOfTheMatchItem?>? = null,

	@field:SerializedName("matchCompleteTimestamp")
	val matchCompleteTimestamp: Long? = null,

	@field:SerializedName("team1")
	val team1: ScorecardTeam1? = null,

	@field:SerializedName("seriesName")
	val seriesName: String? = null,

	@field:SerializedName("team2")
	val team2: ScorecardTeam2? = null,

	@field:SerializedName("seriesDesc")
	val seriesDesc: String? = null,

	@field:SerializedName("dayNight")
	val dayNight: Boolean? = null,

	@field:SerializedName("isMatchNotCovered")
	val isMatchNotCovered: Boolean? = null,

	@field:SerializedName("complete")
	val complete: Boolean? = null,

	@field:SerializedName("revisedTarget")
	val revisedTarget: ScorecardRevisedTarget? = null,

	@field:SerializedName("matchStartTimestamp")
	val matchStartTimestamp: Long? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Pat1(

	@field:SerializedName("bat2fours")
	val bat2fours: Int? = null,

	@field:SerializedName("bat2balls")
	val bat2balls: Int? = null,

	@field:SerializedName("bat2Sixers")
	val bat2Sixers: Int? = null,

	@field:SerializedName("bat2Twos")
	val bat2Twos: Int? = null,

	@field:SerializedName("bat1Ones")
	val bat1Ones: Int? = null,

	@field:SerializedName("bat1Boundaries")
	val bat1Boundaries: Int? = null,

	@field:SerializedName("bat1fours")
	val bat1fours: Int? = null,

	@field:SerializedName("bat1Threes")
	val bat1Threes: Int? = null,

	@field:SerializedName("bat2sixes")
	val bat2sixes: Int? = null,

	@field:SerializedName("bat1Name")
	val bat1Name: String? = null,

	@field:SerializedName("bat1Runs")
	val bat1Runs: Int? = null,

	@field:SerializedName("bat1balls")
	val bat1balls: Int? = null,

	@field:SerializedName("totalBalls")
	val totalBalls: Int? = null,

	@field:SerializedName("bat2Threes")
	val bat2Threes: Int? = null,

	@field:SerializedName("bat1Fives")
	val bat1Fives: Int? = null,

	@field:SerializedName("bat1Sixers")
	val bat1Sixers: Int? = null,

	@field:SerializedName("bat2Runs")
	val bat2Runs: Int? = null,

	@field:SerializedName("bat2Ones")
	val bat2Ones: Int? = null,

	@field:SerializedName("bat1sixes")
	val bat1sixes: Int? = null,

	@field:SerializedName("bat2Name")
	val bat2Name: String? = null,

	@field:SerializedName("bat1Id")
	val bat1Id: Int? = null,

	@field:SerializedName("bat2Fives")
	val bat2Fives: Int? = null,

	@field:SerializedName("totalRuns")
	val totalRuns: Int? = null,

	@field:SerializedName("bat2Boundaries")
	val bat2Boundaries: Int? = null,

	@field:SerializedName("bat2Id")
	val bat2Id: Int? = null,

	@field:SerializedName("bat1Twos")
	val bat1Twos: Int? = null
)

data class ScorecardTossResults(

	@field:SerializedName("decision")
	val decision: String? = null,

	@field:SerializedName("tossWinnerName")
	val tossWinnerName: String? = null,

	@field:SerializedName("tossWinnerId")
	val tossWinnerId: Int? = null
)

data class BowlTeamDetails(

	@field:SerializedName("bowlTeamId")
	val bowlTeamId: Int? = null,

	@field:SerializedName("bowlTeamName")
	val bowlTeamName: String? = null,

	@field:SerializedName("bowlersData")
	val bowlersData: List<BowlersDataItem?>? = null,

	@field:SerializedName("bowlTeamShortName")
	val bowlTeamShortName: String? = null
)

data class Pat4(

	@field:SerializedName("bat2fours")
	val bat2fours: Int? = null,

	@field:SerializedName("bat2balls")
	val bat2balls: Int? = null,

	@field:SerializedName("bat2Sixers")
	val bat2Sixers: Int? = null,

	@field:SerializedName("bat2Twos")
	val bat2Twos: Int? = null,

	@field:SerializedName("bat1Ones")
	val bat1Ones: Int? = null,

	@field:SerializedName("bat1Boundaries")
	val bat1Boundaries: Int? = null,

	@field:SerializedName("bat1fours")
	val bat1fours: Int? = null,

	@field:SerializedName("bat1Threes")
	val bat1Threes: Int? = null,

	@field:SerializedName("bat2sixes")
	val bat2sixes: Int? = null,

	@field:SerializedName("bat1Name")
	val bat1Name: String? = null,

	@field:SerializedName("bat1Runs")
	val bat1Runs: Int? = null,

	@field:SerializedName("bat1balls")
	val bat1balls: Int? = null,

	@field:SerializedName("totalBalls")
	val totalBalls: Int? = null,

	@field:SerializedName("bat2Threes")
	val bat2Threes: Int? = null,

	@field:SerializedName("bat1Fives")
	val bat1Fives: Int? = null,

	@field:SerializedName("bat1Sixers")
	val bat1Sixers: Int? = null,

	@field:SerializedName("bat2Runs")
	val bat2Runs: Int? = null,

	@field:SerializedName("bat2Ones")
	val bat2Ones: Int? = null,

	@field:SerializedName("bat1sixes")
	val bat1sixes: Int? = null,

	@field:SerializedName("bat2Name")
	val bat2Name: String? = null,

	@field:SerializedName("bat1Id")
	val bat1Id: Int? = null,

	@field:SerializedName("bat2Fives")
	val bat2Fives: Int? = null,

	@field:SerializedName("totalRuns")
	val totalRuns: Int? = null,

	@field:SerializedName("bat2Boundaries")
	val bat2Boundaries: Int? = null,

	@field:SerializedName("bat2Id")
	val bat2Id: Int? = null,

	@field:SerializedName("bat1Twos")
	val bat1Twos: Int? = null
)

data class Pat5(

	@field:SerializedName("bat2fours")
	val bat2fours: Int? = null,

	@field:SerializedName("bat2balls")
	val bat2balls: Int? = null,

	@field:SerializedName("bat2Sixers")
	val bat2Sixers: Int? = null,

	@field:SerializedName("bat2Twos")
	val bat2Twos: Int? = null,

	@field:SerializedName("bat1Ones")
	val bat1Ones: Int? = null,

	@field:SerializedName("bat1Boundaries")
	val bat1Boundaries: Int? = null,

	@field:SerializedName("bat1fours")
	val bat1fours: Int? = null,

	@field:SerializedName("bat1Threes")
	val bat1Threes: Int? = null,

	@field:SerializedName("bat2sixes")
	val bat2sixes: Int? = null,

	@field:SerializedName("bat1Name")
	val bat1Name: String? = null,

	@field:SerializedName("bat1Runs")
	val bat1Runs: Int? = null,

	@field:SerializedName("bat1balls")
	val bat1balls: Int? = null,

	@field:SerializedName("totalBalls")
	val totalBalls: Int? = null,

	@field:SerializedName("bat2Threes")
	val bat2Threes: Int? = null,

	@field:SerializedName("bat1Fives")
	val bat1Fives: Int? = null,

	@field:SerializedName("bat1Sixers")
	val bat1Sixers: Int? = null,

	@field:SerializedName("bat2Runs")
	val bat2Runs: Int? = null,

	@field:SerializedName("bat2Ones")
	val bat2Ones: Int? = null,

	@field:SerializedName("bat1sixes")
	val bat1sixes: Int? = null,

	@field:SerializedName("bat2Name")
	val bat2Name: String? = null,

	@field:SerializedName("bat1Id")
	val bat1Id: Int? = null,

	@field:SerializedName("bat2Fives")
	val bat2Fives: Int? = null,

	@field:SerializedName("totalRuns")
	val totalRuns: Int? = null,

	@field:SerializedName("bat2Boundaries")
	val bat2Boundaries: Int? = null,

	@field:SerializedName("bat2Id")
	val bat2Id: Int? = null,

	@field:SerializedName("bat1Twos")
	val bat1Twos: Int? = null
)

data class Pat9(

	@field:SerializedName("bat2fours")
	val bat2fours: Int? = null,

	@field:SerializedName("bat2balls")
	val bat2balls: Int? = null,

	@field:SerializedName("bat2Sixers")
	val bat2Sixers: Int? = null,

	@field:SerializedName("bat2Twos")
	val bat2Twos: Int? = null,

	@field:SerializedName("bat1Ones")
	val bat1Ones: Int? = null,

	@field:SerializedName("bat1Boundaries")
	val bat1Boundaries: Int? = null,

	@field:SerializedName("bat1fours")
	val bat1fours: Int? = null,

	@field:SerializedName("bat1Threes")
	val bat1Threes: Int? = null,

	@field:SerializedName("bat2sixes")
	val bat2sixes: Int? = null,

	@field:SerializedName("bat1Name")
	val bat1Name: String? = null,

	@field:SerializedName("bat1Runs")
	val bat1Runs: Int? = null,

	@field:SerializedName("bat1balls")
	val bat1balls: Int? = null,

	@field:SerializedName("totalBalls")
	val totalBalls: Int? = null,

	@field:SerializedName("bat2Threes")
	val bat2Threes: Int? = null,

	@field:SerializedName("bat1Fives")
	val bat1Fives: Int? = null,

	@field:SerializedName("bat1Sixers")
	val bat1Sixers: Int? = null,

	@field:SerializedName("bat2Runs")
	val bat2Runs: Int? = null,

	@field:SerializedName("bat2Ones")
	val bat2Ones: Int? = null,

	@field:SerializedName("bat1sixes")
	val bat1sixes: Int? = null,

	@field:SerializedName("bat2Name")
	val bat2Name: String? = null,

	@field:SerializedName("bat1Id")
	val bat1Id: Int? = null,

	@field:SerializedName("bat2Fives")
	val bat2Fives: Int? = null,

	@field:SerializedName("totalRuns")
	val totalRuns: Int? = null,

	@field:SerializedName("bat2Boundaries")
	val bat2Boundaries: Int? = null,

	@field:SerializedName("bat2Id")
	val bat2Id: Int? = null,

	@field:SerializedName("bat1Twos")
	val bat1Twos: Int? = null
)

data class Pat8(

	@field:SerializedName("bat2fours")
	val bat2fours: Int? = null,

	@field:SerializedName("bat2balls")
	val bat2balls: Int? = null,

	@field:SerializedName("bat2Sixers")
	val bat2Sixers: Int? = null,

	@field:SerializedName("bat2Twos")
	val bat2Twos: Int? = null,

	@field:SerializedName("bat1Ones")
	val bat1Ones: Int? = null,

	@field:SerializedName("bat1Boundaries")
	val bat1Boundaries: Int? = null,

	@field:SerializedName("bat1fours")
	val bat1fours: Int? = null,

	@field:SerializedName("bat1Threes")
	val bat1Threes: Int? = null,

	@field:SerializedName("bat2sixes")
	val bat2sixes: Int? = null,

	@field:SerializedName("bat1Name")
	val bat1Name: String? = null,

	@field:SerializedName("bat1Runs")
	val bat1Runs: Int? = null,

	@field:SerializedName("bat1balls")
	val bat1balls: Int? = null,

	@field:SerializedName("totalBalls")
	val totalBalls: Int? = null,

	@field:SerializedName("bat2Threes")
	val bat2Threes: Int? = null,

	@field:SerializedName("bat1Fives")
	val bat1Fives: Int? = null,

	@field:SerializedName("bat1Sixers")
	val bat1Sixers: Int? = null,

	@field:SerializedName("bat2Runs")
	val bat2Runs: Int? = null,

	@field:SerializedName("bat2Ones")
	val bat2Ones: Int? = null,

	@field:SerializedName("bat1sixes")
	val bat1sixes: Int? = null,

	@field:SerializedName("bat2Name")
	val bat2Name: String? = null,

	@field:SerializedName("bat1Id")
	val bat1Id: Int? = null,

	@field:SerializedName("bat2Fives")
	val bat2Fives: Int? = null,

	@field:SerializedName("totalRuns")
	val totalRuns: Int? = null,

	@field:SerializedName("bat2Boundaries")
	val bat2Boundaries: Int? = null,

	@field:SerializedName("bat2Id")
	val bat2Id: Int? = null,

	@field:SerializedName("bat1Twos")
	val bat1Twos: Int? = null
)

data class ScoreCardItem(

	@field:SerializedName("ppData")
	val ppData: PpData? = null,

	@field:SerializedName("bowlTeamDetails")
	val bowlTeamDetails: BowlTeamDetails? = null,

	@field:SerializedName("scoreDetails")
	val scoreDetails: ScoreDetails? = null,

	@field:SerializedName("extrasData")
	val extrasData: ExtrasData? = null,

	@field:SerializedName("timeScore")
	val timeScore: Long? = null,

	@field:SerializedName("wicketsData")
	val wicketsData: List<WicketsDataItem?>? = null,

	/*@field:SerializedName("partnershipsData")
	val partnershipsData: PartnershipsData? = null,*/

	@field:SerializedName("partnershipsData")
	val partnershipsData: List<Pat1?>? = null,

	@field:SerializedName("matchId")
	val matchId: Int? = null,

	@field:SerializedName("inningsId")
	val inningsId: Int? = null,

	@field:SerializedName("batTeamDetails")
	val batTeamDetails: BatTeamDetails? = null,

	var expand :Boolean ?= true
)

data class ExtrasData(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("noBalls")
	val noBalls: Int? = null,

	@field:SerializedName("penalty")
	val penalty: Int? = null,

	@field:SerializedName("legByes")
	val legByes: Int? = null,

	@field:SerializedName("byes")
	val byes: Int? = null,

	@field:SerializedName("wides")
	val wides: Int? = null
)

data class Pat10(

	@field:SerializedName("bat2fours")
	val bat2fours: Int? = null,

	@field:SerializedName("bat2balls")
	val bat2balls: Int? = null,

	@field:SerializedName("bat2Sixers")
	val bat2Sixers: Int? = null,

	@field:SerializedName("bat2Twos")
	val bat2Twos: Int? = null,

	@field:SerializedName("bat1Ones")
	val bat1Ones: Int? = null,

	@field:SerializedName("bat1Boundaries")
	val bat1Boundaries: Int? = null,

	@field:SerializedName("bat1fours")
	val bat1fours: Int? = null,

	@field:SerializedName("bat1Threes")
	val bat1Threes: Int? = null,

	@field:SerializedName("bat2sixes")
	val bat2sixes: Int? = null,

	@field:SerializedName("bat1Name")
	val bat1Name: String? = null,

	@field:SerializedName("bat1Runs")
	val bat1Runs: Int? = null,

	@field:SerializedName("bat1balls")
	val bat1balls: Int? = null,

	@field:SerializedName("totalBalls")
	val totalBalls: Int? = null,

	@field:SerializedName("bat2Threes")
	val bat2Threes: Int? = null,

	@field:SerializedName("bat1Fives")
	val bat1Fives: Int? = null,

	@field:SerializedName("bat1Sixers")
	val bat1Sixers: Int? = null,

	@field:SerializedName("bat2Runs")
	val bat2Runs: Int? = null,

	@field:SerializedName("bat2Ones")
	val bat2Ones: Int? = null,

	@field:SerializedName("bat1sixes")
	val bat1sixes: Int? = null,

	@field:SerializedName("bat2Name")
	val bat2Name: String? = null,

	@field:SerializedName("bat1Id")
	val bat1Id: Int? = null,

	@field:SerializedName("bat2Fives")
	val bat2Fives: Int? = null,

	@field:SerializedName("totalRuns")
	val totalRuns: Int? = null,

	@field:SerializedName("bat2Boundaries")
	val bat2Boundaries: Int? = null,

	@field:SerializedName("bat2Id")
	val bat2Id: Int? = null,

	@field:SerializedName("bat1Twos")
	val bat1Twos: Int? = null
)

data class PpData(

	@field:SerializedName("pp_1")
	val pp1: Pp1? = null
)

data class ScoreDetails(

	@field:SerializedName("revisedOvers")
	val revisedOvers: Int? = null,

	@field:SerializedName("ballNbr")
	val ballNbr: Int? = null,

	@field:SerializedName("isDeclared")
	val isDeclared: Boolean? = null,

	@field:SerializedName("runRate")
	val runRate: Any? = null,

	@field:SerializedName("overs")
	val overs: Any? = null,

	@field:SerializedName("runs")
	val runs: Int? = null,

	@field:SerializedName("wickets")
	val wickets: Int? = null,

	@field:SerializedName("runsPerBall")
	val runsPerBall: Any? = null,

	@field:SerializedName("isFollowOn")
	val isFollowOn: Boolean? = null
)

data class PartnershipsData(

	@field:SerializedName("pat_1")
	val pat1: Pat1? = null,

	@field:SerializedName("pat_2")
	val pat2: Pat2? = null,

	@field:SerializedName("pat_3")
	val pat3: Pat3? = null,

	@field:SerializedName("pat_10")
	val pat10: Pat10? = null,

	@field:SerializedName("pat_4")
	val pat4: Pat4? = null,

	@field:SerializedName("pat_5")
	val pat5: Pat5? = null,

	@field:SerializedName("pat_6")
	val pat6: Pat6? = null,

	@field:SerializedName("pat_7")
	val pat7: Pat7? = null,

	@field:SerializedName("pat_8")
	val pat8: Pat8? = null,

	@field:SerializedName("pat_9")
	val pat9: Pat9? = null
)

data class WicketsDataItem(

	@field:SerializedName("batName")
	val batName: String? = null,

	@field:SerializedName("wktOver")
	val wktOver: Any? = null,

	@field:SerializedName("batId")
	val batId: Int? = null,

	@field:SerializedName("ballNbr")
	val ballNbr: Int? = null,

	@field:SerializedName("wktNbr")
	val wktNbr: Int? = null,

	@field:SerializedName("wktRuns")
	val wktRuns: Int? = null
)

data class BatsmenDataItem(

	@field:SerializedName("batId")
	val batId: Int? = null,

	@field:SerializedName("outDesc")
	val outDesc: String? = null,

	@field:SerializedName("balls")
	val balls: Int? = null,

	@field:SerializedName("fives")
	val fives: Int? = null,

	@field:SerializedName("twos")
	val twos: Int? = null,

	@field:SerializedName("mins")
	val mins: Int? = null,

	@field:SerializedName("sixes")
	val sixes: Int? = null,

	@field:SerializedName("wicketCode")
	val wicketCode: String? = null,

	@field:SerializedName("boundaries")
	val boundaries: Int? = null,

	@field:SerializedName("fours")
	val fours: Int? = null,

	@field:SerializedName("fielderId3")
	val fielderId3: Int? = null,

	@field:SerializedName("dots")
	val dots: Int? = null,

	@field:SerializedName("fielderId2")
	val fielderId2: Int? = null,

	@field:SerializedName("batShortName")
	val batShortName: String? = null,

	@field:SerializedName("isOverseas")
	val isOverseas: Boolean? = null,

	@field:SerializedName("batName")
	val batName: String? = null,

	@field:SerializedName("strikeRate")
	val strikeRate: Any? = null,

	@field:SerializedName("bowlerId")
	val bowlerId: Int? = null,

	@field:SerializedName("threes")
	val threes: Int? = null,

	@field:SerializedName("playingXIChange")
	val playingXIChange: String? = null,

	@field:SerializedName("ones")
	val ones: Int? = null,

	@field:SerializedName("sixers")
	val sixers: Int? = null,

	@field:SerializedName("isCaptain")
	val isCaptain: Boolean? = null,

	@field:SerializedName("fielderId1")
	val fielderId1: Int? = null,

	@field:SerializedName("inMatchChange")
	val inMatchChange: String? = null,

	@field:SerializedName("runs")
	val runs: Int? = null,

	@field:SerializedName("isKeeper")
	val isKeeper: Boolean? = null
)

data class BowlersDataItem(

	@field:SerializedName("bowlShortName")
	val bowlShortName: String? = null,

	@field:SerializedName("bowlName")
	val bowlName: String? = null,

	@field:SerializedName("dots")
	val dots: Int? = null,

	@field:SerializedName("balls")
	val balls: Int? = null,

	@field:SerializedName("economy")
	val economy: Any? = null,

	@field:SerializedName("overs")
	val overs: Any? = null,

	@field:SerializedName("isOverseas")
	val isOverseas: Boolean? = null,

	@field:SerializedName("no_balls")
	val noBalls: Int? = null,

	@field:SerializedName("bowlerId")
	val bowlerId: Int? = null,

	@field:SerializedName("playingXIChange")
	val playingXIChange: String? = null,

	@field:SerializedName("maidens")
	val maidens: Int? = null,

	@field:SerializedName("isCaptain")
	val isCaptain: Boolean? = null,

	@field:SerializedName("inMatchChange")
	val inMatchChange: String? = null,

	@field:SerializedName("runs")
	val runs: Int? = null,

	@field:SerializedName("wickets")
	val wickets: Int? = null,

	@field:SerializedName("runsPerBall")
	val runsPerBall: Any? = null,

	@field:SerializedName("isKeeper")
	val isKeeper: Boolean? = null,

	@field:SerializedName("wides")
	val wides: Int? = null
)

data class Pat7(

	@field:SerializedName("bat2fours")
	val bat2fours: Int? = null,

	@field:SerializedName("bat2balls")
	val bat2balls: Int? = null,

	@field:SerializedName("bat2Sixers")
	val bat2Sixers: Int? = null,

	@field:SerializedName("bat2Twos")
	val bat2Twos: Int? = null,

	@field:SerializedName("bat1Ones")
	val bat1Ones: Int? = null,

	@field:SerializedName("bat1Boundaries")
	val bat1Boundaries: Int? = null,

	@field:SerializedName("bat1fours")
	val bat1fours: Int? = null,

	@field:SerializedName("bat1Threes")
	val bat1Threes: Int? = null,

	@field:SerializedName("bat2sixes")
	val bat2sixes: Int? = null,

	@field:SerializedName("bat1Name")
	val bat1Name: String? = null,

	@field:SerializedName("bat1Runs")
	val bat1Runs: Int? = null,

	@field:SerializedName("bat1balls")
	val bat1balls: Int? = null,

	@field:SerializedName("totalBalls")
	val totalBalls: Int? = null,

	@field:SerializedName("bat2Threes")
	val bat2Threes: Int? = null,

	@field:SerializedName("bat1Fives")
	val bat1Fives: Int? = null,

	@field:SerializedName("bat1Sixers")
	val bat1Sixers: Int? = null,

	@field:SerializedName("bat2Runs")
	val bat2Runs: Int? = null,

	@field:SerializedName("bat2Ones")
	val bat2Ones: Int? = null,

	@field:SerializedName("bat1sixes")
	val bat1sixes: Int? = null,

	@field:SerializedName("bat2Name")
	val bat2Name: String? = null,

	@field:SerializedName("bat1Id")
	val bat1Id: Int? = null,

	@field:SerializedName("bat2Fives")
	val bat2Fives: Int? = null,

	@field:SerializedName("totalRuns")
	val totalRuns: Int? = null,

	@field:SerializedName("bat2Boundaries")
	val bat2Boundaries: Int? = null,

	@field:SerializedName("bat2Id")
	val bat2Id: Int? = null,

	@field:SerializedName("bat1Twos")
	val bat1Twos: Int? = null
)

data class BatTeamDetails(

	@field:SerializedName("batTeamId")
	val batTeamId: Int? = null,

	@field:SerializedName("batTeamName")
	val batTeamName: String? = null,

	@field:SerializedName("batsmenData")
	val batsmenData: List<BatsmenDataItem?>? = null,

	@field:SerializedName("batTeamShortName")
	val batTeamShortName: String? = null
)

data class MatchTeamInfoItem(

	@field:SerializedName("battingTeamId")
	val battingTeamId: Int? = null,

	@field:SerializedName("battingTeamShortName")
	val battingTeamShortName: String? = null,

	@field:SerializedName("bowlingTeamId")
	val bowlingTeamId: Int? = null,

	@field:SerializedName("bowlingTeamShortName")
	val bowlingTeamShortName: String? = null
)

data class ScorecardTeam1(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("playerDetails")
	val playerDetails: List<Any?>? = null,

	@field:SerializedName("shortName")
	val shortName: String? = null
)

data class Pat3(

	@field:SerializedName("bat2fours")
	val bat2fours: Int? = null,

	@field:SerializedName("bat2balls")
	val bat2balls: Int? = null,

	@field:SerializedName("bat2Sixers")
	val bat2Sixers: Int? = null,

	@field:SerializedName("bat2Twos")
	val bat2Twos: Int? = null,

	@field:SerializedName("bat1Ones")
	val bat1Ones: Int? = null,

	@field:SerializedName("bat1Boundaries")
	val bat1Boundaries: Int? = null,

	@field:SerializedName("bat1fours")
	val bat1fours: Int? = null,

	@field:SerializedName("bat1Threes")
	val bat1Threes: Int? = null,

	@field:SerializedName("bat2sixes")
	val bat2sixes: Int? = null,

	@field:SerializedName("bat1Name")
	val bat1Name: String? = null,

	@field:SerializedName("bat1Runs")
	val bat1Runs: Int? = null,

	@field:SerializedName("bat1balls")
	val bat1balls: Int? = null,

	@field:SerializedName("totalBalls")
	val totalBalls: Int? = null,

	@field:SerializedName("bat2Threes")
	val bat2Threes: Int? = null,

	@field:SerializedName("bat1Fives")
	val bat1Fives: Int? = null,

	@field:SerializedName("bat1Sixers")
	val bat1Sixers: Int? = null,

	@field:SerializedName("bat2Runs")
	val bat2Runs: Int? = null,

	@field:SerializedName("bat2Ones")
	val bat2Ones: Int? = null,

	@field:SerializedName("bat1sixes")
	val bat1sixes: Int? = null,

	@field:SerializedName("bat2Name")
	val bat2Name: String? = null,

	@field:SerializedName("bat1Id")
	val bat1Id: Int? = null,

	@field:SerializedName("bat2Fives")
	val bat2Fives: Int? = null,

	@field:SerializedName("totalRuns")
	val totalRuns: Int? = null,

	@field:SerializedName("bat2Boundaries")
	val bat2Boundaries: Int? = null,

	@field:SerializedName("bat2Id")
	val bat2Id: Int? = null,

	@field:SerializedName("bat1Twos")
	val bat1Twos: Int? = null
)

data class Pp1(

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

data class PlayersOfTheMatchItem(

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

data class ScorecardRevisedTarget(

	@field:SerializedName("reason")
	val reason: String? = null
)

data class Pat2(

	@field:SerializedName("bat2fours")
	val bat2fours: Int? = null,

	@field:SerializedName("bat2balls")
	val bat2balls: Int? = null,

	@field:SerializedName("bat2Sixers")
	val bat2Sixers: Int? = null,

	@field:SerializedName("bat2Twos")
	val bat2Twos: Int? = null,

	@field:SerializedName("bat1Ones")
	val bat1Ones: Int? = null,

	@field:SerializedName("bat1Boundaries")
	val bat1Boundaries: Int? = null,

	@field:SerializedName("bat1fours")
	val bat1fours: Int? = null,

	@field:SerializedName("bat1Threes")
	val bat1Threes: Int? = null,

	@field:SerializedName("bat2sixes")
	val bat2sixes: Int? = null,

	@field:SerializedName("bat1Name")
	val bat1Name: String? = null,

	@field:SerializedName("bat1Runs")
	val bat1Runs: Int? = null,

	@field:SerializedName("bat1balls")
	val bat1balls: Int? = null,

	@field:SerializedName("totalBalls")
	val totalBalls: Int? = null,

	@field:SerializedName("bat2Threes")
	val bat2Threes: Int? = null,

	@field:SerializedName("bat1Fives")
	val bat1Fives: Int? = null,

	@field:SerializedName("bat1Sixers")
	val bat1Sixers: Int? = null,

	@field:SerializedName("bat2Runs")
	val bat2Runs: Int? = null,

	@field:SerializedName("bat2Ones")
	val bat2Ones: Int? = null,

	@field:SerializedName("bat1sixes")
	val bat1sixes: Int? = null,

	@field:SerializedName("bat2Name")
	val bat2Name: String? = null,

	@field:SerializedName("bat1Id")
	val bat1Id: Int? = null,

	@field:SerializedName("bat2Fives")
	val bat2Fives: Int? = null,

	@field:SerializedName("totalRuns")
	val totalRuns: Int? = null,

	@field:SerializedName("bat2Boundaries")
	val bat2Boundaries: Int? = null,

	@field:SerializedName("bat2Id")
	val bat2Id: Int? = null,

	@field:SerializedName("bat1Twos")
	val bat1Twos: Int? = null
)
