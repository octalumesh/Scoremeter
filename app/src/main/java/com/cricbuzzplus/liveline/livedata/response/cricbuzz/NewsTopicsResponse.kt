package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class NewsTopicsResponse(

	@field:SerializedName("topics")
	val topics: List<TopicsItem?>? = null
)

data class TopicsItem(

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("headline")
	val headline: String? = null
)
