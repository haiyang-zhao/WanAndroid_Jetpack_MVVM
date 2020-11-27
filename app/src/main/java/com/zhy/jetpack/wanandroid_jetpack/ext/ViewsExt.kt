package com.zhy.jetpack.wanandroid_jetpack.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import com.zhy.jetpack.wanandroid_jetpack.AppContext
import com.zhy.jetpack.wanandroid_jetpack.R
import com.zhy.jetpack.wanandroid_jetpack.http.state.PagerState
import com.zhy.jetpack.wanandroid_jetpack.utils.SettingUtil
import com.zhy.jetpack.wanandroid_jetpack.view.adapter.IndicatorTitle
import com.zhy.jetpack.wanandroid_jetpack.widget.loadCallBack.EmptyCallback
import com.zhy.jetpack.wanandroid_jetpack.widget.loadCallBack.ErrorCallback
import com.zhy.jetpack.wanandroid_jetpack.widget.loadCallBack.LoadingCallback
import com.zhy.jetpack.wanandroid_jetpack.widget.recyclerview.DefineLoadMoreView
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView


//绑定SwipeRecyclerView
fun SwipeRecyclerView.init(
    layoutManger: RecyclerView.LayoutManager,
    bindAdapter: RecyclerView.Adapter<*>,
    isScroll: Boolean = true
): SwipeRecyclerView {
    layoutManager = layoutManger
    setHasFixedSize(true)
    adapter = bindAdapter
    isNestedScrollingEnabled = isScroll
    return this
}


//初始化Toolbar
fun Toolbar.init(titleStr: String = ""): Toolbar {
    setBackgroundColor(SettingUtil.getColor(AppContext))
    title = titleStr
    return this
}

//初始化有返回键的toolbar
fun Toolbar.initClose(
    titleName: String = "",
    backImg: Int = R.drawable.ic_back,
    onBack: (toolbar: Toolbar) -> Unit
): Toolbar {
    setBackgroundColor(SettingUtil.getColor(AppContext))
    title = titleName.toHtml()
    setNavigationIcon(backImg)
    setNavigationOnClickListener { onBack.invoke(this) }
    return this
}

//注册LoadSir
fun LoadService<*>.setErrorText(message: String) {
    if (message.isNotEmpty()) {
        this.setCallBack(ErrorCallback::class.java) { _, view ->
            view.findViewById<TextView>(R.id.error_text).text = message
        }
    }
}

//设置错误布局
fun LoadService<Any>.showError(message: String = "") {
    this.setErrorText(message)
    this.showCallback(ErrorCallback::class.java)
}

//设置空布局
fun LoadService<Any>.showEmpty() {
    this.showCallback(EmptyCallback::class.java)
}

//设置加载中
fun LoadService<Any>.showLoading() {
    this.showCallback(LoadingCallback::class.java)
}

fun View.registerLoadSir(callback: () -> Unit): LoadService<Any> {
    val loadSir = LoadSir.getDefault().register(this) {
        //点击重试时触发的操作
        callback.invoke()
    }
    loadSir.showSuccess()
    SettingUtil.setLoadingColor(SettingUtil.getColor(AppContext), loadSir)
    return loadSir
}


//加载分页数据
fun <T> pagerStateChanged(
    pagerState: PagerState<T>,
    baseQuickAdapter: BaseQuickAdapter<T, *>,
    loadService: LoadService<Any>,
    recyclerView: SwipeRecyclerView,
    swipeRefreshLayout: SwipeRefreshLayout
) {
    swipeRefreshLayout.isRefreshing = false
    recyclerView.loadMoreFinish(pagerState.isEmpty(), pagerState.hasMore)

    if (pagerState.isSuccess) {
        //成功
        when {
            //没有数据 显示空布局界面
            pagerState.isEmpty() -> {
                loadService.showEmpty()
            }
            //是第一页
            pagerState.firstLoad -> {
                baseQuickAdapter.setList(pagerState.data)
                loadService.showSuccess()
            }
            //不是第一页
            else -> {
                baseQuickAdapter.addData(pagerState.data)
                loadService.showSuccess()
            }
        }
    } else {
        //失败
        if (pagerState.firstLoad) {
            //如果是第一页，则显示错误界面，并提示错误信息
            loadService.showError(pagerState.errorMsg)
        } else {
            recyclerView.loadMoreError(0, pagerState.errorMsg)
        }
    }
}


