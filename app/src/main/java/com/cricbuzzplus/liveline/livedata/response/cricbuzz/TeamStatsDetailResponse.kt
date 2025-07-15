package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class TeamStatsDetailResponse(

	@field:SerializedName("filter")
	val filter: TeamStatsFilter? = null,

	@field:SerializedName("headers")
	val headers: List<String?>? = null,

	@field:SerializedName("appIndex")
	val appIndex: TeamStatsAppIndex? = null,

	@field:SerializedName("values")
	val values: List<ValuesItemStats?>? = null
)

data class TeamStatsMatchtypeItem(

	@field:SerializedName("matchTypeDesc")
	val matchTypeDesc: String? = null,

	@field:SerializedName("matchTypeId")
	val matchTypeId: String? = null
)

data class TeamStatsAppIndex(

	@field:SerializedName("webURL")
	val webURL: String? = null,

	@field:SerializedName("seoTitle")
	val seoTitle: String? = null
)

data class TeamStatsValuesItem(

	@field:SerializedName("values")
	val values: List<String?>? = null
)

data class TeamStatsFilter(

	@field:SerializedName("selectedTeam")
	val selectedTeam: String? = null,

	@field:SerializedName("selectedYear")
	val selectedYear: String? = null,

	@field:SerializedName("matchtype")
	val matchtype: List<MatchtypeItem?>? = null,

	@field:SerializedName("selectedMatchType")
	val selectedMatchType: String? = null,

	@field:SerializedName("team")
	val team: List<TeamItem?>? = null
)

data class TeamItem(

	@field:SerializedName("teamShortName")
	val teamShortName: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
