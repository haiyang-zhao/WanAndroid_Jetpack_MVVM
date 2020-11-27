package com.zhy.jetpack.wanandroid_jetpack.view.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.kingja.loadsir.core.LoadService
import com.zhy.jetpack.mvvm.ext.nav
import com.zhy.jetpack.mvvm.ext.navigateAction
import com.zhy.jetpack.wanandroid_jetpack.R
import com.zhy.jetpack.wanandroid_jetpack.base.BaseFragment
import com.zhy.jetpack.wanandroid_jetpack.databinding.IncludeListBinding
import com.zhy.jetpack.wanandroid_jetpack.ext.*
import com.zhy.jetpack.wanandroid_jetpack.utils.KEY_ARTICLE
import com.zhy.jetpack.wanandroid_jetpack.utils.KEY_WX_CHAPTER_ID
import com.zhy.jetpack.wanandroid_jetpack.view.adapter.ArticleAdapter
import com.zhy.jetpack.wanandroid_jetpack.viewmodel.request.RequestWXArticleViewModel
import com.zhy.jetpack.wanandroid_jetpack.viewmodel.state.WXArticleViewModel
import com.zhy.jetpack.wanandroid_jetpack.widget.recyclerview.SpaceItemDecoration
import kotlinx.android.synthetic.main.include_list.*
import kotlinx.android.synthetic.main.include_recyclerview.*

class ArticleListFragment : BaseFragment<WXArticleViewModel, IncludeListBinding>() {

    private val requestViewModel by viewModels<RequestWXArticleViewModel>()
    private val articleAdapter by lazy { ArticleAdapter(mutableListOf()) }
    private lateinit var loadSir: LoadService<Any>

    override fun initView() {
        initLoadSir()
    }

    private fun initLoadSir() {
        loadSir = swipeRefresh.registerLoadSir {
            loadSir.showLoading()
            requestViewModel.requestArticles()
        }

        swipeRefresh.init {
            requestViewModel.requestArticles()
        }
        initRecyclerView()
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
                requestViewModel.requestArticles(false)
            }
            .initGoTopBtn(floatbtn)

        articleAdapter.setOnItemClickListener { _, _, position ->
            val article = articleAdapter.data[position - recyclerview.headerCount]
            nav().navigateAction(R.id.action_to_webFragment, Bundle().apply {
                putParcelable(KEY_ARTICLE, article)
            })
        }

    }

    override fun initData() {
        arguments?.apply {
            requestViewModel.cId = getInt(KEY_WX_CHAPTER_ID)
        }
    }

    override fun layoutId(): Int {
        return R.layout.include_list
    }

    override fun lazyLoadData() {
        loadSir.showLoading()
        requestViewModel.requestArticles()
    }

    companion object {
        fun newInstance(id: Int): ArticleListFragment {
            return ArticleListFragment()
                .apply {
                    arguments = Bundle().apply {
                        putInt(KEY_WX_CHAPTER_ID, id)
                    }
                }
        }
    }

    override fun addObserver() {
        requestViewModel
            .apply {
                articleData.observe(viewLifecycleOwner, {
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
}