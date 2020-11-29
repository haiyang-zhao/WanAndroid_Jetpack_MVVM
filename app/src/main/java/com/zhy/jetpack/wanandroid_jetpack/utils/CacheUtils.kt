package com.zhy.jetpack.wanandroid_jetpack.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tencent.mmkv.MMKV
import com.zhy.jetpack.wanandroid_jetpack.data.model.User
import com.zhy.jetpack.wanandroid_jetpack.view.adapter.IndicatorTitle


val gson = Gson()
val mmkv = MMKV.mmkvWithID(KEY_APP_NAME)

fun cacheUse(user: User) {
    mmkv.encode(KEY_CUR_USER, gson.toJson(user))
}

fun curUser(): User? {
    return mmkv.decodeString(KEY_CUR_USER)?.run {
        gson.fromJson(this, User::class.java)
    }
}

fun cacheMyChannel(channels: MutableList<IndicatorTitle>) {
    mmkv.encode(KEY_MY_CHANNELS, gson.toJson(channels))
}

fun curChannels(): MutableList<IndicatorTitle>? {
    return mmkv.decodeString(KEY_MY_CHANNELS)?.run {
        return gson.fromJson(this, object : TypeToken<MutableList<IndicatorTitle>>() {}.type)
    }
}

fun cacheIsLogin(isLogin: Boolean) {
    mmkv.encode(KEY_IS_LOGIN, isLogin)
}

fun isLogin(): Boolean {
    return mmkv.decodeBool(KEY_IS_LOGIN)
}
