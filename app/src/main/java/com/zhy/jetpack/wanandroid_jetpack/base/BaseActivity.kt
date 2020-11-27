package com.zhy.jetpack.wanandroid_jetpack.base

import android.annotation.SuppressLint
import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import com.zhy.jetpack.mvvm.base.activity.BaseVDBActivity
import com.zhy.jetpack.mvvm.base.viewmodel.BaseViewModel
import com.zhy.jetpack.wanandroid_jetpack.viewmodel.EventViewModel
import com.zhy.jetpack.wanandroid_jetpack.viewmodel.SharedViewModel

@SuppressLint("NonConstantResourceId")
abstract class BaseActivity<VM : BaseViewModel, VDB : ViewDataBinding> :
    BaseVDBActivity<VM, VDB>() {

    val sharedViewModel by viewModels<SharedViewModel>()

    val eventViewModel by viewModels<EventViewModel>()

    override fun layoutId() = 0


    override fun showLoading(message: String) {

    }

    override fun dismissLoading() {

    }
}