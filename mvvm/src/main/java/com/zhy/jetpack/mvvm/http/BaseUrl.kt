package com.zhy.jetpack.mvvm.http

import okhttp3.HttpUrl

interface BaseUrl {
    fun url(): HttpUrl
}