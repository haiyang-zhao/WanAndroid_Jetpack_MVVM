package com.zhy.jetpack.wanandroid_jetpack.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.zhy.jetpack.mvvm.base.viewmodel.BaseViewModel
import com.zhy.jetpack.wanandroid_jetpack.ext.request
import com.zhy.jetpack.wanandroid_jetpack.http.apiClient
import com.zhy.jetpack.wanandroid_jetpack.http.model.ApiResult
import com.zhy.jetpack.wanandroid_jetpack.http.state.ResultState
import com.zhy.jetpack.wanandroid_jetpack.utils.cacheMyChannel
import com.zhy.jetpack.wanandroid_jetpack.utils.curChannels
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
        return try {
            var myChannels = curChannels()
            if (myChannels == null) {
                myChannels = apiClient.channelTree()
                    .data[0].children
                    .map { item -> IndicatorTitle(item.id, item.name) }.toMutableList()
                cacheMyChannel(myChannels)
            }
            ApiResult(myChannels)
        } catch (e: Exception) {
            ApiResult(mutableListOf(), errorCode = -1, errorMsg = e.toString())
        }
    }

}