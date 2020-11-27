package com.zhy.jetpack.mvvm.base.viewmodel

import androidx.lifecycle.ViewModel
import com.zhy.jetpack.mvvm.livedata.EventLiveData

open class BaseViewModel : ViewModel() {

    val loadingChange by lazy {
        UILoadingChange()
    }

    /**
     * 加载loading
     */
    inner class UILoadingChange {
        //显示加载框
        val showDialog by lazy { EventLiveData<String>() }

        //隐藏
        val dismissDialog by lazy { EventLiveData<Boolean>() }
    }
}