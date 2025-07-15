package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class NewsDetailResponse(

	@field:SerializedName("publishTime")
	val publishTime: String? = null,

	@field:SerializedName("format")
	val format: List<FormatItem?>? = null,

	@field:SerializedName("source")
	val source: String? = null,

	@field:SerializedName("content")
	val content: List<ContentItem?>? = null,

	@field:SerializedName("tags")
	val tags: List<TagsItem?>? = null,

	@field:SerializedName("coverImage")
	val coverImage: NewsDetailCoverImage? = null,

	@field:SerializedName("intro")
	val intro: String? = null,

	@field:SerializedName("context")
	val context: String? = null,

	@field:SerializedName("lastUpdatedTime")
	val lastUpdatedTime: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("storyType")
	val storyType: String? = null,

	@field:SerializedName("headline")
	val headline: String? = null,

	@field:SerializedName("embeds")
	val embeds: List<EmbedsItem?>? = null,

	@field:SerializedName("authors")
	val authors: List<AuthorsItem?>? = null
)

data class EmbedsItem(

	@field:SerializedName("embedType")
	val embedType: String? = null,

	@field:SerializedName("embedValue")
	val embedValue: String? = null,

	@field:SerializedName("key")
	val key: String? = null
)

data class ContentItem(

	@field:SerializedName("content")
	val content: Content? = null
)

data class ValueItem(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("value")
	val value: String? = null
)

data class FormatItem(

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("value")
	val value: List<ValueItem?>? = null
)

data class NewsDetailCoverImage(

	@field:SerializedName("caption")
	val caption: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("source")
	val source: String? = null
)

data class TagsItem(

	@field:SerializedName("itemId")
	val itemId: String? = null,

	@field:SerializedName("itemName")
	val itemName: String? = null,

	@field:SerializedName("itemType")
	val itemType: String? = null
)

data class Content(

	@field:SerializedName("hasFormat")
	val hasFormat: Boolean? = null,

	@field:SerializedName("contentValue")
	val contentValue: String? = null,

	@field:SerializedName("contentType")
	val contentType: String? = null
)

data class AuthorsItem(

	@field:SerializedName("imageId")
	val imageId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
