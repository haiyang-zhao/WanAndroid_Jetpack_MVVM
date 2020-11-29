package com.zhy.jetpack.wanandroid_jetpack.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.zhy.jetpack.mvvm.base.viewmodel.BaseViewModel
import com.zhy.jetpack.wanandroid_jetpack.data.model.User
import com.zhy.jetpack.wanandroid_jetpack.ext.request
import com.zhy.jetpack.wanandroid_jetpack.http.apiClient
import com.zhy.jetpack.wanandroid_jetpack.http.state.ResultState

class RequestLoginViewModel : BaseViewModel() {

    var loginState = MutableLiveData<ResultState<User>>()

    fun requestLogin(username: String, password: String) {
        request({ apiClient.login(username, password) }, {
            loginState.value = ResultState(isSuccess = true, data = mutableListOf(it))
        }, {
            loginState.value = ResultState(isSuccess = false, errorMsg = it.errorMsg)
        })
    }
}