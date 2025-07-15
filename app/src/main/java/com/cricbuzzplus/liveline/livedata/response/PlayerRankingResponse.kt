package com.cricbuzzplus.liveline.livedata.response

import com.google.gson.annotations.SerializedName

data class PlayerRankingResponse(

	@field:SerializedName("PlayerRankingResponse")
	val playerRankingResponse: List<PlayerRankingResponseItem?>? = null
)

data class PlayerRankingResponseItem(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("rating")
	val rating: Int? = null,

	@field:SerializedName("rank")
	val rank: Int? = null
)
