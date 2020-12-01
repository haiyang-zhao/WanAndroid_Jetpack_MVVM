package com.zhy.jetpack.wanandroid_jetpack.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.zhy.jetpack.mvvm.base.viewmodel.BaseViewModel
import com.zhy.jetpack.wanandroid_jetpack.data.model.Channel
import com.zhy.jetpack.wanandroid_jetpack.ext.request
import com.zhy.jetpack.wanandroid_jetpack.http.model.ApiResult
import com.zhy.jetpack.wanandroid_jetpack.http.state.ResultState
import com.zhy.jetpack.wanandroid_jetpack.utils.allChannel
import com.zhy.jetpack.wanandroid_jetpack.utils.cacheMyChannel
import com.zhy.jetpack.wanandroid_jetpack.utils.myChannels
import com.zhy.jetpack.wanandroid_jetpack.view.adapter.IndicatorTitle

class RequestChannelEditViewModel : BaseViewModel() {

    var myChannelData = MutableLiveData<ResultState<IndicatorTitle>>()
    var allChannelData =
        MutableLiveData<ResultState<Channel>>()

    fun requestMyChannels() {
        request({ getMyChannels() }, {
            myChannelData.value = ResultState(it, isSuccess = true)
        }, {
            myChannelData.value = ResultState(errorMsg = it.errorMsg, isSuccess = false)
        })
    }

    private fun getMyChannels(): ApiResult<MutableList<IndicatorTitle>> {
        return ApiResult(data = myChannels() ?: mutableListOf())
    }


    private fun getAllChannels(): ApiResult<MutableList<Channel>> {
        return ApiResult(data = allChannel() ?: mutableListOf())
    }

    fun requestAllChannels() {
        request({ getAllChannels() }, {
            allChannelData.value = ResultState(it, isSuccess = true)
        }, {
            print(it)
            allChannelData.value = ResultState(isSuccess = false, errorMsg = it.errorMsg)
        })
    }

    fun requestRemoveChannel(item: IndicatorTitle) {
        val data = myChannelData.value?.data ?: mutableListOf()
        data.remove(item)
        myChannelData.value = ResultState(data, isSuccess = true)
    }

    fun requestDone() {
        cacheMyChannel(myChannelData.value?.data ?: mutableListOf())
    }

    fun requestAddChannel(item: IndicatorTitle) {
        val data = myChannelData.value?.data ?: mutableListOf()
        data.add(0, item)
        myChannelData.value = ResultState(data, isSuccess = true)
    }

}