package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class NewsCategoriesResponse(

	@field:SerializedName("storyType")
	val storyType: List<StoryTypeItem?>? = null
)

data class StoryTypeItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
