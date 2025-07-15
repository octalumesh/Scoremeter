package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class StadiumStatsResponse(

	@field:SerializedName("venueStats")
	val venueStats: List<VenueStatsItem?>? = null
)

data class VenueStatsItem(

	@field:SerializedName("value")
	val value: String? = null,

	@field:SerializedName("key")
	val key: String? = null
)
