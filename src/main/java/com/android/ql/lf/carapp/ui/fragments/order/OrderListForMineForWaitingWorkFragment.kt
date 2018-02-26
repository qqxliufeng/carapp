package com.android.ql.lf.carapp.ui.fragments.order

import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.EventOrderStatusBean
import com.android.ql.lf.carapp.data.OrderBean
import com.android.ql.lf.carapp.data.UserInfo
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.adapter.OrderListForMineForWaitingWorkAdapter
import com.android.ql.lf.carapp.ui.fragments.AbstractLazyLoadFragment
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import com.android.ql.lf.carapp.utils.RxBus
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.jetbrains.anko.bundleOf


/**
 * Created by lf on 18.1.25.
 * @author lf on 18.1.25
 */
class OrderListForMineForWaitingWorkFragment : AbstractLazyLoadFragment<OrderBean>() {

    companion object {
        fun newInstance(): OrderListForMineForWaitingWorkFragment {
            return OrderListForMineForWaitingWorkFragment()
        }
    }

    private val updateOrderStatusSubscription by lazy {
        RxBus.getDefault().toObservable(EventOrderStatusBean::class.java).subscribe {
            if (it.orderStatus == 1) {
                loadData()
            }
        }
    }

    override fun createAdapter(): BaseQuickAdapter<OrderBean, BaseViewHolder>
            = OrderListForMineForWaitingWorkAdapter(R.layout.adapter_order_list_for_mine_for_waiting_work_item_layout, mArrayList)

    override fun initView(view: View?) {
        super.initView(view)
        registerLoginSuccessBus()
        updateOrderStatusSubscription
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
            setRefreshEnable(true)
            mPresent.getDataByPost(0x0, RequestParamsHelper.ORDER_MODEL, RequestParamsHelper.ACT_MY_QORDER, RequestParamsHelper.getMyQorderParam("1", currentPage))
        }
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mPresent.getDataByPost(0x0, RequestParamsHelper.ORDER_MODEL, RequestParamsHelper.ACT_MY_QORDER, RequestParamsHelper.getMyQorderParam("1", currentPage))
    }

    override fun onLoginSuccess(userInfo: UserInfo?) {
        super.onLoginSuccess(userInfo)
        loadData()
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        if (requestID == 0x0) {
            processList(result as String, OrderBean::class.java)
        }else if (requestID == 0x1){

        }
    }

    override fun onMyItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemClick(adapter, view, position)
        FragmentContainerActivity.startFragmentContainerActivity(mContext, "订单详情", true, false, OrderDetailForWaitingWorkFragment::class.java)
    }

    override fun onMyItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemChildClick(adapter, view, position)
        when (view!!.id) {
            R.id.mBtOrderListForWaitingWorkCamera -> {
                FragmentContainerActivity
                        .from(mContext)
                        .setTitle("拍照")
                        .setNeedNetWorking(true)
                        .setClazz(OrderImageUpLoadFragment::class.java)
                        .setExtraBundle(bundleOf(Pair("oid", mArrayList[position].qorder_id)))
                        .start()
            }
            R.id.mBtOrderListForWaitingWorkComplete -> {
                mPresent.getDataByPost(0x1,
                        RequestParamsHelper.ORDER_MODEL,
                        RequestParamsHelper.ACT_EDIT_QORDER_STATUS,
                        RequestParamsHelper.getEditQorderStatusParam(mArrayList[position].qorder_id, "2"))
            }
        }
    }

    override fun onDestroyView() {
        unsubscribe(updateOrderStatusSubscription)
        super.onDestroyView()
    }

}