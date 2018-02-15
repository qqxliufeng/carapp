package com.android.ql.lf.carapp.ui.fragments.order

import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.UserInfo
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.adapter.OrderListForMineForWaitingWorkAdapter
import com.android.ql.lf.carapp.ui.fragments.AbstractLazyLoadFragment
import com.android.ql.lf.carapp.ui.fragments.user.LoginFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


/**
 * Created by lf on 18.1.25.
 * @author lf on 18.1.25
 */
class OrderListForMineForWaitingWorkFragment : AbstractLazyLoadFragment<String>() {

    companion object {
        fun newInstance(): OrderListForMineForWaitingWorkFragment {
            return OrderListForMineForWaitingWorkFragment()
        }
    }

    override fun createAdapter(): BaseQuickAdapter<String, BaseViewHolder>
            = OrderListForMineForWaitingWorkAdapter(R.layout.adapter_order_list_for_mine_for_waiting_work_item_layout, mArrayList)

    override fun initView(view: View?) {
        super.initView(view)
        registerLoginSuccessBus()
    }

    override fun getEmptyMessage() = if (!UserInfo.getInstance().isLogin) {
        resources.getString(R.string.login_notify_title)
    } else {
        "暂没有待施工订单"
    }!!

    override fun loadData() {
        if (!UserInfo.getInstance().isLogin) {
            setEmptyViewStatus()
        } else {
            isLoad = true
//            testAdd("")
            setEmptyView()
        }
    }

    override fun onLoginSuccess(userInfo: UserInfo?) {
        super.onLoginSuccess(userInfo)
        loadData()
    }

    override fun onMyItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemClick(adapter, view, position)
        FragmentContainerActivity.startFragmentContainerActivity(mContext, "订单详情", true, false, OrderDetailForWaitingWorkFragment::class.java)
    }

    override fun onMyItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemChildClick(adapter, view, position)
        when (view!!.id) {
            R.id.mBtOrderListForWaitingWorkCamera -> {
                FragmentContainerActivity.startFragmentContainerActivity(mContext, "拍照", true, false, OrderImageUpLoadFragment::class.java)
            }
        }
    }

}