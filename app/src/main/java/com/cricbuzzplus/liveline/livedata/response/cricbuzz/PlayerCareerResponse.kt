package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class PlayerCareerResponse(

	@field:SerializedName("values")
	val values: List<PlayerCareerValuesItem?>? = null
)

data class PlayerCareerValuesItem(

	@field:SerializedName("debut")
	val debut: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("lastPlayed")
	val lastPlayed: String? = null
)
