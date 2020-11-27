package com.zhy.jetpack.wanandroid_jetpack.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.kingja.loadsir.core.LoadService
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.indicator.enums.IndicatorStyle
import com.zhy.jetpack.mvvm.ext.nav
import com.zhy.jetpack.mvvm.ext.navigateAction
import com.zhy.jetpack.wanandroid_jetpack.R
import com.zhy.jetpack.wanandroid_jetpack.base.BaseFragment
import com.zhy.jetpack.wanandroid_jetpack.data.model.Banner
import com.zhy.jetpack.wanandroid_jetpack.databinding.FragmentIndexBinding
import com.zhy.jetpack.wanandroid_jetpack.ext.*
import com.zhy.jetpack.wanandroid_jetpack.utils.KEY_ARTICLE
import com.zhy.jetpack.wanandroid_jetpack.utils.KEY_BANNER
import com.zhy.jetpack.wanandroid_jetpack.view.adapter.ArticleAdapter
import com.zhy.jetpack.wanandroid_jetpack.view.adapter.BannerViewHolder
import com.zhy.jetpack.wanandroid_jetpack.view.adapter.HomeBannerAdapter
import com.zhy.jetpack.wanandroid_jetpack.viewmodel.request.RequestHomeViewModel
import com.zhy.jetpack.wanandroid_jetpack.viewmodel.state.HomeViewModel
import com.zhy.jetpack.wanandroid_jetpack.widget.recyclerview.SpaceItemDecoration
import kotlinx.android.synthetic.main.include_list.*
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.include_toolbar.*

class HomeFragment : BaseFragment<HomeViewModel, FragmentIndexBinding>() {

    private val requestHomeViewModel by viewModels<RequestHomeViewModel>()
    private val articleAdapter by lazy { ArticleAdapter(mutableListOf()) }

    private lateinit var loadSir: LoadService<Any>
    private lateinit var mBannerPager: BannerViewPager<Banner, BannerViewHolder>

    override fun addObserver() {
        requestHomeViewModel
            .apply {
                bannerData.observe(viewLifecycleOwner, {
                    mBannerPager.refreshData(it)
                })
                homeData.observe(viewLifecycleOwner, {
                    pagerStateChanged(
                        pagerState = it,
                        baseQuickAdapter = articleAdapter,
                        loadService = loadSir,
                        recyclerView = recyclerview,
                        swipeRefreshLayout = swipeRefresh
                    )
                })
            }
    }

    override fun initView() {
        initLoadSir()
        initToolBar()
        initRecyclerView()
        initBanner()
        swipeRefresh.init {
            requestHomeViewModel.requestData(true)
        }
    }

    private fun initLoadSir() {
        loadSir = swipeRefresh.registerLoadSir {
            loadSir.showLoading()
            requestHomeViewModel.requestBanner()
        }
    }

    private fun initRecyclerView() {
        recyclerview
            .init(LinearLayoutManager(context), articleAdapter)
            .apply {
                addItemDecoration(
                    SpaceItemDecoration(
                        0,
                        ConvertUtils.dp2px(8f), false
                    )
                )
            }
            .initFooter {
                requestHomeViewModel.requestData(false)
            }
            .initGoTopBtn(floatbtn)

        articleAdapter.setOnItemClickListener { _, _, position ->
            val article = articleAdapter.data[position - recyclerview.headerCount]
            nav().navigateAction(R.id.action_to_webFragment, Bundle().apply {
                putParcelable(KEY_ARTICLE, article)
            })
        }

    }

    private fun initToolBar() {
        toolbar.run {
            init(getString(R.string.bottom_name1))
            inflateMenu(R.menu.menu_toolbar_home)
        }
    }

    private fun initBanner() {
        val headerView = LayoutInflater.from(context).inflate(R.layout.include_banner, null)
        mBannerPager = headerView.findViewById(R.id.banner_view)
        mBannerPager.apply {
            adapter = HomeBannerAdapter()
            setAutoPlay(true)
            setLifecycleRegistry(lifecycle)
            setIndicatorStyle(IndicatorStyle.ROUND_RECT)
            setOnPageClickListener {
                val banner = data[it]
                nav().navigateAction(R.id.action_to_webFragment, Bundle().apply {
                    putParcelable(KEY_BANNER, banner)
                })
            }
        }.create()
        recyclerview.addHeaderView(headerView)
    }

    override fun initData() {

    }

    override fun lazyLoadData() {
        loadSir.showLoading()
        requestHomeViewModel.requestBanner()
        requestHomeViewModel.requestData(true)
    }

    override fun layoutId(): Int {
        return R.layout.fragment_index
    }
}