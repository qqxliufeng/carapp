package com.android.ql.lf.carapp.ui.fragments.mall.order

import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.ShoppingCarItemBean
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carapp.ui.fragments.mall.address.AddressSelectFragment
import com.android.ql.lf.carapp.ui.views.SelectPayTypeView
import com.android.ql.lf.carapp.utils.toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_order_submit_layout.*

/**
 * Created by lf on 18.3.22.
 * @author lf on 18.3.22
 */
class OrderSubmitFragment : BaseRecyclerViewFragment<ShoppingCarItemBean>() {

    private val headerView by lazy { View.inflate(mContext, R.layout.layout_submit_new_order_header_layout, null) }

    private val emptyAddressButton by lazy {
        headerView.findViewById<Button>(R.id.mBtSubmitOrderHeaderNoAddress)
    }

    private val selectAddressView by lazy {
        headerView.findViewById<LinearLayout>(R.id.mLlSubmitOrderAddress)
    }

    private val footerView by lazy { View.inflate(mContext, R.layout.layout_submit_new_order_footer_layout, null) }

    override fun createAdapter(): BaseQuickAdapter<ShoppingCarItemBean, BaseViewHolder> {
        return object : BaseQuickAdapter<ShoppingCarItemBean, BaseViewHolder>(R.layout.adapter_submit_order_info_item_layout, mArrayList) {
            override fun convert(helper: BaseViewHolder?, item: ShoppingCarItemBean?) {
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_order_submit_layout

    override fun initView(view: View?) {
        super.initView(view)
        mSwipeRefreshLayout.isEnabled = false
        mBaseAdapter.addHeaderView(headerView)
        mBaseAdapter.addFooterView(footerView)
        val selectTypeView = footerView.findViewById<SelectPayTypeView>(R.id.mStvPay)
        mTvSubmitOrder.setOnClickListener {
            toast(selectTypeView.payType)
        }
        emptyAddressButton.setOnClickListener {
            FragmentContainerActivity.from(mContext).setTitle("选择地址").setNeedNetWorking(true).setClazz(AddressSelectFragment::class.java).start()
        }
        selectAddressView.setOnClickListener {
            FragmentContainerActivity.from(mContext).setTitle("选择地址").setNeedNetWorking(true).setClazz(AddressSelectFragment::class.java).start()
        }
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        val itemDecoration = super.getItemDecoration() as DividerItemDecoration
        itemDecoration.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.recycler_view_height_divider))
        return itemDecoration
    }

}