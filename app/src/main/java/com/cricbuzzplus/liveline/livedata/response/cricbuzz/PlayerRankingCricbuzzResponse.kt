package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class PlayerRankingCricbuzzResponse(

	@field:SerializedName("rank")
	val rank: List<PlayerRankingRankItem?>? = null
)

data class PlayerRankingRankItem(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("avg")
	val avg: String? = null,

	@field:SerializedName("trend")
	val trend: String? = null,

	@field:SerializedName("faceImageId")
	val faceImageId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("rating")
	val rating: String? = null,

	@field:SerializedName("rank")
	val rank: String? = null,

	@field:SerializedName("difference")
	val difference: Int? = null,

	@field:SerializedName("lastUpdatedOn")
	val lastUpdatedOn: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("countryId")
	val countryId: String? = null,

	@field:SerializedName("points")
	val points: String? = null
)
