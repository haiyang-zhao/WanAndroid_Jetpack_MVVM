package com.zhy.jetpack.wanandroid_jetpack.http.model

class ApiException : Exception {
    var errorCode: Int = 0
    var errorMsg: String = ""

    constructor(
        errorCode: Int,
        errorMsg: String
    ) : super("[errorMsg:$errorMsg,errorCode:$errorCode]") {
        this.errorCode = errorCode
        this.errorMsg = errorMsg
    }

    constructor(errorMsg: String, throwable: Throwable) : super(errorMsg, throwable) {
        this.errorMsg = errorMsg
    }
}