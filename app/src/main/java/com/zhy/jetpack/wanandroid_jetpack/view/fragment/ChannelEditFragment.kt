package com.zhy.jetpack.wanandroid_jetpack.view.fragment

import android.content.Context
import android.graphics.Color
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.zhy.jetpack.mvvm.ext.nav
import com.zhy.jetpack.wanandroid_jetpack.AppContext
import com.zhy.jetpack.wanandroid_jetpack.R
import com.zhy.jetpack.wanandroid_jetpack.base.BaseFragment
import com.zhy.jetpack.wanandroid_jetpack.data.model.Channel
import com.zhy.jetpack.wanandroid_jetpack.databinding.FragmentChannelEditBinding
import com.zhy.jetpack.wanandroid_jetpack.ext.hideSoftKeyboard
import com.zhy.jetpack.wanandroid_jetpack.ext.initClose
import com.zhy.jetpack.wanandroid_jetpack.view.adapter.*
import com.zhy.jetpack.wanandroid_jetpack.viewmodel.request.RequestChannelEditViewModel
import com.zhy.jetpack.wanandroid_jetpack.viewmodel.state.ChannelEditViewModel
import com.zhy.jetpack.wanandroid_jetpack.viewmodel.state.OPT_EDIT
import com.zhy.jetpack.wanandroid_jetpack.viewmodel.state.OPT_SAVE
import kotlinx.android.synthetic.main.fragment_channel_edit.*
import kotlinx.android.synthetic.main.include_toolbar.*
import net.lucode.hackware.magicindicator.FragmentContainerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView


class ChannelEditFragment : BaseFragment<ChannelEditViewModel, FragmentChannelEditBinding>() {

    private val requestViewModel by viewModels<RequestChannelEditViewModel>()
    private val myChannels = mutableListOf<IndicatorTitle>()
    private var allParentChannels = mutableListOf<IndicatorTitle>()
    private var allChildrenChannels = mutableListOf<Channel>()
    private val mFragmentContainerHelper = FragmentContainerHelper()

    private var curPosition = 0
    override fun addObserver() {
        requestViewModel.apply {
            myChannelData.observe(viewLifecycleOwner, {
                if (it.isSuccess) {
                    myChannels.clear()
                    myChannels.addAll(it.data)
                    rv_myChannel.adapter?.notifyDataSetChanged()
                }
            })
            allChannelData.observe(viewLifecycleOwner, {
                if (it.isSuccess) {
                    allParentChannels.clear()
                    allParentChannels.addAll(it.data.map { r -> IndicatorTitle(r.id, r.name) }
                        .toMutableList())
                    magic_indicator.navigator.notifyDataSetChanged()
                    allChildrenChannels.clear()
                    allChildrenChannels.addAll(it.data)
                    rv_all_channel.adapter?.notifyDataSetChanged()
                }
            })
        }

        mViewModel.apply {
            operation.observe(viewLifecycleOwner, {
                when (it) {
                    OPT_EDIT -> {
                        (rv_myChannel.adapter as ChannelTagAdapter).opt = CHANNEL_OPT_NONE
                    }
                    OPT_SAVE -> {
                        (rv_myChannel.adapter as ChannelTagAdapter).opt = CHANNEL_OPT_REMOVE
                    }
                }

                rv_myChannel.adapter?.notifyDataSetChanged()
            })
        }
    }

    override fun initView() {
        mBinding.vm = mViewModel
        mBinding.click = ClickProxy()
        initToolbar()
        ns_parent.setTopView(cl_top)
        ns_parent.setTabLayout(magic_indicator)
        ns_parent.setViewPager(rv_all_channel)
        rv_myChannel.layoutManager = FlexboxLayoutManager(context).apply {
            //方向 主轴为水平方向，起点在左端
            flexDirection = FlexDirection.ROW
            //左对齐
            justifyContent = JustifyContent.FLEX_START
        }
        rv_myChannel.adapter = ChannelTagAdapter(myChannels).apply {
            optListener = object : ChannelTagAdapter.TagOperationListener {
                override fun doOperation(opt: Int, tag: IndicatorTitle) {
                    if (opt == CHANNEL_OPT_REMOVE) {
                        requestViewModel.requestRemoveChannel(tag)
                    }
                }

            }
        }
        setUpAllChannel()

    }

    private fun setUpAllChannel() {
        rv_all_channel.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = ChannelAdapter(allChildrenChannels).apply {
                optListener = object : ChannelTagAdapter.TagOperationListener {
                    override fun doOperation(opt: Int, tag: IndicatorTitle) {
                        if (opt == CHANNEL_OPT_ADD) {
                            requestViewModel.requestAddChannel(tag)
                        }
                    }

                }
            }
        }
        magic_indicator.apply {

            val navigator = CommonNavigator(AppContext)
                .apply {
                    adapter = object : CommonNavigatorAdapter() {
                        override fun getCount() = allParentChannels.size

                        override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                            return ClipPagerTitleView(context)
                                .apply {
                                    text = allParentChannels[index].title
                                    textColor = Color.BLACK
                                    clipColor = Color.BLACK
                                    setOnClickListener {
                                        (rv_all_channel.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                                            index,
                                            0
                                        )
                                        curPosition = index
                                        mFragmentContainerHelper.handlePageSelected(index)
                                    }
                                }
                        }

                        override fun getIndicator(context: Context): IPagerIndicator {
                            return LinePagerIndicator(context).apply {
                                setColors(Color.RED)
                                mode = LinePagerIndicator.MODE_WRAP_CONTENT
                            }
                        }

                    }
                }

            setNavigator(navigator)
            mFragmentContainerHelper.attachMagicIndicator(this)
        }
        rv_all_channel.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lm = recyclerView.layoutManager as LinearLayoutManager
                val position = lm.findFirstVisibleItemPosition()
                if (curPosition != position) {
                    curPosition = position
                    mFragmentContainerHelper.handlePageSelected(curPosition)
                }
            }

        })

    }


    private fun initToolbar() {
        toolbar.run {
            //设置menu 关键代码
            mActivity.setSupportActionBar(this)
            initClose(backImg = R.drawable.ic_close) {
                hideSoftKeyboard(activity)
                nav().navigateUp()
            }
        }
    }

    override fun initData() {
        requestViewModel.requestMyChannels()
        requestViewModel.requestAllChannels()
    }

    override fun layoutId(): Int {
        return R.layout.fragment_channel_edit
    }

    inner class ClickProxy {
        fun opt() {
            if (mViewModel.operation.value == OPT_EDIT) {
                mViewModel.operation.value = OPT_SAVE
            } else if (mViewModel.operation.value == OPT_SAVE) {
                requestViewModel.requestDone()
                mViewModel.operation.value = OPT_EDIT

            }


        }
    }
}