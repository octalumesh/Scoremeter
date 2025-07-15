package com.cricbuzzplus.liveline.livedata.model

import com.google.firebase.Timestamp

data class ChatResponse(
    var name:String? = null,
    var senderid:String? = null,
    var msg:String?= null,
    var created: Timestamp?= null
)
