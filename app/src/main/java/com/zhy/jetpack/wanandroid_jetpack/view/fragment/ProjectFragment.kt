package com.zhy.jetpack.wanandroid_jetpack.view.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.zhy.jetpack.wanandroid_jetpack.R
import com.zhy.jetpack.wanandroid_jetpack.base.BaseFragment
import com.zhy.jetpack.wanandroid_jetpack.databinding.FragmentProjectBinding
import com.zhy.jetpack.wanandroid_jetpack.ext.bindAdapter
import com.zhy.jetpack.wanandroid_jetpack.ext.bindViewPager2
import com.zhy.jetpack.wanandroid_jetpack.view.adapter.IndicatorTitle
import com.zhy.jetpack.wanandroid_jetpack.viewmodel.request.RequestProjectCategoryViewModel
import com.zhy.jetpack.wanandroid_jetpack.viewmodel.state.ProjectViewModel
import kotlinx.android.synthetic.main.include_tab_viewpager.*

class ProjectFragment : BaseFragment<ProjectViewModel, FragmentProjectBinding>() {


    private val requestViewModel by viewModels<RequestProjectCategoryViewModel>()
    private var categories = mutableListOf<IndicatorTitle>()

    private var fragments = mutableListOf<Fragment>()


    override fun initView() {
        magic_indicator.bindViewPager2(view_pager, categories)
        view_pager.bindAdapter(fragments, this)
    }

    override fun initData() {
    }

    override fun layoutId(): Int {
        return R.layout.fragment_tab_viewpager
    }

    override fun addObserver() {

        requestViewModel.apply {
            categoryData.observe(viewLifecycleOwner, { result ->
                if (result.isSuccess) {
                    categories.addAll(result.data)
                    magic_indicator.navigator.notifyDataSetChanged()
                    categories.forEach {
                        fragments.add(ProjectArticleFragment.newInstance(it.id))
                    }
                    view_pager.adapter?.notifyDataSetChanged()
                }
            })
        }
    }

    override fun lazyLoadData() {
        requestViewModel.requestChapters()
    }
}