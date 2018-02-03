package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.widget.TextView
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.2.3.
 * @author lf on 18.2.3
 */
class MineCashListFragment : BaseRecyclerViewFragment<String>() {

    override fun createAdapter(): BaseQuickAdapter<String, BaseViewHolder> = CashListAdapter(R.layout.adapter_mine_cash_list_item_layout, mArrayList)

    override fun onRefresh() {
        super.onRefresh()
        testAdd("")
    }

    class CashListAdapter(resId: Int, list: ArrayList<String>) : BaseQuickAdapter<String, BaseViewHolder>(resId, list) {
        override fun convert(helper: BaseViewHolder?, item: String?) {
            val tv_time = helper!!.getView<TextView>(R.id.mTvCashItemTime)
            val tv_count = helper!!.getView<TextView>(R.id.mTvCashItemCount)
            val tv_result = helper!!.getView<TextView>(R.id.mTvCashItemResult)
            if (helper.adapterPosition % 2 == 0) {
                tv_time.setTextColor(mContext.resources.getColor(R.color.text_deep_dark_color))
                tv_count.setTextColor(mContext.resources.getColor(R.color.text_deep_dark_color))
                tv_result.setTextColor(mContext.resources.getColor(R.color.colorPrimary))
            }else{
                tv_time.setTextColor(mContext.resources.getColor(R.color.text_dark_color))
                tv_count.setTextColor(mContext.resources.getColor(R.color.text_dark_color))
                tv_result.setTextColor(mContext.resources.getColor(R.color.text_dark_color))
            }
        }
    }

}