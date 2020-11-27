package com.zhy.jetpack.wanandroid_jetpack.view.acitivity

import android.os.Bundle
import com.zhy.jetpack.mvvm.anotations.ContentView
import com.zhy.jetpack.mvvm.base.viewmodel.BaseViewModel
import com.zhy.jetpack.wanandroid_jetpack.R
import com.zhy.jetpack.wanandroid_jetpack.base.BaseActivity
import com.zhy.jetpack.wanandroid_jetpack.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.fragment_main.*


@ContentView(R.layout.activity_main)
class MainActivity : BaseActivity<BaseViewModel, ActivityMainBinding>() {

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun addObserver() {
    }

}