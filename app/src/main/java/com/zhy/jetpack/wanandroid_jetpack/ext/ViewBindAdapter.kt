package com.zhy.jetpack.wanandroid_jetpack.ext

import android.text.InputType
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.zhy.jetpack.wanandroid_jetpack.R
import com.zhy.jetpack.wanandroid_jetpack.viewmodel.state.OPT_EDIT

object ViewBindAdapter {


    //EditText 密码输入框 显示和隐藏密码
    @BindingAdapter(value = ["showPwd"])
    @JvmStatic
    fun showPwd(view: EditText, boolean: Boolean) {
        if (boolean) {
            view.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            view.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        view.setSelection(view.textString().length)
    }

    @BindingAdapter(value = ["checkChange"])
    @JvmStatic
    fun checkChange(checkbox: CheckBox, listener: CompoundButton.OnCheckedChangeListener) {
        checkbox.setOnCheckedChangeListener(listener)
    }

    @BindingAdapter(value = ["optText"])
    @JvmStatic
    fun optText(textView: TextView, opt: Int) {
        textView.setText(if (opt == OPT_EDIT) R.string.channel_edit else R.string.channel_complete)
    }
}