package com.zhy.jetpack.wanandroid_jetpack.data.model

class Pagination<T>(
    val curPage: Int,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    val datas: MutableList<T>
) {
    fun hasMore() = !over
}