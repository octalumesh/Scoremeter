package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class SeriesVenuesResponse(

	@field:SerializedName("seriesVenue")
	val seriesVenue: List<SeriesVenueItem?>? = null,

	@field:SerializedName("seriesName")
	val seriesName: String? = null,

	@field:SerializedName("seriesId")
	val seriesId: Int? = null
)

data class SeriesVenueItem(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("imageId")
	val imageId: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("ground")
	val ground: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
