package com.zhy.jetpack.wanandroid_jetpack.widget.loadCallBack


import com.kingja.loadsir.callback.Callback
import com.zhy.jetpack.wanandroid_jetpack.R


class EmptyCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_empty
    }

}