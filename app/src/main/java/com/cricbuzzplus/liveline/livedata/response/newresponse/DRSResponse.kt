package com.cricbuzzplus.liveline.livedata.response.newresponse

import com.google.gson.annotations.SerializedName

data class DRSResponse(

	@field:SerializedName("team_a_optdrs")
	val teamAOptdrs: Int? = null,

	@field:SerializedName("team_a_short")
	val teamAShort: String? = null,

	@field:SerializedName("matchs")
	val matchs: String? = null,

	@field:SerializedName("team_b_image")
	val teamBImage: String? = null,

	@field:SerializedName("team_b_optdrs")
	val teamBOptdrs: Int? = null,

	@field:SerializedName("team_a_totaldrs")
	val teamATotaldrs: Int? = null,

	@field:SerializedName("match_id")
	val matchId: String? = null,

	@field:SerializedName("team_a_image")
	val teamAImage: String? = null,

	@field:SerializedName("team_b_totaldrs")
	val teamBTotaldrs: Int? = null,

	@field:SerializedName("team_b")
	val teamB: String? = null,

	@field:SerializedName("team_a")
	val teamA: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("team_b_remaining")
	val teamBRemaining: Int? = null,

	@field:SerializedName("match_type")
	val matchType: String? = null,

	@field:SerializedName("team_b_short")
	val teamBShort: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("team_a_remaining")
	val teamARemaining: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
