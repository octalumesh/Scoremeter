package com.cricbuzzplus.liveline.livedata.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResponse(

    @field:SerializedName("TossPass")
    val tossPass: Int? = null,

    @field:SerializedName("otpExpireAt")
    val otpExpireAt: String? = null,

    @field:SerializedName("MatchFail")
    val matchFail: Int? = null,

    @field:SerializedName("mobile_no")
    val mobile: String? = null,

    @field:SerializedName("active")
    val active: Boolean? = null,

    @field:SerializedName("otp")
    val otp: Int? = null,

    @field:SerializedName("socialType")
    val socialType: String? = null,

    @field:SerializedName("points")
    val points: Int? = null,

    @field:SerializedName("MatchPred")
    val matchPred: Int? = null,

    @field:SerializedName("TossFail")
    val tossFail: Int? = null,

    @field:SerializedName("name")
    val firstName: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("TossPred")
    val tossPred: Int? = null,

    @field:SerializedName("followers")
    val followers: Int? = null,

    @field:SerializedName("ratting")
    val ratting: String? = null,

    @field:SerializedName("socialId")
    val socialId: String? = null,

    @field:SerializedName("profilepicture")
    val profilepicture: String? = null,

    @field:SerializedName("fcmTokan")
    val fcmTokan: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("email2")
    val email2: String? = null,

    @field:SerializedName("mobile2")
    val mobile2: String? = null,

    @field:SerializedName("referId")
    val referId: String? = null,

    @field:SerializedName("referBy")
    val referBy: String? = null,

    @field:SerializedName("amount")
    val amount: Int? = null,

    @field:SerializedName("tossPoint")
    val tossPoint: Int? = null,

    @field:SerializedName("referamount")
    val referamount: Int? = null,

    @field:SerializedName("MatchPass")
    val matchPass: Int? = null,

    @field:SerializedName("prize")
    val prize: Prize? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null,

    ) : Parcelable

@Parcelize
data class Prize(

    @field:SerializedName("secondPrize")
    val secondPrize: Int? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("fourthPrize")
    val fourthPrize: Int? = null,

    @field:SerializedName("firstPrize")
    val firstPrize: Int? = null,

    @field:SerializedName("referAmount")
    val referAmount: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("thirdPrize")
    val thirdPrize: Int? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
) : Parcelable