//初始化 SwipeRefreshLayout
fun SwipeRefreshLayout.init(onRefreshListener: () -> Unit) {
    this.run {
        setOnRefreshListener {
            onRefreshListener.invoke()
        }
        //设置主题颜色
        setColorSchemeColors(SettingUtil.getColor(AppContext))
    }
}


//加载更多
fun SwipeRecyclerView.initFooter(loadMoreListener: SwipeRecyclerView.LoadMoreListener): SwipeRecyclerView {
    val footerView = DefineLoadMoreView(AppContext)
    //给尾部设置颜色
    footerView.setLoadViewColor(SettingUtil.getOneColorStateList(AppContext))
    //设置尾部点击回调
    footerView.setmLoadMoreListener(SwipeRecyclerView.LoadMoreListener {
        footerView.onLoading()
        loadMoreListener.onLoadMore()
    })
    this.run {
        //添加加载更多尾部
        addFooterView(footerView)
        setLoadMoreView(footerView)
        //设置加载更多回调
        setLoadMoreListener(loadMoreListener)
    }
    return this
}


//设置适配器的列表动画
fun BaseQuickAdapter<*, *>.setAdapterAnimation(mode: Int) {
    //等于0，关闭列表动画 否则开启
    if (mode == 0) {
        this.animationEnable = false
    } else {
        this.animationEnable = true
        this.setAnimationWithDefault(BaseQuickAdapter.AnimationType.values()[mode - 1])
    }
}

fun String.toHtml(flag: Int = Html.FROM_HTML_MODE_LEGACY): Spanned {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(this, flag)
    } else {
        Html.fromHtml(this)
    }
}

//隐藏软键盘
fun hideSoftKeyboard(activity: Activity?) {
    activity?.let { act ->
        val view = act.currentFocus
        view?.let {
            val inputMethodManager =
                act.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}

//回到顶部按钮
fun RecyclerView.initGoTopBtn(floatbtn: FloatingActionButton) {
    //监听recyclerview滑动到顶部的时候，需要把向上返回顶部的按钮隐藏
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        @SuppressLint("RestrictedApi")
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!canScrollVertically(-1)) {
                floatbtn.visibility = View.INVISIBLE
            }
        }
    })
    floatbtn.backgroundTintList = SettingUtil.getOneColorStateList(AppContext)
    floatbtn.setOnClickListener {
        val layoutManager = layoutManager as LinearLayoutManager
        //如果当前recyclerview 最后一个视图位置的索引大于等于40，则迅速返回顶部，否则带有滚动动画效果返回到顶部
        if (layoutManager.findLastVisibleItemPosition() >= 40) {
            scrollToPosition(0)//没有动画迅速返回到顶部(马上)
        } else {
            smoothScrollToPosition(0)//有滚动动画返回到顶部(有点慢)
        }
    }
}

//指示器绑定ViewPager2
fun MagicIndicator.bindViewPager2(viewPager2: ViewPager2, titles: MutableList<IndicatorTitle>) {
    val indicator = this
    val navigator = CommonNavigator(AppContext)
        .apply {
            adapter = object : CommonNavigatorAdapter() {
                override fun getCount() = titles.size

                override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                    return ClipPagerTitleView(context)
                        .apply {
                            text = titles[index].title
                            textColor = Color.WHITE
                            clipColor = Color.WHITE
                            setOnClickListener {
                                viewPager2.currentItem = index
                            }
                        }
                }

                override fun getIndicator(context: Context): IPagerIndicator {
                    return LinePagerIndicator(context).apply {
                        setColors(Color.WHITE)
                        mode = LinePagerIndicator.MODE_WRAP_CONTENT
                    }
                }

            }
        }

    setNavigator(navigator)
    viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            indicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            indicator.onPageSelected(position)
        }

        override fun onPageScrollStateChanged(state: Int) {
            indicator.onPageScrollStateChanged(state)
        }
    })
}

fun ViewPager2.bindAdapter(
    fragments: MutableList<Fragment>,
    fm: Fragment,
    isUserInputEnabled: Boolean = true
): ViewPager2 {
    setUserInputEnabled(isUserInputEnabled)
    adapter = object : FragmentStateAdapter(fm) {
        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

    }
    return this
}