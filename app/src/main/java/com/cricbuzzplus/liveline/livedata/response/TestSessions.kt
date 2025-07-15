package com.cricbuzzplus.liveline.livedata.response

data class TestSessions(
    var overs :Int ?= null,
    var min :Int ?= null,
    var max : Int ?= null,
    var pass :String ?= null,
    var open : String ?= null,
    var rate : String ?= null,
    var wkt : String ?= null,
    var fav : String ?= null
)
