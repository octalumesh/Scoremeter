package com.cricbuzzplus.liveline.livedata.response.newresponse

import com.google.gson.annotations.SerializedName
import com.cricbuzzplus.liveline.livedata.response.LoginResponse

data class WithdrawRequestResponse(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("kycId")
	val kycId: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null,

	@field:SerializedName("userDetails")
	val userDetails: LoginResponse? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

