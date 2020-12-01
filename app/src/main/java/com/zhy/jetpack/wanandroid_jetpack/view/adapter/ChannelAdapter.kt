package com.zhy.jetpack.wanandroid_jetpack.view.adapter

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.zhy.jetpack.wanandroid_jetpack.R
import com.zhy.jetpack.wanandroid_jetpack.data.model.Channel
import com.zhy.jetpack.wanandroid_jetpack.ext.setAdapterAnimation
import com.zhy.jetpack.wanandroid_jetpack.utils.SettingUtil

class ChannelAdapter(
    data: MutableList<Channel>,
    var optListener: ChannelTagAdapter.TagOperationListener? = null
) :

    BaseQuickAdapter<Channel, BaseViewHolder>(
        R.layout.item_channel,
        data
    ) {


    init {
        setAdapterAnimation(SettingUtil.getListMode())
    }

    override fun convert(
        holder: BaseViewHolder,
        item: Channel
    ) {

        item.run {
            holder.setGone(R.id.tv_channel_title, holder.layoutPosition == 0)
            holder.setText(R.id.tv_channel_title, item.name)
            holder.getView<RecyclerView>(R.id.rv_channel_children)
                .apply {
                    layoutManager = FlexboxLayoutManager(context).apply {
                        //方向 主轴为水平方向，起点在左端
                        flexDirection = FlexDirection.ROW
                        //左对齐
                        justifyContent = JustifyContent.FLEX_START
                    }
                    adapter =
                        ChannelTagAdapter(item.children.map { i -> IndicatorTitle(i.id, i.name) }
                            .toMutableList(), CHANNEL_OPT_ADD, optListener)
                }
        }
    }
}

