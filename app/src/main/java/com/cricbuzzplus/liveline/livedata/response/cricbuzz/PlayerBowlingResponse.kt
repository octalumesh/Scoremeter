package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class PlayerBowlingResponse(

	@field:SerializedName("headers")
	val headers: List<String?>? = null,

	@field:SerializedName("values")
	val values: List<ValuesItemBowling?>? = null,

	@field:SerializedName("seriesSpinner")
	val seriesSpinner: List<SeriesSpinnerItemBowling?>? = null
)

data class SeriesSpinnerItemBowling(

	@field:SerializedName("seriesName")
	val seriesName: String? = null,

	@field:SerializedName("seriesId")
	val seriesId: Int? = null
)

data class ValuesItemBowling(

	@field:SerializedName("values")
	val values: List<String?>? = null
)
