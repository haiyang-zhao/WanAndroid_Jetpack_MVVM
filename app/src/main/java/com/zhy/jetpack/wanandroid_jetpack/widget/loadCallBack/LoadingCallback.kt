package com.zhy.jetpack.wanandroid_jetpack.widget.loadCallBack

import android.content.Context
import android.view.View
import com.kingja.loadsir.callback.Callback
import com.zhy.jetpack.wanandroid_jetpack.R


class LoadingCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_loading
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }
}