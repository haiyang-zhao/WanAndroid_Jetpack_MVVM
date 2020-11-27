package com.zhy.jetpack.wanandroid_jetpack.base

import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import com.zhy.jetpack.mvvm.base.fragment.BaseVDBFragment
import com.zhy.jetpack.mvvm.base.viewmodel.BaseViewModel
import com.zhy.jetpack.wanandroid_jetpack.viewmodel.EventViewModel
import com.zhy.jetpack.wanandroid_jetpack.viewmodel.SharedViewModel

abstract class BaseFragment<VM : BaseViewModel, VDB : ViewDataBinding> :
    BaseVDBFragment<VM, VDB>() {


    val sharedViewModel by lazy { mActivity.viewModels<SharedViewModel>() }

    val eventViewModel by lazy { mActivity.viewModels<EventViewModel>() }


    override fun lazyLoadData() {

    }

    override fun showLoading(message: String) {

    }

    override fun dismissLoading() {

    }

}