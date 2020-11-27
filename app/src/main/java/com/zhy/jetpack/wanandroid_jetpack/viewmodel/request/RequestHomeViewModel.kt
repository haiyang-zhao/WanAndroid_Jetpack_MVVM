package com.zhy.jetpack.wanandroid_jetpack.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.zhy.jetpack.mvvm.base.viewmodel.BaseViewModel
import com.zhy.jetpack.wanandroid_jetpack.data.model.Article
import com.zhy.jetpack.wanandroid_jetpack.data.model.Banner
import com.zhy.jetpack.wanandroid_jetpack.data.model.Pagination
import com.zhy.jetpack.wanandroid_jetpack.ext.request
import com.zhy.jetpack.wanandroid_jetpack.http.apiClient
import com.zhy.jetpack.wanandroid_jetpack.http.model.ApiResult
import com.zhy.jetpack.wanandroid_jetpack.http.state.PagerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class RequestHomeViewModel : BaseViewModel() {

    var bannerData = MutableLiveData<List<Banner>>()
    var homeData = MutableLiveData<PagerState<Article>>()
    var pageIndex = 0

    fun requestBanner() {
        request({ apiClient.banner() }, {
            bannerData.value = it
        }, {

        })
    }


    fun requestData(firstLoad: Boolean) {
        if (firstLoad) {
            pageIndex = 0;
        }
        request({ articles(pageIndex) },
            {
                pageIndex++
                homeData.value = PagerState(
                    data = it.datas, isSuccess = true,
                    hasMore = it.hasMore(), firstLoad = firstLoad
                )
            },
            {
                homeData.value = PagerState(
                    isSuccess = false, errorMsg = it.errorMsg, firstLoad = firstLoad
                )
            })

    }

    private suspend fun articles(page: Int): ApiResult<Pagination<Article>> {
        //withContext() 函数将协程的执行操作移至其他线程
        return withContext(Dispatchers.IO) {
            val allArticles = async { apiClient.articleList(page) }

            if (page == 0) {
                val topArticles = async { apiClient.topArticles() }
                //合并数据
                allArticles.await().data.datas.addAll(0, topArticles.await().data)
            }

            allArticles.await()
        }

    }
}