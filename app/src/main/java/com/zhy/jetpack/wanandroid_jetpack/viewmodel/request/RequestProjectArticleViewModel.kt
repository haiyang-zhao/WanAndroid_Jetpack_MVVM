package com.zhy.jetpack.wanandroid_jetpack.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.zhy.jetpack.mvvm.base.viewmodel.BaseViewModel
import com.zhy.jetpack.wanandroid_jetpack.data.model.Article
import com.zhy.jetpack.wanandroid_jetpack.ext.request
import com.zhy.jetpack.wanandroid_jetpack.http.apiClient
import com.zhy.jetpack.wanandroid_jetpack.http.state.PagerState

class RequestProjectArticleViewModel : BaseViewModel() {

    var pageIndex = 0
    var cId = 0
    var articleData = MutableLiveData<PagerState<Article>>()


    fun requestArticles(firstLoad: Boolean = true) {

        if (firstLoad) {
            pageIndex = 0
        }

        request({ apiClient.projectArticles(pageIndex, cId) }, {
            pageIndex++
            articleData.value = PagerState(
                data = it.datas, isSuccess = true,
                hasMore = it.hasMore(), firstLoad = firstLoad
            )
        },
            {
                articleData.value = PagerState(
                    isSuccess = false, errorMsg = it.errorMsg, firstLoad = firstLoad
                )
            })

    }
}