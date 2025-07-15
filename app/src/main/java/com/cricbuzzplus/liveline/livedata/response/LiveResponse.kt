package com.cricbuzzplus.liveline.livedata.response

import com.google.gson.annotations.SerializedName

data class LiveResponse(

	@field:SerializedName("rr_rate")
	val rrRate: String? = null,

	@field:SerializedName("min_rate_1")
	val minRate1: String? = null,

	@field:SerializedName("powerplay")
	val powerplay: String? = null,

	@field:SerializedName("ball_rem")
	val ballRem: Int? = null,

	@field:SerializedName("s_min")
	val sMin: String? = null,

	@field:SerializedName("max_rate_1")
	val maxRate1: String? = null,

	@field:SerializedName("max_rate")
	val maxRate: String? = null,

	@field:SerializedName("max_rate_2")
	val maxRate2: String? = null,

	@field:SerializedName("team_a_scores")
	val teamAScores: String? = null,

	@field:SerializedName("second_circle")
	val secondCircle: String? = null,

	@field:SerializedName("team_b_scores")
	val teamBScores: String? = null,

	@field:SerializedName("team_b_score")
	val teamBScore: LiveTeamBScore? = null,

	@field:SerializedName("curr_rate")
	val currRate: String? = null,

	@field:SerializedName("lastwicket")
	val lastwicket: Lastwicket? = null,

	@field:SerializedName("s_max")
	val sMax: String? = null,

	@field:SerializedName("balling_team")
	val ballingTeam: Int? = null,

	@field:SerializedName("s_ovr")
	val sOvr: String? = null,

	@field:SerializedName("next_batsman")
	val nextBatsman: Any? = null,

	@field:SerializedName("yet_to_bet")
	val yetToBet: List<String>? = null,

	@field:SerializedName("team_a_short")
	val teamAShort: String? = null,

	@field:SerializedName("partnership")
	val partnership: Partnership? = null,

	@field:SerializedName("match_over")
	val matchOver: String? = null,

	@field:SerializedName("team_a_id")
	val teamAId: Int? = null,

	@field:SerializedName("toss")
	val toss: String? = null,

	@field:SerializedName("team_b")
	val teamB: String? = null,

	@field:SerializedName("team_b_over")
	val teamBOver: String? = null,

	@field:SerializedName("min_rate_2")
	val minRate2: String? = null,

	@field:SerializedName("current_inning")
	val currentInning: String? = null,

	@field:SerializedName("s_run")
	val sRun: Int? = null,

	@field:SerializedName("fav_team")
	val favTeam: String? = null,

	@field:SerializedName("team_a_img")
	val teamAImg: String? = null,

	@field:SerializedName("team_b_id")
	val teamBId: Int? = null,

	@field:SerializedName("session")
	val session: String? = null,

	@field:SerializedName("team_b_img")
	val teamBImg: String? = null,

	@field:SerializedName("trail_lead")
	val trailLead: String? = null,

	@field:SerializedName("lambi_ovr")
	val lambiOvr: String? = null,

	@field:SerializedName("lambi_min")
	val lambiMin: String? = null,

	@field:SerializedName("lambi_max")
	val lambiMax: String? = null,

	@field:SerializedName("team_a_score")
	val teamAScore: LiveTeamAScore? = null,

	@field:SerializedName("bolwer")
	val bolwer: Bolwer? = null,

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("team_a")
	val teamA: String? = null,

	@field:SerializedName("first_circle")
	val firstCircle: String? = null,

	@field:SerializedName("match_type")
	val matchType: String? = null,

	@field:SerializedName("team_a_over")
	val teamAOver: String? = null,

	@field:SerializedName("batsman")
	val batsman: List<BatsmanItem?>? = null,

	@field:SerializedName("min_rate")
	val minRate: String? = null,

	@field:SerializedName("match_id")
	val matchId: Int? = null,

	@field:SerializedName("run_need")
	val runNeed: Int? = null,

	@field:SerializedName("last36ball")
	val last36ball: List<String?>? = null,

	@field:SerializedName("last4overs")
	val last4overs: List<Last4OverItem?>? = null,

	@field:SerializedName("s_ball")
	val sBall: Int? = null,

	@field:SerializedName("series_id")
	val seriesId: Int? = null,

	@field:SerializedName("target")
	val target: Int? = null,

	@field:SerializedName("batting_team")
	val battingTeam: Int? = null,

	@field:SerializedName("team_b_short")
	val teamBShort: String? = null,

	@field:SerializedName("projected_score")
	val projectedScore: List<ProjectedScoreItem?>? = null,

	@field:SerializedName("s_history_1")
	val sHistory1: List<SHistoryItem?>? = null,

	@field:SerializedName("s_history_2")
	val sHistory2: List<SHistoryItem?>? = null,

	@field:SerializedName("udrs")
	val udrs: UDRS? = null,
)


data class UDRS(

	@field:SerializedName("team_a")
	val teamAUdrs: teamAUDRS? = null,

	@field:SerializedName("team_b")
	val teamBUdrs: teamBUDRS? = null,

)

data class teamAUDRS(

	@field:SerializedName("left")
	val left: Int? = null,

	@field:SerializedName("pass")
	val pass: Int? = null,

	@field:SerializedName("fail")
	val fail: Int? = null
)

data class teamBUDRS(

	@field:SerializedName("left")
	val left: Int? = null,

	@field:SerializedName("pass")
	val pass: Int? = null,

	@field:SerializedName("fail")
	val fail: Int? = null
)


