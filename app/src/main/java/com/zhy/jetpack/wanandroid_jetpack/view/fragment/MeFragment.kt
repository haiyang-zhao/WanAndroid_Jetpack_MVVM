package com.zhy.jetpack.wanandroid_jetpack.view.fragment

import android.view.View
import androidx.fragment.app.viewModels
import com.zhy.jetpack.mvvm.ext.nav
import com.zhy.jetpack.mvvm.ext.navigateAction
import com.zhy.jetpack.mvvm.ext.notNull
import com.zhy.jetpack.wanandroid_jetpack.R
import com.zhy.jetpack.wanandroid_jetpack.base.BaseFragment
import com.zhy.jetpack.wanandroid_jetpack.databinding.FragmentMeBinding
import com.zhy.jetpack.wanandroid_jetpack.utils.curUser
import com.zhy.jetpack.wanandroid_jetpack.utils.isLogin
import com.zhy.jetpack.wanandroid_jetpack.viewmodel.request.RequestViewModel
import com.zhy.jetpack.wanandroid_jetpack.viewmodel.state.MeViewModel

class MeFragment : BaseFragment<MeViewModel, FragmentMeBinding>() {

    private val requestViewModel by viewModels<RequestViewModel>()

    override fun addObserver() {
        sharedViewModel.apply {

            currentUser.observeInFragment(this@MeFragment) { user ->
                user.notNull({
                    mViewModel.isLogin.set(true)
                    mViewModel.coin.set(user.coinCount)
                    mViewModel.name.set(user.nickname)
                    requestViewModel.requestUserCoin()
                }, {
                    mViewModel.isLogin.set(false)
                })
            }
        }

        requestViewModel.meData.observe(viewLifecycleOwner, {
            if (it.isSuccess) {
                val userCoin = it.data[0]
                mViewModel.coin.set(userCoin.coinCount)
                mViewModel.info.set("id：${userCoin.userId}　排名：${userCoin.rank}")
            }
        })
    }

    override fun initView() {
        mBinding.vm = mViewModel
        mBinding.click = ProxyClick()

        mViewModel.isLogin.set(isLogin())
        mViewModel.name.set(curUser()?.nickname)
    }

    override fun initData() {
    }

    override fun lazyLoadData() {
        requestViewModel.requestUserCoin()
    }

    override fun layoutId(): Int {
        return R.layout.fragment_me
    }


    inner class ProxyClick {


        fun login() {
            nav().navigateAction(R.id.action_to_loginFragment)
        }

        fun integral() {

        }

        fun collect() {

        }

        fun article() {

        }

        fun todo() {

        }

        fun about() {

        }

        fun join() {

        }

        fun demo() {

        }

        fun setting() {

        }


        fun visibility(isShow: Boolean): Int {
            return if (isShow) View.VISIBLE else View.GONE
        }

    }
}