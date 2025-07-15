package com.cricbuzzplus.liveline.livedata.response

import com.google.gson.annotations.SerializedName

data class SeriesListResponse(

	@field:SerializedName("SeriesListResponse")
	val seriesListResponse: List<SeriesListResponseItem?>? = null
)

data class SeriesListResponseItem(

	@field:SerializedName("end_date")
	val endDate: String? = null,

	@field:SerializedName("series_date")
	val seriesDate: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("series")
	val series: String? = null,

	@field:SerializedName("series_id")
	val seriesId: Int? = null,

	@field:SerializedName("total_matches")
	val totalMatches: Int? = null,

	@field:SerializedName("start_date")
	val startDate: String? = null
)
