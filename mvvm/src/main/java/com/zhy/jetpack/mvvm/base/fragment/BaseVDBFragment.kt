package com.zhy.jetpack.mvvm.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.zhy.jetpack.mvvm.base.viewmodel.BaseViewModel

abstract class BaseVDBFragment<VM : BaseViewModel, VDB : ViewDataBinding>
    : BaseVMFragment<VM>() {

    lateinit var mBinding: VDB
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mBinding.lifecycleOwner = this
        return mBinding.root
    }
}