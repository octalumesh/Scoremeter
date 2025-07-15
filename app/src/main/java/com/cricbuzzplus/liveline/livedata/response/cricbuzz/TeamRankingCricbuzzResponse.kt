package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class TeamRankingCricbuzzResponse(

	@field:SerializedName("rank")
	val rank: List<RankItem?>? = null
)

data class RankItem(

	@field:SerializedName("imageId")
	val imageId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("rating")
	val rating: String? = null,

	@field:SerializedName("rank")
	val rank: String? = null,

	@field:SerializedName("lastUpdatedOn")
	val lastUpdatedOn: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("matches")
	val matches: String? = null,

	@field:SerializedName("points")
	val points: String? = null
)
