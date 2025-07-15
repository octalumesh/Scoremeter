package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class PhotoGalleryCricResponse(

	@field:SerializedName("appIndex")
	val appIndex: AppIndexPhoto? = null,

	@field:SerializedName("publishedTime")
	val publishedTime: String? = null,

	@field:SerializedName("intro")
	val intro: String? = null,

	@field:SerializedName("photoId")
	val photoId: Int? = null,

	@field:SerializedName("photoGalleryDetails")
	val photoGalleryDetails: List<PhotoGalleryDetailsItem?>? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("headline")
	val headline: String? = null,

	@field:SerializedName("tags")
	val tags: List<TagsItemPhoto?>? = null
)

data class TagsItemPhoto(

	@field:SerializedName("itemId")
	val itemId: String? = null,

	@field:SerializedName("itemName")
	val itemName: String? = null,

	@field:SerializedName("itemType")
	val itemType: String? = null
)

data class PhotoGalleryDetailsItem(

	@field:SerializedName("imageId")
	val imageId: Int? = null,

	@field:SerializedName("caption")
	val caption: String? = null
)

data class AppIndexPhoto(

	@field:SerializedName("webURL")
	val webURL: String? = null,

	@field:SerializedName("seoTitle")
	val seoTitle: String? = null
)
