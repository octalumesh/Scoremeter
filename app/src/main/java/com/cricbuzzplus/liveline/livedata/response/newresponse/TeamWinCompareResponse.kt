package com.cricbuzzplus.liveline.livedata.response.newresponse

import com.google.gson.annotations.SerializedName

data class TeamWinCompareResponse(

	@field:SerializedName("teamA_matchlist")
	val teamAMatchlist: List<String?>? = null,

	@field:SerializedName("teamB_matchlist")
	val teamBMatchlist: List<String?>? = null
)
