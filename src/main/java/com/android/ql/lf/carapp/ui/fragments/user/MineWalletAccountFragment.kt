package com.android.ql.lf.carapp.ui.fragments.user

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.fragments.BaseFragment
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import kotlinx.android.synthetic.main.fragment_mine_wallet_account_layout.*

/**
 * Created by liufeng on 2018/2/25.
 */
class MineWalletAccountFragment : BaseFragment() {

    private val titles by lazy {
        arrayListOf("微信", "支付宝")
    }

    override fun getLayoutId() = R.layout.fragment_mine_wallet_account_layout

    override fun initView(view: View?) {
        mVpMineWalletAccountContainer.adapter = MineWalletAccountViewPagerAdapter(childFragmentManager)
        mTlMineWalletAccountTitle.setupWithViewPager(mVpMineWalletAccountContainer)
    }

    inner class MineWalletAccountViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
        override fun getItem(position: Int) = when (position) {
            0 -> {
                MineWalletWXAccountFragment()
            }
            1 -> {
                MineWalletAliAccountFragment()
            }
            else -> {
                null
            }
        }

        override fun getCount() = titles.size

        override fun getPageTitle(position: Int) = titles[position]
    }

    class MineWalletWXAccountFragment : BaseNetWorkingFragment() {
        override fun getLayoutId() = android.R.layout.simple_list_item_1

        override fun initView(view: View?) {
        }
    }


    class MineWalletAliAccountFragment : BaseNetWorkingFragment() {
        override fun getLayoutId() = android.R.layout.simple_list_item_1

        override fun initView(view: View?) {
        }
    }

}