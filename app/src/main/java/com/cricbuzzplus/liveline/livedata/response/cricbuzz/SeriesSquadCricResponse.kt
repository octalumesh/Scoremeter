package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class SeriesSquadCricResponse(

	@field:SerializedName("appIndex")
	val appIndex: AppIndexS? = null,

	@field:SerializedName("squads")
	val squads: List<SquadsItem?>? = null,

	@field:SerializedName("seriesName")
	val seriesName: String? = null,

	@field:SerializedName("seriesId")
	val seriesId: Int? = null
)

data class SquadsItem(

	@field:SerializedName("imageId")
	val imageId: Int? = null,

	@field:SerializedName("teamId")
	val teamId: Int? = null,

	@field:SerializedName("squadId")
	val squadId: Int? = null,

	@field:SerializedName("squadType")
	val squadType: String? = null,

	@field:SerializedName("isHeader")
	val isHeader: Boolean? = null
)

data class AppIndexS(

	@field:SerializedName("webURL")
	val webURL: String? = null,

	@field:SerializedName("seoTitle")
	val seoTitle: String? = null
)
