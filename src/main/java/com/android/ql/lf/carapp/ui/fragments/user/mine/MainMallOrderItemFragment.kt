package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.os.Bundle
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.MallSaleOrderBean
import com.android.ql.lf.carapp.data.SearchParamBean
import com.android.ql.lf.carapp.ui.adapter.MainMallOrderItemAdapter
import com.android.ql.lf.carapp.ui.fragments.AbstractLazyLoadFragment
import com.android.ql.lf.carapp.ui.fragments.mall.normal.SearchResultListFragment
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.3.27.
 * @author lf on 18.3.27
 */
class MainMallOrderItemFragment : AbstractLazyLoadFragment<MallSaleOrderBean>() {

    companion object {
        val SEARCH_PARAM_FLAG = "search_param_flag"

        val ORDER_TYPE_FLAG = "order_type_flag"

        fun newInstance(bundle: Bundle): MainMallOrderItemFragment {
            val fragment = MainMallOrderItemFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val searchParamBean by lazy {
        arguments.classLoader = this@MainMallOrderItemFragment.javaClass.classLoader
        arguments.getParcelable<SearchParamBean>(SEARCH_PARAM_FLAG)
    }

    private val postParam by lazy {
        val apiParam = RequestParamsHelper.getWithPageParams(currentPage)
        if (searchParamBean.params != null && !searchParamBean.params.isEmpty()) {
            searchParamBean.params.forEach {
                apiParam.put(it.key, it.value)
            }
        }
        apiParam
    }

    override fun createAdapter(): BaseQuickAdapter<MallSaleOrderBean, BaseViewHolder> =
            MainMallOrderItemAdapter(R.layout.adapter_main_mall_order_item_layout, mArrayList)

    override fun loadData() {
        isLoad = true
        mPresent.getDataByPost(0x0, searchParamBean.model, searchParamBean.act, postParam.addParam("page", currentPage))
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mPresent.getDataByPost(0x0, searchParamBean.model, searchParamBean.act, postParam.addParam("page", currentPage))
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        processList(result as String, MallSaleOrderBean::class.java)
    }

}