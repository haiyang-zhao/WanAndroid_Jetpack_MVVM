package com.zhy.jetpack.mvvm.anotations

import androidx.annotation.LayoutRes


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ContentView(@LayoutRes val resId: Int)
