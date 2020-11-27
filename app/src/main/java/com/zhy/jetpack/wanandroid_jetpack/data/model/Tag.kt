package com.zhy.jetpack.wanandroid_jetpack.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tag(val name: String, val url: String) : Parcelable