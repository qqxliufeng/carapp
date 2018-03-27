package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.os.Bundle
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.MallSaleOrderBean
import com.android.ql.lf.carapp.data.SearchParamBean
import com.android.ql.lf.carapp.ui.adapter.MainMallOrderItemAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.3.27.
 * @author lf on 18.3.27
 */
class MainMallOrderAllListFragment :BaseRecyclerViewFragment<MallSaleOrderBean>(){

    companion object {
        val SEARCH_PARAM_FLAG = "search_param_flag"

        fun newInstance(bundle: Bundle): MainMallOrderAllListFragment {
            val fragment = MainMallOrderAllListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun createAdapter(): BaseQuickAdapter<MallSaleOrderBean, BaseViewHolder> =
            MainMallOrderItemAdapter(R.layout.adapter_main_mall_order_item_layout, mArrayList)

    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0, RequestParamsHelper.MEMBER_MODEL, RequestParamsHelper.ACT_MYORDER, RequestParamsHelper.getMyorderParams(currentPage))
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mPresent.getDataByPost(0x0, RequestParamsHelper.MEMBER_MODEL, RequestParamsHelper.ACT_MYORDER, RequestParamsHelper.getMyorderParams(currentPage))
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        processList(result as String, MallSaleOrderBean::class.java)
    }

}