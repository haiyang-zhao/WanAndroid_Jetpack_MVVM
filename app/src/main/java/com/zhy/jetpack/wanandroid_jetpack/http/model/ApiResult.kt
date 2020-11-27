package com.zhy.jetpack.wanandroid_jetpack.http.model

data class ApiResult<T>(val data: T, val errorCode: Int = 0, val errorMsg: String = "") {
    fun success() = errorCode == 0
}