package com.cricbuzzplus.liveline.livedata.response

import com.google.gson.annotations.SerializedName

data class PointListResponse(

	@field:SerializedName("PointListResponse")
	val pointListResponse: List<PointListResponseItem?>? = null
)

data class PointListResponseItem(

	@field:SerializedName("P")
	val P: String? = null,

	@field:SerializedName("teams")
	val teams: String? = null,

	@field:SerializedName("NR")
	val nR: String? = null,

	@field:SerializedName("W")
	val W: String? = null,

	@field:SerializedName("L")
	val L: String? = null,

	@field:SerializedName("Pts")
	val pts: String? = null,

	@field:SerializedName("NRR")
	val nRR: String? = null
)
