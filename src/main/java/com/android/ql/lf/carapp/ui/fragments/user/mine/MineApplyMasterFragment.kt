package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.fragments.BaseFragment
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import kotlinx.android.synthetic.main.fragment_mine_apply_master_layout.*
import java.lang.reflect.Field

/**
 * Created by lf on 18.2.8.
 * @author lf on 18.2.8
 */
class MineApplyMasterFragment : BaseFragment() {

    companion object {
        val TITLES = listOf("修理师傅", "门店商家")
    }

    override fun getLayoutId() = R.layout.fragment_mine_apply_master_layout

    override fun initView(view: View?) {
        mVpMineApplyMaster.adapter = ApplyMasterViewPagerAdapter(childFragmentManager)
        mTlMineApplyMaster.setupWithViewPager(mVpMineApplyMaster)
        setIndicator(mTlMineApplyMaster, 30, 30)
    }

    private fun setIndicator(tabs: TabLayout, leftDip: Int, rightDip: Int) {
        val tabLayout = tabs.javaClass
        var tabStrip: Field? = null
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip")
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }

        tabStrip!!.isAccessible = true
        var llTab: LinearLayout? = null
        try {
            llTab = tabStrip.get(tabs) as LinearLayout
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        val left = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip.toFloat(), resources.displayMetrics).toInt()
        val right = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip.toFloat(), resources.displayMetrics).toInt()

        for (i in 0 until llTab!!.childCount) {
            val child = llTab.getChildAt(i)
            child.setPadding(0, 0, 0, 0)
            val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
            params.leftMargin = left
            params.rightMargin = right
            child.layoutParams = params
            child.invalidate()
        }
    }

    class ApplyMasterViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

        override fun getItem(position: Int) = MineApplyMasterItemFragment()

        override fun getCount() = TITLES.size

        override fun getPageTitle(position: Int) = TITLES[position]
    }
}

class MineApplyMasterItemFragment : BaseNetWorkingFragment() {

    override fun getLayoutId() = R.layout.fragment_mine_apply_master_item_layout

    override fun initView(view: View?) {
    }

}
