package com.android.ql.lf.carapp.ui.fragments.order

import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.adapter.OrderListForMineForWaitingWorkAdapter
import com.android.ql.lf.carapp.ui.fragments.AbstractLazyLoadFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.1.25.
 * @author lf on 18.1.25
 */
class OrderListForMineForHavingCalculateFragment : AbstractLazyLoadFragment<String>() {

    companion object {
        fun newInstance(): OrderListForMineForHavingCalculateFragment {
            return OrderListForMineForHavingCalculateFragment()
        }
    }

    override fun createAdapter(): BaseQuickAdapter<String, BaseViewHolder>
            = OrderListForMineForWaitingWorkAdapter(R.layout.adapter_order_list_for_mine_for_having_calculate_item_layout, mArrayList)

    override fun loadData() {
        testAdd("")
        isLoad = true
    }

}