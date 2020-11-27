package com.zhy.jetpack.wanandroid_jetpack.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 微信公众号
 */

@Parcelize
data class OfficialAccount(
    val children: MutableList<OfficialAccount>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
) : Parcelable