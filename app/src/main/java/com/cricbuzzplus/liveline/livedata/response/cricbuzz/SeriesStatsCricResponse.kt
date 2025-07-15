package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class SeriesStatsCricResponse(

	@field:SerializedName("types")
	val types: List<TypesItem?>? = null
)

data class TypesItem(

	@field:SerializedName("header")
	val header: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("value")
	val value: String? = null
)
