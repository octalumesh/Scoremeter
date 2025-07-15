package com.cricbuzzplus.liveline.livedata.response

import com.google.firebase.Timestamp

data class PollsResponse(
    var count_a :Int =0,
    var count_b :Int =0,
    var match_id :Int =0,
    var series_id :Int =0,
    var tie :Int =0,
    var match_date :String = "",
    var match_time :String = "",
    var series :String = "",
    var team_a_img :String = "",
    var team_a_name :String = "",
    var team_b_img :String = "",
    var team_b_name :String = "",
    var team_a_short :String = "",
    var team_b_short :String = "",
    var venue :String = "",
    var createdAt : Timestamp = Timestamp.now()
)
