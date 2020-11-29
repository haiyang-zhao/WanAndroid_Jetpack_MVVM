package com.zhy.jetpack.wanandroid_jetpack.viewmodel.state

import com.zhy.jetpack.mvvm.base.viewmodel.BaseViewModel
import com.zhy.jetpack.mvvm.livedata.BooleanLiveData
import com.zhy.jetpack.mvvm.livedata.StringLiveData

class LoginViewModel : BaseViewModel() {

    var username = StringLiveData()
    var password = StringLiveData()

    var showPwd = BooleanLiveData()
}