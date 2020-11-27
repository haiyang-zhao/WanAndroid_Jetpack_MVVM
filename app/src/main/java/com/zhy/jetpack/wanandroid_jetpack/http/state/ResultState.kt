package com.zhy.jetpack.wanandroid_jetpack.http.state

class ResultState<T>(
    val data: MutableList<T> = mutableListOf(),
    val isSuccess: Boolean = false,
    val errorMsg: String = ""
)