data class SHistoryItem(

	@field:SerializedName("over")
	val over: Any? = null,

	@field:SerializedName("fav_team")
	val favTeam: Any? = null,

	@field:SerializedName("score")
	val score: Any? = null,

	@field:SerializedName("s_o_min")
	val sOMin: Any? = null,

	@field:SerializedName("s_min")
	val sMin: Any? = null,

	@field:SerializedName("s_max")
	val sMax: Any? = null,

	@field:SerializedName("max_rate")
	val maxRate: Any? = null,

	@field:SerializedName("s_o_max")
	val sOMax: Any? = null,

	@field:SerializedName("team_id")
	val teamId: Int? = null,

	@field:SerializedName("min_rate")
	val minRate: Any? = null,

	@field:SerializedName("wicket")
	val wicket: Any? = null
)

data class ProjectedScoreItem(

	@field:SerializedName("over")
	val over: Any? = null,

	@field:SerializedName("cur_rate_2_score")
	val curRate2Score: Int? = null,

	@field:SerializedName("cur_rate_score")
	val curRateScore: Int? = null,

	@field:SerializedName("cur_rate_1")
	val curRate1: String? = null,

	@field:SerializedName("cur_rate")
	val curRate: String? = null,

	@field:SerializedName("cur_rate_1_score")
	val curRate1Score: Int? = null,

	@field:SerializedName("cur_rate_3")
	val curRate3: String? = null,

	@field:SerializedName("cur_rate_2")
	val curRate2: String? = null,

	@field:SerializedName("cur_rate_3_score")
	val curRate3Score: Int? = null
)

/*data class SHistory2Item(

	@field:SerializedName("over")
	val over: String? = null,

	@field:SerializedName("fav_team")
	val favTeam: String? = null,

	@field:SerializedName("score")
	val score: Int? = null,

	@field:SerializedName("s_o_min")
	val sOMin: String? = null,

	@field:SerializedName("s_min")
	val sMin: String? = null,

	@field:SerializedName("s_max")
	val sMax: String? = null,

	@field:SerializedName("max_rate")
	val maxRate: Any? = null,

	@field:SerializedName("s_o_max")
	val sOMax: String? = null,

	@field:SerializedName("team_id")
	val teamId: Int? = null,

	@field:SerializedName("min_rate")
	val minRate: Any? = null,

	@field:SerializedName("wicket")
	val wicket: Int? = null
)*/

data class Last4OverItem(

	@field:SerializedName("over")
	val over: Int? = null,

	@field:SerializedName("balls")
	val balls: List<String?>? = null,

	@field:SerializedName("runs")
	val runs: Int? = null
)

data class LiveTeamBScore(

	@field:SerializedName("2")
	val jsonMember2: Inings2? = null,

	@field:SerializedName("4")
	val jsonMember4: Inings4? = null,

	@field:SerializedName("1")
	val jsonMember1: Inings1? = null,

	@field:SerializedName("3")
	val jsonMember3: Inings3? = null,

	@field:SerializedName("team_id")
	val teamId: Int? = null
)

data class Inings2(

	@field:SerializedName("score")
	val score: String? = null,

	@field:SerializedName("ball")
	val ball: String? = null,

	@field:SerializedName("wicket")
	val wicket: String? = null
)

data class Inings3(

	@field:SerializedName("score")
	val score: String? = null,

	@field:SerializedName("ball")
	val ball: String? = null,

	@field:SerializedName("wicket")
	val wicket: String? = null
)

data class Inings1(

	@field:SerializedName("score")
	val score: String? = null,

	@field:SerializedName("ball")
	val ball: String? = null,

	@field:SerializedName("wicket")
	val wicket: String? = null
)

data class Bolwer(

	@field:SerializedName("maiden")
	val maiden: Any? = null,

	@field:SerializedName("over")
	val over: Any? = null,

	@field:SerializedName("name")
	val name: Any? = null,

	@field:SerializedName("run")
	val run: Any? = null,

	@field:SerializedName("economy")
	val economy: Any? = null,

	@field:SerializedName("wicket")
	val wicket: Any? = null
)

data class Inings4(

	@field:SerializedName("score")
	val score: String? = null,

	@field:SerializedName("ball")
	val ball: String? = null,

	@field:SerializedName("wicket")
	val wicket: String? = null
)

data class Partnership(

	@field:SerializedName("ball")
	val ball: Any? = null,

	@field:SerializedName("run")
	val run: Any? = null
)

data class Lastwicket(

	@field:SerializedName("ball")
	val ball: Any? = null,

	@field:SerializedName("run")
	val run: Any? = null,

	@field:SerializedName("player")
	val player: Any? = null
)

data class LiveTeamAScore(

	@field:SerializedName("1")
	val jsonMember1: Inings1? = null,

	@field:SerializedName("3")
	val jsonMember3: Inings3? = null,

	@field:SerializedName("2")
	val jsonMember2: Inings2? = null,

	@field:SerializedName("4")
	val jsonMember4: Inings4? = null,

	@field:SerializedName("team_id")
	val teamId: Int? = null
)

data class BatsmanItem(

	@field:SerializedName("ball")
	val ball: Any? = null,

	@field:SerializedName("strike_rate")
	val strikeRate: Any? = null,

	@field:SerializedName("sixes")
	val sixes: Any? = null,

	@field:SerializedName("name")
	val name: Any? = null,

	@field:SerializedName("out_by")
	val outBy: Any? = null,

	@field:SerializedName("run")
	val run: Any? = null,

	@field:SerializedName("fours")
	val fours: Any? = null
)
