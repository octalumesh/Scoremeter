package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class SuperLeagueResponse(

	@field:SerializedName("headers")
	val headers: List<String?>? = null,

	@field:SerializedName("values")
	val values: List<SuperLeagueValuesItem?>? = null,

	@field:SerializedName("seasonStandings")
	val seasonStandings: List<SuperLeagueSeasonStandingsItem?>? = null
)

data class SuperLeagueSeasonStandingsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class SuperLeagueValuesItem(

	@field:SerializedName("value")
	val value: List<String?>? = null
)
