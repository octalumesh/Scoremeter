package com.cricbuzzplus.liveline.livedata.response.newresponse

import com.google.gson.annotations.SerializedName

data class AllExpertsResponse(

	@field:SerializedName("AllExpertsResponse")
	val allExpertsResponse: List<AllExpertsResponseItem?>? = null
)

data class AllExpertsResponseItem(

	@field:SerializedName("TossPass")
	val tossPass: Int? = null,

	@field:SerializedName("predicationPrecent")
	val predicationPrecent: Int? = null,

	@field:SerializedName("otpExpireAt")
	val otpExpireAt: String? = null,

	@field:SerializedName("MatchFail")
	val matchFail: Int? = null,

	@field:SerializedName("mobile")
	val mobile: String? = null,

	@field:SerializedName("active")
	val active: Boolean? = null,

	@field:SerializedName("otp")
	val otp: Int? = null,

	@field:SerializedName("socialType")
	val socialType: String? = null,

	@field:SerializedName("points")
	val points: Int? = null,

	@field:SerializedName("MatchPred")
	val matchPred: Int? = null,

	@field:SerializedName("TossFail")
	val tossFail: Int? = null,

	@field:SerializedName("name")
	val firstName: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("TossPred")
	val tossPred: Int? = null,

	@field:SerializedName("followers")
	val followers: Int? = null,

	@field:SerializedName("ratting")
	val ratting: String? = null,

	@field:SerializedName("socialId")
	val socialId: String? = null,

	@field:SerializedName("profilepicture")
	val profilepicture: String? = null,

	@field:SerializedName("fcmTokan")
	val fcmTokan: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("MatchPass")
	val matchPass: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
