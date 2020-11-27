package com.zhy.jetpack.wanandroid_jetpack.view.fragment

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zhy.jetpack.wanandroid_jetpack.R
import com.zhy.jetpack.wanandroid_jetpack.base.BaseFragment
import com.zhy.jetpack.wanandroid_jetpack.databinding.FragmentMainBinding
import com.zhy.jetpack.wanandroid_jetpack.viewmodel.state.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>() {


    override fun initView() {
        initViewPager()
        initBottomNav()
    }

    private fun initBottomNav() {
        mainBottomNav.apply {
            enableAnimation(true)
            enableShiftingMode(false)
            enableItemShiftingMode(true)
            onNavigationItemSelectedListener =
                BottomNavigationView.OnNavigationItemSelectedListener {
                    when (it.itemId) {
                        R.id.menu_main -> mainVp.setCurrentItem(0, false)
                        R.id.menu_project -> mainVp.setCurrentItem(1, false)
                        R.id.menu_system -> mainVp.setCurrentItem(2, false)
                        R.id.menu_public -> mainVp.setCurrentItem(3, false)
                        R.id.menu_me -> mainVp.setCurrentItem(4, false)
                    }
                    true
                }
        }
    }

    private fun initViewPager() {
        //禁止滑动
        mainVp.isUserInputEnabled = false
        mainVp.offscreenPageLimit = 5

        mainVp.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 5
            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> HomeFragment()
                    1 -> ProjectFragment()
                    2 -> SquareFragment()
                    3 -> OfficialAccountsFragment()
                    4 -> MeFragment()
                    else -> HomeFragment()
                }
            }
        }

    }

    override fun initData() {
    }


    override fun addObserver() {

    }

    override fun layoutId(): Int {
        return R.layout.fragment_main
    }
}