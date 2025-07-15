package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class SquadNewResponse(

	@field:SerializedName("team1")
	val team1: SquadNewTeam1? = null,

	@field:SerializedName("team2")
	val team2: SquadNewTeam2? = null
)

data class SupportStaffItem(

	@field:SerializedName("teamName")
	val teamName: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("nickName")
	val nickName: String? = null,

	@field:SerializedName("faceImageId")
	val faceImageId: Int? = null,

	@field:SerializedName("teamId")
	val teamId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("isSupportStaff")
	val isSupportStaff: Boolean? = null
)

data class BenchItem(

	@field:SerializedName("teamName")
	val teamName: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("nickName")
	val nickName: String? = null,

	@field:SerializedName("keeper")
	val keeper: Boolean? = null,

	@field:SerializedName("battingStyle")
	val battingStyle: String? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("captain")
	val captain: Boolean? = null,

	@field:SerializedName("substitute")
	val substitute: Boolean? = null,

	@field:SerializedName("bowlingStyle")
	val bowlingStyle: String? = null,

	@field:SerializedName("faceImageId")
	val faceImageId: Int? = null,

	@field:SerializedName("teamId")
	val teamId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("playingXIChange")
	val playingXIChange: String? = null
)

data class Team(

	@field:SerializedName("teamName")
	val teamName: String? = null,

	@field:SerializedName("imageId")
	val imageId: Int? = null,

	@field:SerializedName("teamId")
	val teamId: Int? = null,

	@field:SerializedName("teamSName")
	val teamSName: String? = null
)

data class Players(

	@field:SerializedName("bench")
	val bench: List<PlayingXIItem?>? = null,

	@field:SerializedName("playing XI")
	val playingXI: List<PlayingXIItem?>? = null,

	@field:SerializedName("Squad")
	val squad: List<PlayingXIItem?>? = null,

	@field:SerializedName("support staff")
	val supportStaff: List<SupportStaffItem?>? = null
)

data class SquadNewTeam1(

	@field:SerializedName("players")
	val players: Players? = null,

	@field:SerializedName("team")
	val team: Team? = null
)

data class PlayingXIItem(

	@field:SerializedName("teamName")
	val teamName: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("nickName")
	val nickName: String? = null,

	@field:SerializedName("keeper")
	val keeper: Boolean? = null,

	@field:SerializedName("battingStyle")
	val battingStyle: String? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("captain")
	val captain: Boolean? = null,

	@field:SerializedName("substitute")
	val substitute: Boolean? = null,

	@field:SerializedName("bowlingStyle")
	val bowlingStyle: String? = null,

	@field:SerializedName("faceImageId")
	val faceImageId: Int? = null,

	@field:SerializedName("teamId")
	val teamId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("playingXIChange")
	val playingXIChange: String? = null

)

data class SquadNewTeam2(

	@field:SerializedName("players")
	val players: Players? = null,

	@field:SerializedName("team")
	val team: Team? = null
)
