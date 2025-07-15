package com.cricbuzzplus.liveline.livedata.response.cricbuzz

import com.google.gson.annotations.SerializedName

data class PlayerInfoResponse(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("teams")
	val teams: String? = null,

	@field:SerializedName("nickName")
	val nickName: String? = null,

	@field:SerializedName("bio")
	val bio: String? = null,

	@field:SerializedName("bowl")
	val bowl: String? = null,

	@field:SerializedName("intlTeam")
	val intlTeam: String? = null,

	@field:SerializedName("birthPlace")
	val birthPlace: String? = null,

	@field:SerializedName("rankings")
	val rankings: Rankings? = null,

	@field:SerializedName("bat")
	val bat: String? = null,

	@field:SerializedName("DoB")
	val doB: String? = null,

	@field:SerializedName("faceImageId")
	val faceImageId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("DoBFormat")
	val doBFormat: String? = null
)

data class BatItem(

	@field:SerializedName("t20BestRank")
	val t20BestRank: String? = null,

	@field:SerializedName("t20Rank")
	val t20Rank: String? = null,

	@field:SerializedName("testRank")
	val testRank: String? = null,

	@field:SerializedName("testBestRank")
	val testBestRank: String? = null,

	@field:SerializedName("odiRank")
	val odiRank: String? = null,

	@field:SerializedName("odiBestRank")
	val odiBestRank: String? = null
)

data class BowlItem(

	@field:SerializedName("t20BestRank")
	val t20BestRank: String? = null,

	@field:SerializedName("t20Rank")
	val t20Rank: String? = null,

	@field:SerializedName("testRank")
	val testRank: String? = null,

	@field:SerializedName("testBestRank")
	val testBestRank: String? = null,

	@field:SerializedName("odiRank")
	val odiRank: String? = null,

	@field:SerializedName("odiBestRank")
	val odiBestRank: String? = null
)

data class Rankings(

	@field:SerializedName("all")
	val all: AllItem? = null,

	@field:SerializedName("bat")
	val bat: BatItem? = null,

	@field:SerializedName("bowl")
	val bowl: BowlItem? = null
)

data class AllItem(

	@field:SerializedName("t20BestRank")
	val t20BestRank: String? = null,

	@field:SerializedName("t20Rank")
	val t20Rank: String? = null,

	@field:SerializedName("testRank")
	val testRank: String? = null,

	@field:SerializedName("testBestRank")
	val testBestRank: String? = null,

	@field:SerializedName("odiRank")
	val odiRank: String? = null,

	@field:SerializedName("odiBestRank")
	val odiBestRank: String? = null


)
