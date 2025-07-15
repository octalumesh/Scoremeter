package com.cricbuzzplus.liveline.livedata.response;

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseResponse {

    @SerializedName("status")
    @Expose
    var status: Boolean = false
    @SerializedName("msg")
    @Expose
    var message: String = ""

    @SerializedName("status_code")
    @Expose
    var statusCode: String = ""

    @SerializedName("token")
    @Expose
    var token: String = ""

    @SerializedName("errors")
    @Expose
    var errors: String ?= null

//    @SerializedName("otp")
//    @Expose
//    var otp: Int = 0

   // var isIdeal : Boolean =false

}
