package com.zhy.jetpack.wanandroid_jetpack.view.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhy.jetpack.wanandroid_jetpack.R

class SampleListAdapter(data: MutableList<String>) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.list_item_sample, data) {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.text, item)
    }
}