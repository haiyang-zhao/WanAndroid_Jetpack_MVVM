package com.zhy.jetpack.wanandroid_jetpack.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.zhy.jetpack.mvvm.base.viewmodel.BaseViewModel
import com.zhy.jetpack.wanandroid_jetpack.ext.request
import com.zhy.jetpack.wanandroid_jetpack.http.apiClient
import com.zhy.jetpack.wanandroid_jetpack.http.state.ResultState
import com.zhy.jetpack.wanandroid_jetpack.view.adapter.IndicatorTitle

class RequestProjectCategoryViewModel : BaseViewModel() {

    var categoryData = MutableLiveData<ResultState<IndicatorTitle>>()

    fun requestChapters() {
        request({ apiClient.projectCategories() }, {
            val data = it.map { item -> IndicatorTitle(item.id, item.name) }.toMutableList()
            categoryData.value = ResultState(data = data, isSuccess = true)
        }, {
            categoryData.value = ResultState(isSuccess = true, errorMsg = it.errorMsg)
        })
    }
}