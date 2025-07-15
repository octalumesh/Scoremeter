package com.cricbuzzplus.liveline.livedata.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModelResponse(
    val email: String ?= null,
    val uid: String ?= null,
    val profileImage: String ?= null,
    val name: String ?= null,
    val number: String ?= null,
    val token: String ?= null,
    val online: Boolean ?= null,
    val createdAt: Timestamp ?= null,
    val loginat: Timestamp ?= null,
):Parcelable