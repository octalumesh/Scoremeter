package com.cricbuzzplus.liveline.livedata.response

import com.google.firebase.firestore.FieldValue

data class PollModel(
    val match_id:Int,
    val my_vote:String,
    val deviceid:String,
    val created: FieldValue
)
