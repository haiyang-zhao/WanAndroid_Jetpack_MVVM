package com.zhy.jetpack.wanandroid_jetpack.view.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhy.jetpack.wanandroid_jetpack.R
import com.zhy.jetpack.wanandroid_jetpack.ext.setAdapterAnimation
import com.zhy.jetpack.wanandroid_jetpack.utils.SettingUtil

const val CHANNEL_OPT_NONE = 0
const val CHANNEL_OPT_ADD = 1
const val CHANNEL_OPT_REMOVE = 2

class ChannelTagAdapter(
    data: MutableList<IndicatorTitle>,
    var opt: Int = CHANNEL_OPT_NONE,
    var optListener: TagOperationListener? = null
) :

    BaseQuickAdapter<IndicatorTitle, BaseViewHolder>(R.layout.item_channel_tag, data) {


    init {
        setAdapterAnimation(SettingUtil.getListMode())
    }

    override fun convert(holder: BaseViewHolder, item: IndicatorTitle) {

        holder.setGone(R.id.iv_opt, opt == 0)
        holder.setImageResource(
            R.id.iv_opt,
            if (opt == 2) R.drawable.ic_remove_1 else R.drawable.ic_add_1
        )
        item.run {
            holder.setText(R.id.tv_tag, item.title)
        }
        holder.getView<ImageView>(R.id.iv_opt).setOnClickListener {
            optListener?.doOperation(opt, item)
        }
    }

    interface TagOperationListener {
        fun doOperation(opt: Int, tag: IndicatorTitle)
    }
}