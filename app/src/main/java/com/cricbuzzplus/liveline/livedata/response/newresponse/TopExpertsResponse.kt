package com.cricbuzzplus.liveline.livedata.response.newresponse

import com.google.gson.annotations.SerializedName

data class TopExpertsResponse(

	@field:SerializedName("matchList")
	val matchList: List<MatchListItem?>? = null,

	@field:SerializedName("tossList")
	val tossList: List<MatchListItem?>? = null
)

data class MatchListItem(

	@field:SerializedName("name")
	val firstName: String? = null,

	@field:SerializedName("predicationPrecentMatch")
	val predicationPrecentMatch: Int? = null,

	@field:SerializedName("TossPred")
	val tossPred: Int? = null,

	@field:SerializedName("points")
	val points: Int? = null,

	@field:SerializedName("predicationPrecenttoss")
	val predicationPrecenttoss: Int? = null,

	@field:SerializedName("profilepicture")
	val profilepicture: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("MatchPred")
	val matchPred: Int? = null
)

data class TossListItem(

	@field:SerializedName("firstName")
	val firstName: String? = null,

	@field:SerializedName("predicationPrecentMatch")
	val predicationPrecentMatch: Int? = null,

	@field:SerializedName("TossPred")
	val tossPred: Int? = null,

	@field:SerializedName("predicationPrecenttoss")
	val predicationPrecenttoss: Int? = null,

	@field:SerializedName("profilepicture")
	val profilepicture: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("MatchPred")
	val matchPred: Int? = null
)
