package com.android.ql.lf.carapp.ui.activities

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.fragments.bottom.MainCommunityFragment
import com.android.ql.lf.carapp.ui.fragments.bottom.MainMallFragment
import com.android.ql.lf.carapp.ui.fragments.bottom.MainMineFragment
import com.android.ql.lf.carapp.ui.fragments.bottom.MainOrderHouseFragment
import com.android.ql.lf.carapp.utils.BottomNavigationViewHelper
import kotlinx.android.synthetic.main.activity_main_layout.*

/**
 * Created by lf on 18.1.23.
 * @author lf on 18.1.23
 */
class MainActivity : BaseActivity() {

    override fun getLayoutId() = R.layout.activity_main_layout

    override fun initView() {
        BottomNavigationViewHelper.disableShiftMode(mMainNavigation)
        mMainNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        mMainContent.adapter = MainViewPagerAdapter(supportFragmentManager)
        mMainContent.offscreenPageLimit = 3
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                mMainContent.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                mMainContent.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
//            R.id.navigation_notifications -> {
//                mMainContent.currentItem = 2
//                return@OnNavigationItemSelectedListener true
//            }
            R.id.navigation_message -> {
                mMainContent.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    inner class MainViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

        override fun getItem(position: Int) = when (position) {
            0 -> {
                MainOrderHouseFragment.newInstance()
            }
            1 -> {
                MainCommunityFragment.newInstance()
            }
            2 -> {
//                MainMallFragment.newInstance()
                MainMineFragment.newInstance()
            }
            /*3 -> {
                MainMineFragment.newInstance()
            }*/
            else -> {
                null
            }
        }

        override fun getCount() = 3
    }

}