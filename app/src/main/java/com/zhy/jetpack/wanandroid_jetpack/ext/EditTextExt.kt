package com.zhy.jetpack.wanandroid_jetpack.ext

import android.widget.EditText

fun EditText.textString(): String {
    return this.text.toString()
}