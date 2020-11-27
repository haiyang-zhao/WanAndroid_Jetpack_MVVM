package com.zhy.jetpack.wanandroid_jetpack.http.state

data class PagerState<T>(
    val data: MutableList<T> = mutableListOf(),
    val hasMore: Boolean = false,
    val firstLoad: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMsg: String = ""
) {
    fun isEmpty() = data.isEmpty()
}