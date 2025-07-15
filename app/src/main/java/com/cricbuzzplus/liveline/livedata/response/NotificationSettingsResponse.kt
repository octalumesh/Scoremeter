package com.cricbuzzplus.liveline.livedata.response

import com.google.gson.annotations.SerializedName

data class NotificationSettingsResponse(

	@field:SerializedName("six")
	val six: String? = null,

	@field:SerializedName("fifty")
	val fifty: String? = null,

	@field:SerializedName("match_id")
	val matchId: String? = null,

	@field:SerializedName("hundrade")
	val hundrade: String? = null,

	@field:SerializedName("toss")
	val toss: String? = null,

	@field:SerializedName("token")
	val token: String? = null,

	@field:SerializedName("wicket")
	val wicket: String? = null,

	@field:SerializedName("twohundrade")
	val twohundrade: String? = null,

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("four")
	val four: String? = null,

	@field:SerializedName("matchStart")
	val matchStart: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
