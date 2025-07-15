package com.cricbuzzplus.liveline.livedata.response.newresponse

data class TeamLastTenMatchCompareResponse(
	val teamA: TeamA? = null,
	val teamB: TeamB? = null
)

data class TeamB(
	val highScore: Int? = null,
	val winning: String? = null,
	val lowScore: Int? = null,
	val matchPlayed: Int? = null,
	val averageScore: Int? = null
)

data class TeamA(
	val highScore: Int? = null,
	val winning: String? = null,
	val lowScore: Int? = null,
	val matchPlayed: Int? = null,
	val averageScore: Int? = null
)

