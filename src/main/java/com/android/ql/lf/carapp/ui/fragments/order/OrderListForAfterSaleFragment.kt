package com.android.ql.lf.carapp.ui.fragments.order

import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.OrderBean
import com.android.ql.lf.carapp.data.UserInfo
import com.android.ql.lf.carapp.ui.adapter.OrderListForAfterSaleAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.1.27.
 * @author lf on 18.1.27
 */
class OrderListForAfterSaleFragment : BaseRecyclerViewFragment<OrderBean>() {

    companion object {
        fun newInstance(): OrderListForAfterSaleFragment {
            return OrderListForAfterSaleFragment()
        }
    }

    override fun initView(view: View?) {
        super.initView(view)
        registerLoginSuccessBus()
    }

    override fun createAdapter(): BaseQuickAdapter<OrderBean, BaseViewHolder>
            = OrderListForAfterSaleAdapter(R.layout.adapter_order_list_for_after_sale_item_layout, mArrayList)

    override fun getEmptyMessage() = if (!UserInfo.getInstance().isLogin) {
        resources.getString(R.string.login_notify_title)
    } else {
        "暂没有售后订单"
    }!!

    override fun onRefresh() {
        super.onRefresh()
        if (!UserInfo.getInstance().isLogin) {
            setEmptyViewStatus()
        } else {
            setRefreshEnable(true)
            mPresent.getDataByPost(0x0, RequestParamsHelper.ORDER_MODEL, RequestParamsHelper.ACT_MY_SALE_QORDER, RequestParamsHelper.getMySaleQorderParam(page = currentPage))
        }
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mPresent.getDataByPost(0x0, RequestParamsHelper.ORDER_MODEL, RequestParamsHelper.ACT_MY_SALE_QORDER, RequestParamsHelper.getMySaleQorderParam(page = currentPage))
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        processList(result as String,OrderBean::class.java)
    }

    override fun onLoginSuccess(userInfo: UserInfo?) {
        super.onLoginSuccess(userInfo)
        onPostRefresh()
    }

}