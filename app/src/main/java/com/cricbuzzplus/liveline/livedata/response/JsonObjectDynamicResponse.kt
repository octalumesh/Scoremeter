package com.cricbuzzplus.liveline.livedata.response

import com.google.gson.annotations.SerializedName

open class JsonObjectDynamicResponse: BaseResponse(){

    @SerializedName("data")
    var  data: LinkedHashMap<String, LinkedHashMap<String, List<OverResponseItem>>>?=null

}