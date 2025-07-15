package com.cricbuzzplus.liveline.livedata.response.newresponse

import com.google.gson.annotations.SerializedName

data class DLSResponse(

	@field:SerializedName("over")
	val over: String? = null,

	@field:SerializedName("team_a_short")
	val teamAShort: String? = null,

	@field:SerializedName("matchs")
	val matchs: String? = null,

	@field:SerializedName("team_b_image")
	val teamBImage: String? = null,

	@field:SerializedName("match_id")
	val matchId: String? = null,

	@field:SerializedName("team_a_image")
	val teamAImage: String? = null,

	@field:SerializedName("first_inning_wicket")
	val firstInningWicket: String? = null,

	@field:SerializedName("team_b")
	val teamB: String? = null,

	@field:SerializedName("first_inning_score")
	val firstInningScore: String? = null,

	@field:SerializedName("team_a")
	val teamA: String? = null,

	@field:SerializedName("first_inning_team")
	val firstInningTeam: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("second_inning_Over_target")
	val secondInningOverTarget: String? = null,

	@field:SerializedName("second_inning_target")
	val secondInningTarget: String? = null,

	@field:SerializedName("team_b_short")
	val teamBShort: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
