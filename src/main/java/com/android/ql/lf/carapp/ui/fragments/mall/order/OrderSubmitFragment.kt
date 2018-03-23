package com.android.ql.lf.carapp.ui.fragments.mall.order

import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.AddressBean
import com.android.ql.lf.carapp.data.ShoppingCarItemBean
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carapp.ui.fragments.mall.address.AddressSelectFragment
import com.android.ql.lf.carapp.ui.views.SelectPayTypeView
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import com.android.ql.lf.carapp.utils.RxBus
import com.android.ql.lf.carapp.utils.toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_order_submit_layout.*
import org.json.JSONObject

/**
 * Created by lf on 18.3.22.
 * @author lf on 18.3.22
 */
class OrderSubmitFragment : BaseRecyclerViewFragment<ShoppingCarItemBean>() {

    /**
     * 头部View
     */
    private val headerView by lazy {
        View.inflate(mContext, R.layout.layout_submit_new_order_header_layout, null)
    }

    /**
     * 空地址View
     */
    private val emptyAddressButton by lazy {
        headerView.findViewById<Button>(R.id.mBtSubmitOrderHeaderNoAddress)
    }

    /**
     * 地址包含View
     */
    private val selectAddressContainerView by lazy {
        headerView.findViewById<LinearLayout>(R.id.mLlSubmitOrderAddress)
    }

    /**
     * 地址名
     */
    private val addressName by lazy {
        headerView.findViewById<TextView>(R.id.mIvSubmitOrderAddressName)
    }

    /**
     * 地址手机号
     */
    private val addressPhone by lazy {
        headerView.findViewById<TextView>(R.id.mIvSubmitOrderAddressPhone)
    }

    /**
     * 地址详细信息
     */
    private val addressDetail by lazy {
        headerView.findViewById<TextView>(R.id.mIvSubmitOrderAddressDetail)
    }

    /**
     * 底部View
     */
    private val footerView by lazy { View.inflate(mContext, R.layout.layout_submit_new_order_footer_layout, null) }

    private val addressSubscription by lazy {
        RxBus.getDefault().toObservable(AddressBean::class.java).subscribe {
            if (it != null) {
                addressBean = it
                setAddressInfo(addressBean!!)
            }
        }
    }

    private var addressBean: AddressBean? = null

    override fun createAdapter(): BaseQuickAdapter<ShoppingCarItemBean, BaseViewHolder> {
        return object : BaseQuickAdapter<ShoppingCarItemBean, BaseViewHolder>(R.layout.adapter_submit_order_info_item_layout, mArrayList) {
            override fun convert(helper: BaseViewHolder?, item: ShoppingCarItemBean?) {
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_order_submit_layout

    override fun initView(view: View?) {
        super.initView(view)
        addressSubscription
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
        selectAddressContainerView.setOnClickListener {
            FragmentContainerActivity.from(mContext).setTitle("选择地址").setNeedNetWorking(true).setClazz(AddressSelectFragment::class.java).start()
        }
        mTvSubmitOrder.setOnClickListener {
            if (addressBean == null) {
                toast("请选择收货地址")
                return@setOnClickListener
            }
        }
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        val itemDecoration = super.getItemDecoration() as DividerItemDecoration
        itemDecoration.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.recycler_view_height_divider))
        return itemDecoration
    }

    private fun setAddressInfo(addressBean: AddressBean) {
        addressName.text = addressBean.address_name
        addressPhone.text = addressBean.address_phone
        addressDetail.text = addressBean.address_detail
        emptyAddressButton.visibility = View.GONE
        selectAddressContainerView.visibility = View.VISIBLE
    }

    override fun onRefresh() {
        super.onRefresh()
        //加载地址
        mPresent.getDataByPost(0x0, RequestParamsHelper.ORDER_MODEL, RequestParamsHelper.ACT_DEFAULT_ADDRESS, RequestParamsHelper.getDefaultAddress())
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        if (requestID == 0x0) {
            getFastProgressDialog("正在加载地址……")
        }
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        if (requestID == 0x0) {
            //加载地址
            try {
                val check = checkResultCode(result)
                if (check != null && check.code == SUCCESS_CODE) {
                    val resultJson = check.obj as JSONObject
                    addressBean = Gson().fromJson(resultJson.optJSONObject("result").toString(), AddressBean::class.java)
                    if (addressBean != null) {
                        setAddressInfo(addressBean!!)
                    }
                }
            } catch (e: Exception) {
                emptyAddressButton.visibility = View.VISIBLE
                selectAddressContainerView.visibility = View.GONE
            }
        } else {
            //提交订单
        }
    }

    override fun onRequestFail(requestID: Int, e: Throwable) {
        super.onRequestFail(requestID, e)
        if (requestID == 0x0) {
            emptyAddressButton.visibility = View.VISIBLE
            selectAddressContainerView.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        unsubscribe(addressSubscription)
        super.onDestroyView()
    }

}