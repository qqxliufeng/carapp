package com.android.ql.lf.carapp.ui.fragments.bottom

import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.activities.MainActivity
import com.android.ql.lf.carapp.ui.fragments.BaseFragment
import com.android.ql.lf.carapp.ui.fragments.message.MineMessageListFragment
import com.android.ql.lf.carapp.ui.fragments.order.OrderListForAfterSaleFragment
import com.android.ql.lf.carapp.ui.fragments.order.OrderListForMineFragment
import com.android.ql.lf.carapp.ui.fragments.order.OrderListForQDFragment
import kotlinx.android.synthetic.main.fragment_main_order_house_layout.*
import q.rorbin.badgeview.QBadgeView

/**
 * Created by lf on 18.1.24.
 * @author lf on 18.1.24
 */
class MainOrderHouseFragment : BaseFragment() {

    companion object {
        fun newInstance(): MainOrderHouseFragment {
            return MainOrderHouseFragment()
        }

        val TITLES = listOf("抢单", "我的订单", "售后订单")

    }

    override fun getLayoutId() = R.layout.fragment_main_order_house_layout

    override fun initView(view: View?) {
        val statusHeight = (mContext as MainActivity).statusHeight
        mAlOrderHouse.setPadding(0, statusHeight, 0, 0)
        mVpOrderHouse.adapter = OrderHouseViewPagerAdapter(childFragmentManager)
        mTlOrderHouse.setupWithViewPager(mVpOrderHouse)
        val orderCountBadgeView = QBadgeView(mContext)
        orderCountBadgeView.badgeNumber = 455
        val orderCountBadge = orderCountBadgeView.bindTarget(mIvMainOrderHouseCount)
        orderCountBadge.badgeBackgroundColor = Color.WHITE
        orderCountBadge.badgeTextColor = ContextCompat.getColor(mContext, R.color.main_color)
        orderCountBadge.setBadgeTextSize(8.0f, true)

        val notifyBadgeView = QBadgeView(mContext)
        notifyBadgeView.badgeNumber = -1
        val notifyBadge = notifyBadgeView.bindTarget(mIvMainNotifyCount)
        notifyBadge.isShowShadow = false
        notifyBadge.setGravityOffset(8.0f, 5.0f, true)
        mIvMainNotifyCount.setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "我的消息", true, false, MineMessageListFragment::class.java)
        }
        mAlOrderHouse.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            mTlOrderHouseTitleContainer.alpha = 1 - Math.abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange.toFloat()
        }
    }

    class OrderHouseViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
        override fun getItem(position: Int) = when (position) {
            0 -> {
                OrderListForQDFragment.newInstance()
            }
            1 -> {
                OrderListForMineFragment.newInstance()
            }
            else -> {
                OrderListForAfterSaleFragment.newInstance()
            }
        }

        override fun getCount() = MainOrderHouseFragment.TITLES.size

        override fun getPageTitle(position: Int) = MainOrderHouseFragment.TITLES[position]

    }

}