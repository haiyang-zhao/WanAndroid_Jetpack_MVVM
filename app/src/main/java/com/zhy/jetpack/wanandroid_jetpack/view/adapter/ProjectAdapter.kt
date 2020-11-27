package com.zhy.jetpack.wanandroid_jetpack.view.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhy.jetpack.wanandroid_jetpack.AppContext
import com.zhy.jetpack.wanandroid_jetpack.R
import com.zhy.jetpack.wanandroid_jetpack.data.model.Article
import com.zhy.jetpack.wanandroid_jetpack.ext.setAdapterAnimation
import com.zhy.jetpack.wanandroid_jetpack.ext.toHtml
import com.zhy.jetpack.wanandroid_jetpack.utils.SettingUtil

class ProjectAdapter(data: MutableList<Article>) :

    BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_project, data) {


    init {
        setAdapterAnimation(SettingUtil.getListMode())
    }

    override fun convert(holder: BaseViewHolder, item: Article) {

        item.run {
            holder.setText(R.id.tv_author, if (author.isNotEmpty()) author else shareUser)
            holder.setText(R.id.tv_share_date, niceDate)
            holder.setText(R.id.tv_title, title.toHtml())
            holder.setText(R.id.tv_desc, desc.toHtml())
            val image = holder.getView<ImageView>(R.id.iv_pic)
            Glide.with(AppContext)
                .load(envelopePic)
                .into(image)
        }
    }
}