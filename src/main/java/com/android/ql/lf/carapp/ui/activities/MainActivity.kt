package com.android.ql.lf.carapp.ui.activities

import android.content.Intent
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.UserInfo
import com.android.ql.lf.carapp.ui.fragments.bottom.MainCommunityFragment
import com.android.ql.lf.carapp.ui.fragments.bottom.MainMallFragment
import com.android.ql.lf.carapp.ui.fragments.bottom.MainMineFragment
import com.android.ql.lf.carapp.ui.fragments.bottom.MainOrderHouseFragment
import com.android.ql.lf.carapp.utils.BottomNavigationViewHelper
import com.android.ql.lf.carapp.utils.Constants
import com.android.ql.lf.carapp.utils.PreferenceUtils
import com.android.ql.lf.carapp.utils.toast
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_main_layout.*

/**
 * Created by lf on 18.1.23.
 * @author lf on 18.1.23
 */
class MainActivity : BaseActivity() {

    private var exitTime: Long = 0L

    override fun getLayoutId() = R.layout.activity_main_layout

    override fun initView() {
        Beta.checkUpgrade(false,false)
        setSwipeBackEnable(false)
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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        mMainContent.currentItem = 0
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            toast("再按一次退出")
            exitTime = System.currentTimeMillis()
        } else {
            UserInfo.getInstance().loginOut()
            PreferenceUtils.setPrefBoolean(this, Constants.APP_IS_ALIVE, false)
            finish()
        }
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