package com.android.ql.lf.carapp.ui.fragments.order

import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.adapter.OrderListForAfterSaleAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.1.27.
 * @author lf on 18.1.27
 */
class OrderListForAfterSaleFragment : BaseRecyclerViewFragment<String>() {

    companion object {
        fun newInstance(): OrderListForAfterSaleFragment {
            return OrderListForAfterSaleFragment()
        }
    }

    override fun createAdapter(): BaseQuickAdapter<String, BaseViewHolder>
            = OrderListForAfterSaleAdapter(R.layout.adapter_order_list_for_after_sale_item_layout, mArrayList)


    override fun onRefresh() {
//        super.onRefresh()
        testAdd("")
    }

}