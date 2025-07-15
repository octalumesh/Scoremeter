package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class PlayerBattingResponse(

	@field:SerializedName("headers")
	val headers: List<String?>? = null,

	@field:SerializedName("values")
	val values: List<ValuesItem?>? = null,

	@field:SerializedName("seriesSpinner")
	val seriesSpinner: List<SeriesSpinnerItem?>? = null
)

data class SeriesSpinnerItem(

	@field:SerializedName("seriesName")
	val seriesName: String? = null
)

data class ValuesItem(

	@field:SerializedName("values")
	val values: List<String?>? = null
)
