package com.cricbuzzplus.liveline.livedata.response.newresponse

import com.google.gson.annotations.SerializedName

data class CreatePredictionResponse(

	@field:SerializedName("matchDate")
	val matchDate: String? = null,

	@field:SerializedName("seriesName")
	val seriesName: String? = null,

	@field:SerializedName("matchPredict")
	val matchPredict: String? = null,

	@field:SerializedName("active")
	val active: Boolean? = null,

	@field:SerializedName("tossPredict")
	val tossPredict: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("teamAShort")
	val teamAShort: String? = null,

	@field:SerializedName("teamBShort")
	val teamBShort: String? = null,

	@field:SerializedName("teamA")
	val teamA: String? = null,

	@field:SerializedName("teamB")
	val teamB: String? = null,

	@field:SerializedName("match_type")
	val matchType: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("teamBImg")
	val teamBImg: String? = null,

	@field:SerializedName("teamAImg")
	val teamAImg: String? = null,

	@field:SerializedName("matchid")
	val matchid: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
