package com.android.ql.lf.carapp.ui.fragments.order

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.OrderBean
import com.android.ql.lf.carapp.present.ServiceOrderPresent
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import com.android.ql.lf.carapp.utils.toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_order_detail_for_having_calculate_layout.*
import org.json.JSONObject

/**
 * Created by lf on 18.1.31.
 * @author lf on 18.1.31
 */
class OrderDetailForHavingWorkFragment : BaseNetWorkingFragment() {

    companion object {
        val ORDER_BEAN_FLAG = "order_bean_flag"
    }

    private var orderBean: OrderBean? = null

    override fun getLayoutId(): Int = R.layout.fragment_order_detail_for_having_calculate_layout

    override fun initView(view: View?) {
        mTvOrderDetailLoadFail.setOnClickListener {
            mPresent.getDataByPost(0x0, RequestParamsHelper.ORDER_MODEL, RequestParamsHelper.ACT_QORDER_DETAIL, RequestParamsHelper.getOrderDetailParam(arguments.getString(OrderDetailForHavingWorkFragment.ORDER_BEAN_FLAG)))
        }
        mPresent.getDataByPost(0x0, RequestParamsHelper.ORDER_MODEL, RequestParamsHelper.ACT_QORDER_DETAIL, RequestParamsHelper.getOrderDetailParam(arguments.getString(OrderDetailForHavingWorkFragment.ORDER_BEAN_FLAG)))
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        if (requestID == 0x1) {
            getFastProgressDialog("正在提交……")
        }
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        if (requestID == 0x0) {
            val check = checkResultCode(result)
            if (check != null && check.code == SUCCESS_CODE) {
                mTvOrderDetailLoadFail.visibility = View.GONE
                mSvOrderDetailInfo.visibility = View.VISIBLE
                val json = check.obj as JSONObject
                orderBean = Gson().fromJson(json.optJSONObject("result").toString(), OrderBean::class.java)
                setText(mTvOrderDetailForHavingWorkName, orderBean?.qorder_name)
                setText(mTvOrderDetailForHavingWorkStatus, ServiceOrderPresent.OrderStatus.getDescriptionByIndex(orderBean?.qorder_token))
                setText(mTvOrderDetailForHavingWorkPhone, "手机号码：${orderBean?.qorder_phone}")
                setText(mTvOrderDetailForHavingWorkYTime, orderBean?.qorder_ytime)
                setText(mTvOrderDetailForHavingWorkContent, "备注：${orderBean?.qorder_content}")
                setText(mTvOrderDetailForWaitingWorkPrice, "￥${orderBean?.qorder_price}")
                setText(mTvOrderDetailForHavingWorkOrderName, orderBean?.qorder_name)
                setText(mTvOrderDetailForHavingWorkSN, orderBean?.qorder_sn)
                setText(mTvOrderDetailForHavingWorkOrderTime, orderBean?.qorder_time)
                setText(mTvOrderDetailForHavingWorkOrderCompleteTime, orderBean?.qorder_work_finish_time)
                setText(mTvOrderDetailForHavingWorkAllMoney, "总价：￥${orderBean?.qorder_price}")
                setText(mTvOrderDetailForHavingWorkName, orderBean?.qorder_name)
                if (orderBean != null && !orderBean!!.qorder_pic.isEmpty()) {
                    mICllOrderDetailImageContainer.visibility = View.VISIBLE
                    mICllOrderDetailImageContainer.setImages(orderBean!!.qorder_pic)
                } else {
                    mICllOrderDetailImageContainer.visibility = View.GONE
                }
            } else {
                mTvOrderDetailLoadFail.visibility = View.VISIBLE
                mSvOrderDetailInfo.visibility = View.GONE
            }
        }
    }

    fun setText(textView: TextView, text: String?) {
        textView.text = if (TextUtils.isEmpty(text)) {
            "暂无"
        } else {
            text
        }
    }

    override fun onRequestFail(requestID: Int, e: Throwable) {
        super.onRequestFail(requestID, e)
        if (requestID == 0x0) {
            mTvOrderDetailLoadFail.visibility = View.VISIBLE
            mSvOrderDetailInfo.visibility = View.GONE
        } else if (requestID == 0x1) {
            toast("提交失败，请稍后重试……")
        }
    }
}