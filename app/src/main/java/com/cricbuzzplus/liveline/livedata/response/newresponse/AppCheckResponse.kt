package com.cricbuzzplus.liveline.livedata.response.newresponse

import com.google.gson.annotations.SerializedName

data class AppCheckResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("error")
	val error: Any? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class Data(

	@field:SerializedName("packagename")
	val packagename: String? = null,

	@field:SerializedName("active")
	val active: Boolean? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("otherurl")
	val otherurl: String? = null,

	@field:SerializedName("platform")
	val platform: String? = null,

	@field:SerializedName("deliverydate")
	val deliverydate: String? = null,

	@field:SerializedName("contect")
	val contect: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("weburl")
	val weburl: String? = null,

	@field:SerializedName("clientname")
	val clientname: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("playstoreurl")
	val playstoreurl: String? = null,

	@field:SerializedName("paid")
	val paid: Boolean? = null,

	@field:SerializedName("initdate")
	val initdate: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("deactivemessage")
	val deactivemessage: String? = null,

	@field:SerializedName("adminurl")
	val adminurl: String? = null,

	@field:SerializedName("appstoreurl")
	val appstoreurl: String? = null
)
