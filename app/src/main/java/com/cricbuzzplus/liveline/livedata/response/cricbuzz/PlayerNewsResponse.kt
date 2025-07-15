package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class PlayerNewsResponse(

	@field:SerializedName("lastUpdatedTime")
	val lastUpdatedTime: String? = null,

	@field:SerializedName("storyList")
	val storyList: List<StoryListItem?>? = null
)

data class CoverImage(

	@field:SerializedName("caption")
	val caption: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("source")
	val source: String? = null
)

data class StoryListItem(

	@field:SerializedName("story")
	val story: Story? = null
)

data class Story(

	@field:SerializedName("hline")
	val hline: String? = null,

	@field:SerializedName("imageId")
	val imageId: Int? = null,

	@field:SerializedName("intro")
	val intro: String? = null,

	@field:SerializedName("pubTime")
	val pubTime: String? = null,

	@field:SerializedName("seoHeadline")
	val seoHeadline: String? = null,

	@field:SerializedName("coverImage")
	val coverImage: CoverImage? = null,

	@field:SerializedName("context")
	val context: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("source")
	val source: String? = null,

	@field:SerializedName("storyType")
	val storyType: String? = null
)
