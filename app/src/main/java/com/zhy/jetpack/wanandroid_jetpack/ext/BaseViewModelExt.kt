package com.zhy.jetpack.wanandroid_jetpack.ext

import androidx.lifecycle.viewModelScope
import com.zhy.jetpack.mvvm.base.viewmodel.BaseViewModel
import com.zhy.jetpack.wanandroid_jetpack.http.model.ApiException
import com.zhy.jetpack.wanandroid_jetpack.http.model.ApiResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


fun <T> BaseViewModel.request(
    block: suspend () -> ApiResult<T>,
    onSuccess: (T) -> Unit,
    onError: (ApiException) -> Unit,
    showLoading: Boolean = false,
    loadingMsg: String = "正在加载网络数据，请稍后..."
): Job {
    return viewModelScope.launch {
        runCatching {
            //显示loading
            if (showLoading) {
                loadingChange.showDialog.postValue(loadingMsg)
            }
            block()
        }.onSuccess {
            if (it.success()) {
                onSuccess(it.data)
            } else {
                onError(ApiException(it.errorCode, it.errorMsg))
            }

        }.onFailure {
            onError(ApiException("", it))
        }
    }
}