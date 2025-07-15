package com.cricbuzzplus.liveline.livedata.response.newresponse

import com.google.gson.annotations.SerializedName

data class BannerPopupResponse(

	@field:SerializedName("pages")
	val pages: Int? = null,

	@field:SerializedName("docs")
	val docs: List<DocsItem?>? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null
)

data class DocsItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
