package com.cricbuzzplus.liveline.livedata.model

import com.google.firebase.firestore.FieldValue


data class ChatModel (
        var name:String? = null,
        var senderid:String? = null,
        var msg:String?= null,
        var created:FieldValue ?= null

        )

