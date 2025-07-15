package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class SeriesStatsDetailResponse(

	@field:SerializedName("odiStatsList")
	val odiStatsList: OdiStatsList? = null,

	@field:SerializedName("testStatsList")
	val testStatsList: TestStatsList? = null,

	@field:SerializedName("t20StatsList")
	val t20StatsList: T20StatsList? = null,

	@field:SerializedName("filter")
	val filter: Filter? = null,
)

data class Filter(

	@field:SerializedName("matchtype")
	val matchtype: List<MatchtypeItem?>? = null
)

data class MatchtypeItem(

	@field:SerializedName("matchTypeDesc")
	val matchTypeDesc: String? = null,

	@field:SerializedName("matchTypeId")
	val matchTypeId: String? = null
)

data class TestStatsList(

	@field:SerializedName("headers")
	val headers: List<String?>? = null,

	@field:SerializedName("values")
	val values: List<ValuesItemStats?>? = null
)

data class OdiStatsList(

	@field:SerializedName("headers")
	val headers: List<String?>? = null,

	@field:SerializedName("values")
	val values: List<ValuesItemStats?>? = null
)

data class ValuesItemStats(

	@field:SerializedName("values")
	val values: List<String?>? = null
)

data class T20StatsList(

	@field:SerializedName("headers")
	val headers: List<String?>? = null,

	@field:SerializedName("values")
	val values: List<ValuesItemStats?>? = null
)
