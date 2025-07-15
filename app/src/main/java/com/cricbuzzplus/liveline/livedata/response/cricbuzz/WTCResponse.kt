package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class WTCResponse(

	@field:SerializedName("headers")
	val headers: List<String?>? = null,

	@field:SerializedName("values")
	val values: List<WTCValuesItem?>? = null,

	@field:SerializedName("seasonStandings")
	val seasonStandings: List<SeasonStandingsItem?>? = null,

	@field:SerializedName("subText")
	val subText: String? = null
)

data class SeasonStandingsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class WTCValuesItem(

	@field:SerializedName("value")
	val value: List<String?>? = null
)
