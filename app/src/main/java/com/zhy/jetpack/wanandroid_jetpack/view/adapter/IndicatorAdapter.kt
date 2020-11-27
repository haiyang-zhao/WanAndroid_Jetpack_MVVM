package com.zhy.jetpack.wanandroid_jetpack.view.adapter

import android.content.Context
import android.graphics.Color
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView


class IndicatorAdapter(val data: MutableList<IndicatorTitle>) : CommonNavigatorAdapter() {
    override fun getCount() = data.size

    override fun getTitleView(context: Context, index: Int): IPagerTitleView {
        return ClipPagerTitleView(context)
            .apply {
                text = data[index].title
                textColor = Color.WHITE
                clipColor = Color.WHITE
            }
    }

    override fun getIndicator(context: Context?): IPagerIndicator? {
        val indicator = LinePagerIndicator(context)
        indicator.setColors(Color.WHITE)
        indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
        return indicator
    }
}


data class IndicatorTitle(val id: Int, val title: String)