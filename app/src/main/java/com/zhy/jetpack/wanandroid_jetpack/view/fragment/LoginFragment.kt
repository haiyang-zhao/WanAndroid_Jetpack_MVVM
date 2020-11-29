package com.zhy.jetpack.wanandroid_jetpack.view.fragment

import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import com.zhy.jetpack.mvvm.ext.nav
import com.zhy.jetpack.wanandroid_jetpack.R
import com.zhy.jetpack.wanandroid_jetpack.base.BaseFragment
import com.zhy.jetpack.wanandroid_jetpack.databinding.FragmentLoginBinding
import com.zhy.jetpack.wanandroid_jetpack.ext.hideSoftKeyboard
import com.zhy.jetpack.wanandroid_jetpack.ext.initClose
import com.zhy.jetpack.wanandroid_jetpack.ext.showMessage
import com.zhy.jetpack.wanandroid_jetpack.utils.cacheIsLogin
import com.zhy.jetpack.wanandroid_jetpack.utils.cacheUse
import com.zhy.jetpack.wanandroid_jetpack.viewmodel.request.RequestLoginViewModel
import com.zhy.jetpack.wanandroid_jetpack.viewmodel.state.LoginViewModel
import kotlinx.android.synthetic.main.include_toolbar.*

class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    private val requestViewModel by viewModels<RequestLoginViewModel>()

    override fun addObserver() {
        requestViewModel.apply {
            loginState.observe(viewLifecycleOwner, {
                if (it.isSuccess) {
                    sharedViewModel.currentUser.value = it.data[0]
                    cacheUse(it.data[0])
                    cacheIsLogin(true)
                    nav().navigateUp()
                } else {
                    showMessage(it.errorMsg)
                }
            })
        }
    }

    override fun initView() {
        mBinding.vm = mViewModel
        mBinding.click = ClickProxy()
        initToolbar()
        mViewModel.username.value ="zhy088422"
        mViewModel.password.value="12345678"
    }

    override fun initData() {
    }

    override fun layoutId(): Int {
        return R.layout.fragment_login
    }

    private fun initToolbar() {
        toolbar.run {
            //设置menu 关键代码
            mActivity.setSupportActionBar(this)
            initClose(getString(R.string.login)) {
                hideSoftKeyboard(activity)
                nav().navigateUp()
            }
        }
    }

    inner class ClickProxy {
        fun login() {
            val username = mViewModel.username.value
            val password = mViewModel.password.value
            if (username.isEmpty() || password.isEmpty()) {
                showMessage("用户名和密码不能为空")
            }
            requestViewModel.requestLogin(username, password)
        }


        var onCheckedChangeListener =
            CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                mViewModel.showPwd.value = isChecked
            }

        fun clear() {
            mViewModel.username.value = ""
        }
    }
}