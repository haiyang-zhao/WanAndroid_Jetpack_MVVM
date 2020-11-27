package com.zhy.jetpack.mvvm.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.zhy.jetpack.mvvm.anotations.ContentView
import com.zhy.jetpack.mvvm.base.viewmodel.BaseViewModel
import com.zhy.jetpack.mvvm.ext.getClazz

abstract class BaseVMActivity<VM : BaseViewModel> : AppCompatActivity() {

    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView()
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        mViewModel = createViewModel()
        initView(savedInstanceState)
        addLoadingObserver()
        addObserver()

    }

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun addObserver()

    abstract fun initContentView()


    private fun addLoadingObserver() {
        addLoadingObserver(mViewModel)
    }

    protected fun addLoadingObserver(vararg mViewModels: BaseViewModel) {
        mViewModels.forEach {
            it.loadingChange.showDialog.observe(this, {
                showLoading("正在加载中....")
            })

            it.loadingChange.dismissDialog.observe(this, {
                dismissLoading()
            })
        }

    }

    protected fun getLayoutId(): Int {
        val annotations = this::class.annotations
        if (annotations.isNotEmpty()) {
            if (annotations[0] is ContentView) {
                return (annotations[0] as ContentView).resId
            }
        }
        return layoutId()
    }

    abstract fun layoutId(): Int


    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getClazz(this))
    }


    abstract fun showLoading(message: String = "请求网络中...")

    abstract fun dismissLoading()
}