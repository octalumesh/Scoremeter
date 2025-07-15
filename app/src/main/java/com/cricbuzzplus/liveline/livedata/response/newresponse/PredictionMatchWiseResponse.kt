package com.cricbuzzplus.liveline.livedata.response.newresponse

import com.google.gson.annotations.SerializedName

data class PredictionMatchWiseResponse(

	@field:SerializedName("match")
	val match: Match? = null,

	@field:SerializedName("toss")
	val toss: Toss? = null
)

data class Match(

	@field:SerializedName("teamAPredict")
	val teamAPredict: Int? = null,

	@field:SerializedName("teamBPredict")
	val teamBPredict: Int? = null,

	@field:SerializedName("my_prediction")
	val myPrediction: Boolean? = null,

	@field:SerializedName("match_predict_count")
	val matchPredictCount: Int? = null
)

data class Toss(

	@field:SerializedName("teamAPredict")
	val teamAPredict: Int? = null,

	@field:SerializedName("teamBPredict")
	val teamBPredict: Int? = null,

	@field:SerializedName("my_prediction")
	val myPrediction: Boolean? = null,

	@field:SerializedName("toss_predict_count")
	val tossPredictCount: Int? = null
)
