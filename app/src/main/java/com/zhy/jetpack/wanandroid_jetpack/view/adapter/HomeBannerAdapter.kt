package com.zhy.jetpack.wanandroid_jetpack.view.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder
import com.zhy.jetpack.wanandroid_jetpack.AppContext
import com.zhy.jetpack.wanandroid_jetpack.R
import com.zhy.jetpack.wanandroid_jetpack.data.model.Banner

class HomeBannerAdapter : BaseBannerAdapter<Banner, BannerViewHolder>() {
    override fun createViewHolder(
        parent: ViewGroup,
        itemView: View,
        viewType: Int
    ): BannerViewHolder {
        return BannerViewHolder(itemView)
    }

    override fun onBind(holder: BannerViewHolder, data: Banner, position: Int, pageSize: Int) {
        holder.bindData(data, position, pageSize)
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_banner_home
    }
}

class BannerViewHolder(itemView: View) : BaseViewHolder<Banner>(itemView) {

    override fun bindData(data: Banner, position: Int, pageSize: Int) {
        val image = findView<ImageView>(R.id.ivBanner)
        Glide.with(AppContext)
            .load(data.imagePath)
            .into(image)
    }

}