package com.cricbuzzplus.liveline.livedata.response.newresponse

import com.google.gson.annotations.SerializedName

data class KYCDetailsResponse(

	@field:SerializedName("upiNumber")
	val upiNumber: String? = null,

	@field:SerializedName("panVerify")
	val panVerify: String? = null,

	@field:SerializedName("accountVerify")
	val accountVerify: String? = null,

	@field:SerializedName("bankName")
	val bankName: String? = null,

	@field:SerializedName("panNumber")
	val panNumber: String? = null,

	@field:SerializedName("accountNumber")
	val accountNumber: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("accountHolderName")
	val accountHolderName: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("aadharFront")
	val aadharFront: String? = null,

	@field:SerializedName("aadharNumber")
	val aadharNumber: String? = null,

	@field:SerializedName("dob")
	val dob: String? = null,

	@field:SerializedName("aadharBack")
	val aadharBack: String? = null,

	@field:SerializedName("bankPassbook")
	val bankPassbook: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("panCardImg")
	val panCardImg: String? = null,

	@field:SerializedName("panName")
	val panName: String? = null,

	@field:SerializedName("aadharVerify")
	val aadharVerify: String? = null,

	@field:SerializedName("ifscCode")
	val ifscCode: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
