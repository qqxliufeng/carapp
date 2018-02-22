package com.android.ql.lf.carapp.ui.fragments.order

import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.UserInfo
import com.android.ql.lf.carapp.ui.adapter.OrderListForMineForWaitingWorkAdapter
import com.android.ql.lf.carapp.ui.fragments.AbstractLazyLoadFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.1.25.
 * @author lf on 18.1.25
 */
class OrderListForMineForWaitingCalculateFragment : AbstractLazyLoadFragment<String>() {

    companion object {
        fun newInstance(): OrderListForMineForWaitingCalculateFragment {
            return OrderListForMineForWaitingCalculateFragment()
        }
    }

    override fun createAdapter(): BaseQuickAdapter<String, BaseViewHolder>
            = OrderListForMineForWaitingWorkAdapter(R.layout.adapter_order_list_for_mine_for_waiting_calculate_item_layout, mArrayList)

    override fun initView(view: View?) {
        super.initView(view)
        registerLoginSuccessBus()
    }

    override fun getEmptyMessage() = if (!UserInfo.getInstance().isLogin) {
        resources.getString(R.string.login_notify_title)
    } else {
        "暂没有待结算订单"
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

}