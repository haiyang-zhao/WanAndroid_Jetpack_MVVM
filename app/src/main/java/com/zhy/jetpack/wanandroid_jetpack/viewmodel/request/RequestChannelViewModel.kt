package com.zhy.jetpack.wanandroid_jetpack.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tencent.mmkv.MMKV
import com.zhy.jetpack.mvvm.base.viewmodel.BaseViewModel
import com.zhy.jetpack.wanandroid_jetpack.ext.request
import com.zhy.jetpack.wanandroid_jetpack.http.apiClient
import com.zhy.jetpack.wanandroid_jetpack.http.model.ApiResult
import com.zhy.jetpack.wanandroid_jetpack.http.state.ResultState
import com.zhy.jetpack.wanandroid_jetpack.utils.KEY_MY_CHANNELS
import com.zhy.jetpack.wanandroid_jetpack.view.adapter.IndicatorTitle

class RequestChannelViewModel : BaseViewModel() {

    var chaptersData = MutableLiveData<ResultState<IndicatorTitle>>()

    fun requestChapters() {
        request({ myChannels() }, {
            chaptersData.value = ResultState(data = it, isSuccess = true)
        }, {
            chaptersData.value = ResultState(isSuccess = true, errorMsg = it.errorMsg)
        })
    }

    private suspend fun myChannels(): ApiResult<MutableList<IndicatorTitle>> {
        try {
            val gson = Gson()
            val mmkv = MMKV.defaultMMKV()
            val myChannelsStr = mmkv.decodeString(KEY_MY_CHANNELS)
            val data: MutableList<IndicatorTitle> =
                if (myChannelsStr.isNullOrEmpty()) {
                    val channels = apiClient.channelTree()
                        .data[0].children
                        .map { item -> IndicatorTitle(item.id, item.name) }.toMutableList()
                    mmkv.putString(KEY_MY_CHANNELS, gson.toJson(channels))
                    channels
                } else {
                    gson.fromJson(
                        myChannelsStr, object : TypeToken<MutableList<IndicatorTitle>>() {}.type
                    )
                }
            return ApiResult(data)
        } catch (e: Exception) {
            return ApiResult(mutableListOf(), errorCode = -1, errorMsg = e.toString())
        }
    }

}