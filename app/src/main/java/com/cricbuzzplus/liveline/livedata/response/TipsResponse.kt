package com.cricbuzzplus.liveline.livedata.response

import com.google.firebase.Timestamp

data class TipsResponse(
    var title :String ="",
    var message :String = "",
    var createdAt : Timestamp = Timestamp.now(),
    var date : Timestamp = Timestamp.now()
)
