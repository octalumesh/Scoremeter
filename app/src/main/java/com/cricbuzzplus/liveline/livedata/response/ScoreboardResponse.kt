package com.cricbuzzplus.liveline.livedata.response

import com.google.gson.annotations.SerializedName

data class ScoreboardResponse(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("scorecard")
	val scorecard: Scorecard? = null
)

data class FallwicketItem(

	@field:SerializedName("over")
	val over: String? = null,

	@field:SerializedName("score")
	val score: Int? = null,

	@field:SerializedName("player")
	val player: String? = null,

	@field:SerializedName("wicket")
	val wicket: String? = null
)

data class BolwerItem(

	@field:SerializedName("player_id")
	val playerId: Int? = null,

	@field:SerializedName("over")
	val over: String? = null,

	@field:SerializedName("maiden")
	val maiden: Int? = null,

	@field:SerializedName("dot_ball")
	val dotBall: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("run")
	val run: Int? = null,

	@field:SerializedName("economy")
	val economy: String? = null,

	@field:SerializedName("wicket")
	val wicket: Int? = null
)

data class ThirdInning(

	@field:SerializedName("team")
	val team: Team? = null,

	@field:SerializedName("fallwicket")
	val fallwicket: List<FallwicketItem?>? = null,

	@field:SerializedName("batsman")
	val batsman: List<BatsmanItemBoard?>? = null,

	@field:SerializedName("bolwer")
	val bolwer: List<BolwerItem?>? = null
)

data class Team(

	@field:SerializedName("over")
	val over: String? = null,

	@field:SerializedName("score")
	val score: Int? = null,

	@field:SerializedName("flag")
	val flag: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("extras")
	val extras: String? = null,

	@field:SerializedName("wicket")
	val wicket: Int? = null
)

data class FourthInning(

	@field:SerializedName("team")
	val team: Team? = null,

	@field:SerializedName("fallwicket")
	val fallwicket: List<FallwicketItem?>? = null,

	@field:SerializedName("batsman")
	val batsman: List<BatsmanItemBoard?>? = null,

	@field:SerializedName("bolwer")
	val bolwer: List<BolwerItem?>? = null
)

data class Scorecard(

	@field:SerializedName("1")
	val firstInnings: FirstInning? = null,

	@field:SerializedName("2")
	val secondInnings: SecondInning? = null,

	@field:SerializedName("3")
	val thirdInnings: ThirdInning? = null,

	@field:SerializedName("4")
	val fourthInnings: FourthInning? = null
)

data class SecondInning(

	@field:SerializedName("team")
	val team: Team? = null,

	@field:SerializedName("fallwicket")
	val fallwicket: List<FallwicketItem?>? = null,

	@field:SerializedName("batsman")
	val batsman: List<BatsmanItemBoard?>? = null,

	@field:SerializedName("bolwer")
	val bolwer: List<BolwerItem?>? = null
)

data class BatsmanItemBoard(

	@field:SerializedName("player_id")
	val playerId: Int? = null,

	@field:SerializedName("ball")
	val ball: Int? = null,

	@field:SerializedName("strike_rate")
	val strikeRate: String? = null,

	@field:SerializedName("sixes")
	val sixes: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("out_by")
	val outBy: String? = null,

	@field:SerializedName("run")
	val run: Int? = null,

	@field:SerializedName("fours")
	val fours: Int? = null
)

data class FirstInning(

	@field:SerializedName("team")
	val team: Team? = null,

	@field:SerializedName("fallwicket")
	val fallwicket: List<FallwicketItem?>? = null,

	@field:SerializedName("batsman")
	val batsman: List<BatsmanItemBoard?>? = null,

	@field:SerializedName("bolwer")
	val bolwer: List<BolwerItem?>? = null
)
