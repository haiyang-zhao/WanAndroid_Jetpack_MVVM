package com.zhy.jetpack.wanandroid_jetpack.viewmodel

import com.zhy.jetpack.mvvm.base.viewmodel.BaseViewModel
import com.zhy.jetpack.mvvm.livedata.UnPeekLiveData
import com.zhy.jetpack.wanandroid_jetpack.data.model.User

/**
 * 全局数据共享类
 */
class SharedViewModel : BaseViewModel() {

    var currentUser = UnPeekLiveData<User>()
}