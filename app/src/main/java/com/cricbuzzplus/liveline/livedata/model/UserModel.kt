package com.cricbuzzplus.liveline.livedata.model

import com.google.firebase.firestore.FieldValue


data class UserModel(
    val email: String ?= null,
    val uid: String ?= null,
    val profileImage: String ?= null,
    val name: String ?= null,
    val number: String ?= null,
    val token: String ?= null,
    val online: Boolean ?= null,
    val createdAt: FieldValue ?= null,
    val loginat: FieldValue ?= null,
)