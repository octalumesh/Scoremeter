package com.cricbuzzplus.liveline.livedata.response

import com.google.gson.annotations.SerializedName

data class CommenteryResponse(
    @SerializedName("data")
     var data: LinkedHashMap<String, LinkedHashMap<String, List<OverResponseItem>>>
)

data class OverResponseItem(

    @SerializedName("commentary_id")
     var commentaryId: Int = 0,

    @SerializedName("data")
     val data: CommenteryData? = null,

    @SerializedName("inning")
     val inning: Int = 0,

    @SerializedName("type")
     val type: Int = 0
)

data class CommenteryData(

    @SerializedName("description")
     var description: String? = null,

    @SerializedName("overs")
     val overs: String? = null,

    @SerializedName("title")
     val title: String? = null,

    @SerializedName("runs")
     val runs: String? = null,

    @SerializedName("wicket")
     val wicket: String? = null,

    @SerializedName("over")
     val over: String? = null,

    @SerializedName("batsman_2_balls")
     val batsman2Balls: String? = null,

    @SerializedName("bolwer_wickets")
     val bolwerWickets: String? = null,

    @SerializedName("batsman_1_runs")
     val batsman1Runs: String? = null,

    @SerializedName("batsman_1_balls")
     val batsman1Balls: String? = null,

    @SerializedName("batsman_1_name")
     val batsman1Name: String? = null,

    @SerializedName("bolwer_overs")
     val bolwerOvers: String? = null,

    @SerializedName("team")
     val team: String? = null,

    @SerializedName("team_score")
     val teamScore: String? = null,

    @SerializedName("team_wicket")
     val teamWicket: String? = null,

    @SerializedName("batsman_2_runs")
     val batsman2Runs: String? = null,

    @SerializedName("batsman_2_name")
     val batsman2Name: String? = null,

    @SerializedName("wickets")
     val wickets: String? = null,

    @SerializedName("bolwer_name")
     val bolwerName: String? = null,

    @SerializedName("bolwer_maidens")
     val bolwerMaidens: String? = null,

    @SerializedName("bolwer_runs")
     val bolwerRuns: String? = null
)