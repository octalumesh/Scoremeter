package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class SeriesCricbuzzResponse(

	@field:SerializedName("appIndex")
	val appIndex: AppIndex? = null,

	@field:SerializedName("seriesMapProto")
	val seriesMapProto: List<SeriesMapProtoItem?>? = null
)

data class SeriesMapProtoItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("series")
	val series: List<SeriesItem?>? = null
)

data class AppIndex(

	@field:SerializedName("webURL")
	val webURL: String? = null,

	@field:SerializedName("seoTitle")
	val seoTitle: String? = null
)

data class SeriesItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("startDt")
	val startDt: String? = null,

	@field:SerializedName("endDt")
	val endDt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
