package com.zhy.jetpack.wanandroid_jetpack.widget.customview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.DrawableCompat
import com.zhy.jetpack.wanandroid_jetpack.R

class ColorImageView : AppCompatImageView {

    var mTintColor: ColorStateList? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.ColorImageView, defStyleAttr, defStyleAttr
        )
        mTintColor = a.getColorStateList(R.styleable.ColorImageView_itemIconTint)
        a.recycle()

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mTintColor?.apply {
            DrawableCompat.setTintList(drawable, mTintColor)
            drawable.invalidateSelf()
        }
    }

}