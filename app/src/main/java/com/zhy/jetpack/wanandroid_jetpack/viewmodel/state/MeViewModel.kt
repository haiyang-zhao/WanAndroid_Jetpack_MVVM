package com.zhy.jetpack.wanandroid_jetpack.viewmodel.state

import com.zhy.jetpack.mvvm.base.viewmodel.BaseViewModel
import com.zhy.jetpack.mvvm.databind.BooleanObservableField
import com.zhy.jetpack.mvvm.databind.IntObservableField
import com.zhy.jetpack.mvvm.databind.StringObservableField

class MeViewModel : BaseViewModel() {

    var isLogin = BooleanObservableField(false)

    var name = StringObservableField("请先登录~")

    var coin = IntObservableField(0)

    var info = StringObservableField("id：--　排名：-")

    var imageUrl =
        StringObservableField("https://img2.woyaogexing.com/2019/08/28/667ebc1b9d7c4783bad801a2a3be199d!600x600.jpeg")


}