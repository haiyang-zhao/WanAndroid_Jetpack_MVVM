package com.zhy.jetpack.wanandroid_jetpack.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val admin: Boolean,
    val chapterTops: MutableList<Article>,
    val coinCount: Int,
    val collectIds: MutableList<Int>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
) : Parcelable