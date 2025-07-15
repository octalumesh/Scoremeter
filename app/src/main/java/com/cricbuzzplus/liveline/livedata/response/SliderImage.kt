package com.cricbuzzplus.liveline.livedata.response

import com.google.firebase.Timestamp

data class SliderImage(
    var image :String = "",
    var createdAt : Timestamp = Timestamp.now(),
    var link : String = "",
)
