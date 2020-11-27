package com.zhy.jetpack.mvvm.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.zhy.jetpack.mvvm.anotations.ContentView
import com.zhy.jetpack.mvvm.base.viewmodel.BaseViewModel
import com.zhy.jetpack.mvvm.ext.getClazz
import com.zhy.jetpack.mvvm.network.NetState
import com.zhy.jetpack.mvvm.network.NetworkStateManager

abstract class BaseVMFragment<VM : BaseViewModel> : Fragment() {

    lateinit var mViewModel: VM
    lateinit var mActivity: AppCompatActivity

    //是否为第一加载
    private var isFirst = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = createViewModel()
        initView()
        addObserver()
        addLoadingObserver()
        onVisible()
        initData()
    }


    private fun addLoadingObserver() {
        addLoadingObserver(mViewModel)
    }

    override fun onResume() {
        super.onResume()
        onVisible()
    }

    abstract fun addObserver()

    protected fun addLoadingObserver(vararg mViewModels: BaseViewModel) {
        mViewModels.forEach {
            it.loadingChange.showDialog.observe(viewLifecycleOwner, {
                showLoading("正在加载中....")
            })

            it.loadingChange.dismissDialog.observe(viewLifecycleOwner, {
                dismissLoading()
            })
        }

    }

    abstract fun initView()

    abstract fun initData()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = activity as AppCompatActivity
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

    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            //延迟加载0.12秒加载 避免fragment跳转动画和渲染ui同时进行，出现些微的小卡顿
            view?.postDelayed({
                lazyLoadData()
                //在Fragment中，只有懒加载过了才能开启网络变化监听
                NetworkStateManager.instance.mNetworkStateCallback.observeInFragment(this) {
                    //不是首次订阅时调用方法，防止数据第一次监听错误
                    if (!isFirst) {
                        onNetworkStateChanged(it)
                    }
                }
                isFirst = false
            }, 120)
        }
    }

    /**
     * 网络变化监听 子类重写
     */
    open fun onNetworkStateChanged(netState: NetState) {}
    abstract fun lazyLoadData()

    abstract fun layoutId(): Int


    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getClazz(this))
    }


    abstract fun showLoading(message: String = "请求网络中...")

    abstract fun dismissLoading()
}