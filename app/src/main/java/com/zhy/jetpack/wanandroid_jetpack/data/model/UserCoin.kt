package com.zhy.jetpack.wanandroid_jetpack.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserCoin(
    val coinCount: Int,
    val rank: Int,
    val userId: Int,
    val username: String
) : Parcelable