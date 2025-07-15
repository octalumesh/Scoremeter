package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class PointTableCricResponse(

	@field:SerializedName("consider_group")
	val considerGroup: Boolean? = null,

	@field:SerializedName("series_name")
	val seriesName: String? = null,

	@field:SerializedName("header")
	val header: List<String?>? = null,

	@field:SerializedName("title")
	val title: List<String?>? = null,

	@field:SerializedName("series_id")
	val seriesId: String? = null,

	@field:SerializedName("min_qual")
	val minQual: MinQual? = null,

	@field:SerializedName("order")
	val order: List<String?>? = null,

	@field:SerializedName("group")
	val group: Group? = null
)

data class MinQual(

	@field:SerializedName("Teams")
	val teams: String? = null
)

data class TeamsItem(

	@field:SerializedName("p")
	val p: String? = null,

	@field:SerializedName("nr")
	val nr: String? = null,

	@field:SerializedName("t")
	val t: String? = null,

	@field:SerializedName("w")
	val w: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("l")
	val l: String? = null,

	@field:SerializedName("points")
	val points: String? = null,

	@field:SerializedName("nrr")
	val nrr: String? = null
)

data class Group(

	@field:SerializedName("Teams")
	val teams: List<TeamsItem?>? = null,

	@field:SerializedName("Group A")
	val groupA: List<TeamsItem?>? = null,

	@field:SerializedName("Group B")
	val groupB: List<TeamsItem?>? = null,

	@field:SerializedName("Group C")
	val groupC: List<TeamsItem?>? = null,

	@field:SerializedName("Group D")
	val groupD: List<TeamsItem?>? = null,

	@field:SerializedName("Group E")
	val groupE: List<TeamsItem?>? = null,

) {
	fun get(group: String): List<TeamsItem>? {
		return (when (group) {
			"Teams" -> this.teams
			"Group A" -> this.groupA
			"Group B" -> this.groupB
			"Group C" -> this.groupC
			"Group D" -> this.groupD
			"Group E" -> this.groupE
			else -> null
		} as List<TeamsItem>?)!!
	}
}
