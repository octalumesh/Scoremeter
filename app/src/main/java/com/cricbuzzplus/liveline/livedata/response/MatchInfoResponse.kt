package com.cricbuzzplus.liveline.livedata.response

import com.google.gson.annotations.SerializedName

data class MatchInfoResponse(

	@field:SerializedName("venue")
	val venue: String? = null,

	@field:SerializedName("matchs")
	val matchs: String? = null,

	@field:SerializedName("team_a_img")
	val teamAImg: String? = null,

	@field:SerializedName("team_b_id")
	val teamBId: Int? = null,

	@field:SerializedName("man_of_match")
	val manOfMatch: String? = null,

	@field:SerializedName("team_b_img")
	val teamBImg: String? = null,

	@field:SerializedName("referee")
	val referee: String? = null,

	@field:SerializedName("match_date")
	val matchDate: String? = null,

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("team_a")
	val teamA: String? = null,

	@field:SerializedName("team_comparison")
	val teamComparison: TeamComparison? = null,

	@field:SerializedName("match_type")
	val matchType: String? = null,

	@field:SerializedName("place")
	val place: String? = null,

	@field:SerializedName("third_umpire")
	val thirdUmpire: String? = null,

	@field:SerializedName("man_of_match_player")
	val manOfMatchPlayer: String? = null,

	@field:SerializedName("venue_id")
	val venueId: Int? = null,

	@field:SerializedName("umpire")
	val umpire: String? = null,

	@field:SerializedName("team_a_short")
	val teamAShort: String? = null,

	@field:SerializedName("toss_comparison")
	val tossComparison: TossComparison? = null,

	@field:SerializedName("team_a_id")
	val teamAId: Int? = null,

	@field:SerializedName("venue_weather")
	val venueWeather: VenueWeather? = null,

	@field:SerializedName("head_to_head")
	val headToHead: HeadToHead? = null,

	@field:SerializedName("match_time")
	val matchTime: String? = null,

	@field:SerializedName("toss")
	val toss: String? = null,

	@field:SerializedName("match_status")
	val matchStatus: Int? = null,

	@field:SerializedName("series_id")
	val seriesId: Int? = null,

	@field:SerializedName("team_b")
	val teamB: String? = null,

	@field:SerializedName("series")
	val series: String? = null,

	@field:SerializedName("team_b_short")
	val teamBShort: String? = null,

	@field:SerializedName("pace_spin")
	val paceSpin: PaceSpin? = null,

	@field:SerializedName("forms")
	val forms: Forms? = null
)

data class Forms(

	@field:SerializedName("team_a")
	val teamA: List<String?>? = null,

	@field:SerializedName("team_b")
	val teamB: List<String?>? = null
)

data class TeamComparison(

	@field:SerializedName("team_b_win")
	val teamBWin: Int? = null,

	@field:SerializedName("team_b_low_score")
	val teamBLowScore: Int? = null,

	@field:SerializedName("team_b_avg_score")
	val teamBAvgScore: Int? = null,

	@field:SerializedName("team_a_high_score")
	val teamAHighScore: String? = null,

	@field:SerializedName("team_b_high_score")
	val teamBHighScore: String? = null,

	@field:SerializedName("team_a_win")
	val teamAWin: Int? = null,

	@field:SerializedName("team_a_avg_score")
	val teamAAvgScore: Int? = null,

	@field:SerializedName("team_a_low_score")
	val teamALowScore: String? = null
)

data class TossComparison(

	@field:SerializedName("team_a")
	val teamA: List<String?>? = null,

	@field:SerializedName("team_b")
	val teamB: List<String?>? = null
)

data class VenueWeather(

	@field:SerializedName("cloud")
	val cloud: Int? = null,

	@field:SerializedName("wind_kph")
	val windKph: Any? = null,

	@field:SerializedName("wind_mph")
	val windMph: Any? = null,

	@field:SerializedName("weather")
	val weather: String? = null,

	@field:SerializedName("humidity")
	val humidity: Any? = null,

	@field:SerializedName("wind_dir")
	val windDir: String? = null,

	@field:SerializedName("temp_c")
	val tempC: Any? = null,

	@field:SerializedName("weather_icon")
	val weatherIcon: String? = null,

	@field:SerializedName("temp_f")
	val tempF: Any? = null
)

data class HeadToHead(

	@field:SerializedName("team_a_win_count")
	val teamAWinCount: Int? = null,

	@field:SerializedName("matches")
	val matches: List<MatchesItem?>? = null,

	@field:SerializedName("team_b_win_count")
	val teamBWinCount: Int? = null
)

data class TeamBScoreArrItem(

	@field:SerializedName("over")
	val over: String? = null,

	@field:SerializedName("score")
	val score: Int? = null,

	@field:SerializedName("wicket")
	val wicket: Int? = null
)

data class TeamAScoreArrItem(

	@field:SerializedName("over")
	val over: String? = null,

	@field:SerializedName("score")
	val score: Int? = null,

	@field:SerializedName("wicket")
	val wicket: Int? = null
)

data class MatchesItem(

	@field:SerializedName("win_team")
	val winTeam: String? = null,

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("team_b_score")
	val teamBScore: String? = null,

	@field:SerializedName("team_b_over")
	val teamBOver: String? = null,

	@field:SerializedName("matchs")
	val matchs: String? = null,

	@field:SerializedName("team_b_score_arr")
	val teamBScoreArr: List<TeamBScoreArrItem?>? = null,

	@field:SerializedName("match_id")
	val matchId: Int? = null,

	@field:SerializedName("team_a_score_arr")
	val teamAScoreArr: List<TeamAScoreArrItem?>? = null,

	@field:SerializedName("team_a_over")
	val teamAOver: String? = null,

	@field:SerializedName("team_a_score")
	val teamAScore: String? = null
)

data class PaceSpin(

	@field:SerializedName("spin_percent")
	val spinPercent: Int? = null,

	@field:SerializedName("pace_percent")
	val pacePercent: Int? = null,

	@field:SerializedName("pace_wkt")
	val paceWkt: Int? = null,

	@field:SerializedName("spin_wkt")
	val spinWkt: Int? = null,

	@field:SerializedName("win_bowl_first")
	val winBowlFirst: Int? = null,

	@field:SerializedName("win_bat_first")
	val winBatFirst: Int? = null,

	@field:SerializedName("total_matches")
	val totalMatches: Int? = null
)
