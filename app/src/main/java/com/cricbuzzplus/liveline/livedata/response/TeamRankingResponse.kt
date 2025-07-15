package com.cricbuzzplus.liveline.livedata.response

import com.google.gson.annotations.SerializedName

data class TeamRankingResponse(

	@field:SerializedName("TeamRankingResponse")
	val teamRankingResponse: List<TeamRankingResponseItem?>? = null
)

data class TeamRankingResponseItem(

	@field:SerializedName("rating")
	val rating: Int? = null,

	@field:SerializedName("rank")
	val rank: Int? = null,

	@field:SerializedName("team")
	val team: String? = null,

	@field:SerializedName("point")
	val point: Int? = null
)
