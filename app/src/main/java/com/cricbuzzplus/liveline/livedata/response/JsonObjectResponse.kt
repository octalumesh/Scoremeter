package com.cricbuzzplus.liveline.livedata.response

import com.google.gson.annotations.SerializedName

open class JsonObjectResponse<T> : BaseResponse(){

    @SerializedName("data")
    var data : T? = null

}