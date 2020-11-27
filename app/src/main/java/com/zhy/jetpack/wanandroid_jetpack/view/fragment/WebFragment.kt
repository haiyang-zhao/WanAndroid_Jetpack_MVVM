package com.zhy.jetpack.wanandroid_jetpack.view.fragment

import android.view.Menu
import android.view.MenuInflater
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.zhy.jetpack.mvvm.ext.nav
import com.zhy.jetpack.wanandroid_jetpack.R
import com.zhy.jetpack.wanandroid_jetpack.base.BaseFragment
import com.zhy.jetpack.wanandroid_jetpack.data.model.Article
import com.zhy.jetpack.wanandroid_jetpack.data.model.Banner
import com.zhy.jetpack.wanandroid_jetpack.databinding.FragmentWebBinding
import com.zhy.jetpack.wanandroid_jetpack.ext.hideSoftKeyboard
import com.zhy.jetpack.wanandroid_jetpack.ext.initClose
import com.zhy.jetpack.wanandroid_jetpack.utils.KEY_ARTICLE
import com.zhy.jetpack.wanandroid_jetpack.utils.KEY_BANNER
import com.zhy.jetpack.wanandroid_jetpack.viewmodel.state.WebViewModel
import kotlinx.android.synthetic.main.fragment_web.*
import kotlinx.android.synthetic.main.include_toolbar.*

class WebFragment : BaseFragment<WebViewModel, FragmentWebBinding>() {

    lateinit var mAgentWeb: AgentWeb

    override fun layoutId(): Int {
        return R.layout.fragment_web
    }

    override fun initView() {
        initArgs()
        setHasOptionsMenu(true)
        initToolbar()
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(webContent, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(mViewModel.url)
    }

    private fun initArgs() {
        arguments?.apply {

            getParcelable<Article>(KEY_ARTICLE)
                ?.apply {
                    mViewModel.title = title
                    mViewModel.url = link
                }
            getParcelable<Banner>(KEY_BANNER)
                ?.apply {
                    mViewModel.title = title
                    mViewModel.url = url
                }
        }
    }

    private fun initToolbar() {
        toolbar.run {
            //设置menu 关键代码
            mActivity.setSupportActionBar(this)
            initClose(mViewModel.title) {
                hideSoftKeyboard(activity)
                mAgentWeb.apply {
                    if (webCreator.webView.canGoBack()) {
                        webCreator.webView.goBack()
                    } else {
                        nav().navigateUp()
                    }
                }
            }
        }
    }

    override fun initData() {
    }

    override fun addObserver() {
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_web, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        //如果收藏了，右上角的图标相对应改变
        context?.let {
//            if (mViewModel.collect) {
//                menu.findItem(R.id.web_collect).icon =
//                    ContextCompat.getDrawable(it, R.drawable.ic_collected)
//            } else {
//                menu.findItem(R.id.web_collect).icon =
//                    ContextCompat.getDrawable(it, R.drawable.ic_collect)
//            }
        }

        return super.onPrepareOptionsMenu(menu)
    }


    override fun onPause() {
        mAgentWeb.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb.webLifeCycle?.onDestroy()
        mActivity.setSupportActionBar(null)
        super.onDestroy()
    }

}