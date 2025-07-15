package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class ArchivesCricbuzzResponse(

	@field:SerializedName("seriesMapProto")
	val seriesMapProto: List<ArchivesMapProtoItem?>? = null
)

data class ArchivesItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("startDt")
	val startDt: String? = null,

	@field:SerializedName("endDt")
	val endDt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class ArchivesMapProtoItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("series")
	val series: List<ArchivesItem?>? = null
)
