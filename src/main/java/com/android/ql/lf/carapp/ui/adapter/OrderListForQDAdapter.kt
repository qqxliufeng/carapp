package com.android.ql.lf.carapp.ui.adapter

import android.widget.Button
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.OrderBean
import com.android.ql.lf.carapp.data.UserInfo
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.1.25.
 * @author lf on 18.1.25
 */
class OrderListForQDAdapter(layoutId: Int, list: ArrayList<OrderBean>) : BaseQuickAdapter<OrderBean, BaseViewHolder>(layoutId, list) {

    override fun convert(helper: BaseViewHolder?, item: OrderBean?) {
        helper!!.addOnClickListener(R.id.mBtOrderListForQDItem)
        val bt_qd = helper.getView<Button>(R.id.mBtOrderListForQDItem)
        bt_qd.isEnabled = UserInfo.getInstance().isLogin /*&& UserInfo.getInstance().isMaster*/
        helper.setText(R.id.mTvOrderListForQDItemName, item!!.qorder_name)
    }
}