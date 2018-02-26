package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AlertDialog
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.fragments.BaseFragment
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import com.android.ql.lf.carapp.utils.getTextString
import com.android.ql.lf.carapp.utils.isEmpty
import com.android.ql.lf.carapp.utils.toast
import kotlinx.android.synthetic.main.fragment_mine_wallet_account_layout.*
import kotlinx.android.synthetic.main.fragment_wallet_account_bind_ali_layout.*

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

        override fun onRequestStart(requestID: Int) {
            super.onRequestStart(requestID)
            getFastProgressDialog("正在提交信息……")
        }
        override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
            super.onRequestSuccess(requestID, result)
        }

        override fun onRequestFail(requestID: Int, e: Throwable) {
            super.onRequestFail(requestID, e)
            toast("账号绑定失败，请稍后重试……")
        }

    }


    class MineWalletAliAccountFragment : BaseNetWorkingFragment() {

        override fun getLayoutId() = R.layout.fragment_wallet_account_bind_ali_layout

        override fun initView(view: View?) {
            mBtBindAliAccount.setOnClickListener {
                if (mEtBindAliAccountRealName.isEmpty()) {
                    toast(mEtBindAliAccountRealName.hint.toString())
                    return@setOnClickListener
                }
                if (mEtBindAliAccount.isEmpty()) {
                    toast(mEtBindAliAccount.hint.toString())
                    return@setOnClickListener
                }
                mPresent.getDataByPost(0x0, RequestParamsHelper.MEMBER_MODEL, RequestParamsHelper.ACT_BIND_ALIPAY, RequestParamsHelper.getBindAlipayParam(mEtBindAliAccount.getTextString(), mEtBindAliAccountRealName.getTextString()))
            }
        }

        override fun onRequestStart(requestID: Int) {
            super.onRequestStart(requestID)
            getFastProgressDialog("正在提交信息……")
        }
        override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
            super.onRequestSuccess(requestID, result)
        }

        override fun onRequestFail(requestID: Int, e: Throwable) {
            super.onRequestFail(requestID, e)
            toast("账号绑定失败，请稍后重试……")
        }
    }
}