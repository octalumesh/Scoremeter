package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class PointTableNewCricResponse(

	@field:SerializedName("appIndex")
	val appIndex: AppIndexNew? = null,

	@field:SerializedName("pointsTable")
	val pointsTable: List<PointsTableItem?>? = null
)

data class TeamMatchesItem(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("opponentId")
	val opponentId: Int? = null,

	@field:SerializedName("opponentSName")
	val opponentSName: String? = null,

	@field:SerializedName("winner")
	val winner: Int? = null,

	@field:SerializedName("matchName")
	val matchName: String? = null,

	@field:SerializedName("opponent")
	val opponent: String? = null,

	@field:SerializedName("opponentImageId")
	val opponentImageId: Int? = null,

	@field:SerializedName("startdt")
	val startdt: String? = null,

	@field:SerializedName("matchId")
	val matchId: Int? = null
)

data class PointsTableItem(

	@field:SerializedName("groupName")
	val groupName: String? = null,

	@field:SerializedName("pointsTableInfo")
	val pointsTableInfo: List<PointsTableInfoItem?>? = null
)

data class PointsTableInfoItem(

	@field:SerializedName("teamName")
	val teamName: String? = null,

	@field:SerializedName("matchesWon")
	val matchesWon: Int? = 0,

	@field:SerializedName("noRes")
	val noRes: Int? = 0,

	@field:SerializedName("matchesPlayed")
	val matchesPlayed: Int? = 0,

	@field:SerializedName("matchesLost")
	val matchesLost: Int? = 0,

	@field:SerializedName("form")
	val form: List<String?>? = null,

	@field:SerializedName("teamId")
	val teamId: Int? = null,

	@field:SerializedName("teamFullName")
	val teamFullName: String? = null,

	@field:SerializedName("teamMatches")
	val teamMatches: List<TeamMatchesItem?>? = null,

	@field:SerializedName("teamQualifyStatus")
	val teamQualifyStatus: String? = null,

	@field:SerializedName("teamImageId")
	val teamImageId: Int? = null,

	@field:SerializedName("points")
	val points: Int? = 0,

	@field:SerializedName("nrr")
	val nrr: String? = null
)

data class AppIndexNew(

	@field:SerializedName("webURL")
	val webURL: String? = null,

	@field:SerializedName("seoTitle")
	val seoTitle: String? = null
)
