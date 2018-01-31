package com.android.ql.lf.carapp.ui.fragments.order

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.util.Log
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.adapter.OrderListForMineForWaitingWorkAdapter
import com.android.ql.lf.carapp.ui.fragments.AbstractLazyLoadFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy


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

    override fun loadData() {
        testAdd("")
        isLoad = true
    }

    override fun onMyItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemClick(adapter, view, position)
        FragmentContainerActivity.startFragmentContainerActivity(mContext, "订单详情", true, false, OrderDetailForWaitingWorkFragment::class.java)
    }

    override fun onMyItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemChildClick(adapter, view, position)
        when(view!!.id){
            R.id.mBtOrderListForWaitingWorkCamera->{
                FragmentContainerActivity.startFragmentContainerActivity(mContext,"拍照",true,false,OrderImageUpLoadFragment::class.java)
            }
        }
    }

}