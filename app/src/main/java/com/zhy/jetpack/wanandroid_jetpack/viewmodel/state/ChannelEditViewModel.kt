package com.zhy.jetpack.wanandroid_jetpack.viewmodel.state

import com.zhy.jetpack.mvvm.base.viewmodel.BaseViewModel
import com.zhy.jetpack.mvvm.livedata.IntLiveData

const val OPT_EDIT = 0
const val OPT_SAVE = 1

class ChannelEditViewModel : BaseViewModel() {


    var operation = IntLiveData()
}