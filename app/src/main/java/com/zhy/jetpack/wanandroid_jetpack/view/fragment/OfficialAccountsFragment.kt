package com.zhy.jetpack.wanandroid_jetpack.view.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.zhy.jetpack.wanandroid_jetpack.R
import com.zhy.jetpack.wanandroid_jetpack.base.BaseFragment
import com.zhy.jetpack.wanandroid_jetpack.databinding.FragmentTabViewpagerBinding
import com.zhy.jetpack.wanandroid_jetpack.ext.bindAdapter
import com.zhy.jetpack.wanandroid_jetpack.ext.bindViewPager2
import com.zhy.jetpack.wanandroid_jetpack.view.adapter.IndicatorTitle
import com.zhy.jetpack.wanandroid_jetpack.viewmodel.request.RequestOfficialAccountsViewModel
import com.zhy.jetpack.wanandroid_jetpack.viewmodel.state.OfficialAccountsViewModel
import kotlinx.android.synthetic.main.include_tab_viewpager.*

class OfficialAccountsFragment :
    BaseFragment<OfficialAccountsViewModel, FragmentTabViewpagerBinding>() {

    private val requestViewModel by viewModels<RequestOfficialAccountsViewModel>()
    private var chapters = mutableListOf<IndicatorTitle>()
    private var fragments = mutableListOf<Fragment>()


    override fun initView() {
        magic_indicator.bindViewPager2(view_pager, chapters)
        view_pager.bindAdapter(fragments, this)

    }

    override fun initData() {

    }

    override fun layoutId(): Int {
        return R.layout.fragment_tab_viewpager
    }

    override fun lazyLoadData() {
        requestViewModel.requestChapters()
    }


    override fun addObserver() {
        requestViewModel.apply {
            chaptersData.observe(viewLifecycleOwner, { result ->
                if (result.isSuccess) {
                    chapters.addAll(result.data)
                    magic_indicator.navigator.notifyDataSetChanged()
                    chapters.forEach {
                        fragments.add(WXArticleFragment.newInstance(it.id))
                    }
                    view_pager.adapter?.notifyDataSetChanged()
                }
            })
        }
    }

}