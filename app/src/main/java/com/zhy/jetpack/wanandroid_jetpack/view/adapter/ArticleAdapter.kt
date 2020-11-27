package com.zhy.jetpack.wanandroid_jetpack.view.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhy.jetpack.wanandroid_jetpack.R
import com.zhy.jetpack.wanandroid_jetpack.data.model.Article
import com.zhy.jetpack.wanandroid_jetpack.ext.setAdapterAnimation
import com.zhy.jetpack.wanandroid_jetpack.ext.toHtml
import com.zhy.jetpack.wanandroid_jetpack.utils.SettingUtil

class ArticleAdapter(data: MutableList<Article>) :

    BaseQuickAdapter<Article, BaseViewHolder>(R.layout.item_article, data) {


    init {
        setAdapterAnimation(SettingUtil.getListMode())
    }

    override fun convert(holder: BaseViewHolder, item: Article) {

        item.run {
            holder.setText(R.id.item_home_content, title.toHtml())
            holder.setText(R.id.item_home_author, if (author.isNotEmpty()) author else shareUser)
            holder.setText(R.id.item_home_type2, "$superChapterName·$chapterName".toHtml())
            holder.setText(R.id.item_home_date, niceDate)
//            holder.getView<CollectView>(R.id.item_home_collect).isChecked = collect

            holder.setGone(R.id.item_home_new, !fresh)
            holder.setGone(R.id.item_home_top, type != 1)
            if (tags.isNotEmpty()) {
                holder.setGone(R.id.item_home_type1, false)
                holder.setText(R.id.item_home_type1, tags[0].name)
            } else {
                holder.setGone(R.id.item_home_type1, true)
            }
        }
    }
}