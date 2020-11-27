package com.zhy.jetpack.mvvm.base.activity

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.zhy.jetpack.mvvm.base.viewmodel.BaseViewModel

abstract class BaseVDBActivity<VM : BaseViewModel, VDB : ViewDataBinding> : BaseVMActivity<VM>() {

    lateinit var mDataBinding: VDB

    override fun initContentView() {
        mDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mDataBinding.lifecycleOwner = this
    }
}