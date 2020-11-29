package com.zhy.jetpack.wanandroid_jetpack.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.zhy.jetpack.mvvm.base.viewmodel.BaseViewModel
import com.zhy.jetpack.wanandroid_jetpack.data.model.UserCoin
import com.zhy.jetpack.wanandroid_jetpack.ext.request
import com.zhy.jetpack.wanandroid_jetpack.http.apiClient
import com.zhy.jetpack.wanandroid_jetpack.http.state.ResultState

class RequestViewModel : BaseViewModel() {

    var meData = MutableLiveData<ResultState<UserCoin>>()

    fun requestUserCoin() {
        request({ apiClient.userCoin() }, {
            meData.value = ResultState(isSuccess = true, data = mutableListOf(it))
        }, {
            meData.value = ResultState(isSuccess = false, errorMsg = it.errorMsg)
        })
    }
}