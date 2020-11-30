package com.zhy.jetpack.wanandroid_jetpack.widget.customview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class ScrollDisabledRecyclerView : RecyclerView {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)


    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    //不消费
    override fun onTouchEvent(e: MotionEvent?): Boolean {
        return false
    }

    //不拦截
    override fun onInterceptHoverEvent(event: MotionEvent?): Boolean {
        return false
    }
}