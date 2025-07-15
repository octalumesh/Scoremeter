package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class PhotosResponse(

	@field:SerializedName("appIndex")
	val appIndex: AppIndexPhotos? = null,

	@field:SerializedName("photoGalleryInfoList")
	val photoGalleryInfoList: List<PhotoGalleryInfoListItem?>? = null
)

data class PhotoGalleryInfoListItem(

	@field:SerializedName("photoGalleryInfo")
	val photoGalleryInfo: PhotoGalleryInfo? = null,

	@field:SerializedName("ad")
	val ad: Ad? = null
)

data class AppIndexPhotos(

	@field:SerializedName("webURL")
	val webURL: String? = null,

	@field:SerializedName("seoTitle")
	val seoTitle: String? = null
)

data class Ad(

	@field:SerializedName("layout")
	val layout: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("position")
	val position: Int? = null
)

data class PhotoGalleryInfo(

	@field:SerializedName("imageId")
	val imageId: Int? = null,

	@field:SerializedName("publishedTime")
	val publishedTime: String? = null,

	@field:SerializedName("galleryId")
	val galleryId: Int? = null,

	@field:SerializedName("headline")
	val headline: String? = null
)
