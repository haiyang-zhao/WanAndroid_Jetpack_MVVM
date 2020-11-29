package com.zhy.jetpack.wanandroid_jetpack

import android.app.Application
import android.content.ContextWrapper
import android.os.Environment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.facebook.stetho.Stetho
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir
import com.tencent.mmkv.MMKV
import com.zhy.jetpack.mvvm.log.LogConfigurator
import com.zhy.jetpack.wanandroid_jetpack.widget.loadCallBack.EmptyCallback
import com.zhy.jetpack.wanandroid_jetpack.widget.loadCallBack.ErrorCallback
import com.zhy.jetpack.wanandroid_jetpack.widget.loadCallBack.LoadingCallback


lateinit var INSTANCE: Application

class App : Application(), ViewModelStoreOwner {

    //全局ViewModel
    private lateinit var mAppViewModelStore: ViewModelStore
    private var mFactory: ViewModelProvider.Factory? = null

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        mAppViewModelStore = ViewModelStore()
        initLog()
        MMKV.initialize(this.filesDir.absolutePath + "/mmkv")
        //界面加载管理 初始化
        LoadSir.beginBuilder()
            .addCallback(LoadingCallback())//加载
            .addCallback(ErrorCallback())//错误
            .addCallback(EmptyCallback())//空
            .setDefaultCallback(SuccessCallback::class.java)//设置默认加载状态页
            .commit()

        Stetho.initializeWithDefaults(this)
    }

    private fun initLog() {
        LogConfigurator.configure(
            Environment.getExternalStorageDirectory().absolutePath + "/log",
            assets.open(LogConfigurator.LOGBACK_FILE_NAME)
        )
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }


    fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, this.getAppFactory())
    }

    private fun getAppFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }
}

object AppContext : ContextWrapper(INSTANCE)