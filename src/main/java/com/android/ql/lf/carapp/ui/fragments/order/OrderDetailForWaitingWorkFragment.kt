package com.android.ql.lf.carapp.ui.fragments.order

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.OrderBean
import com.android.ql.lf.carapp.present.ServiceOrderPresent
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import com.android.ql.lf.carapp.utils.toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_order_detail_for_waiting_work_layout.*
import org.jetbrains.anko.bundleOf
import org.json.JSONObject

/**
 * Created by lf on 18.1.27.
 * @author lf on 18.1.27
 */
class OrderDetailForWaitingWorkFragment : BaseNetWorkingFragment() {

    companion object {
        val ORDER_BEAN_FLAG = "order_bean_flag"
    }

    private val serviceOrderPresent by lazy {
        ServiceOrderPresent()
    }

    private var orderBean: OrderBean? = null

    override fun getLayoutId() =
            R.layout.fragment_order_detail_for_waiting_work_layout

    override fun initView(view: View?) {
        mTvOrderDetailLoadFail.setOnClickListener {
            mPresent.getDataByPost(0x0, RequestParamsHelper.ORDER_MODEL, RequestParamsHelper.ACT_QORDER_DETAIL, RequestParamsHelper.getOrderDetailParam(arguments.getString(ORDER_BEAN_FLAG)))
        }
        mPresent.getDataByPost(0x0, RequestParamsHelper.ORDER_MODEL, RequestParamsHelper.ACT_QORDER_DETAIL, RequestParamsHelper.getOrderDetailParam(arguments.getString(ORDER_BEAN_FLAG)))
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
                setText(mTvOrderDetailForWaitingCode, orderBean?.qorder_code)
                setText(mTvOrderDetailForWaitingName, orderBean?.qorder_name)
                setText(mTvOrderDetailForWaitingStatus, ServiceOrderPresent.OrderStatus.getDescriptionByIndex(orderBean?.qorder_token))
                setText(mTvOrderDetailForWaitingPhone, "手机号码：${orderBean?.qorder_phone}")
                setText(mTvOrderDetailForWaitingYTime, orderBean?.qorder_ytime)
                setText(mTvOrderDetailForWaitingContent, "备注：${orderBean?.qorder_content}")
                setText(mTvOrderDetailForWaitingWorkPrice, "￥${orderBean?.qorder_price}")
                setText(mTvOrderDetailForWaitingOrderName, orderBean?.qorder_name)
                setText(mTvOrderDetailForWaitingOrderSN, orderBean?.qorder_sn)
                setText(mTvOrderDetailForWaitingOrderTime, orderBean?.qorder_time)
                setText(mTvOrderDetailForWaitingOrderAllCount, "总价：${orderBean?.qorder_price}")
                mBtOrderDetailForWaitingWorkTakePhoto.setOnClickListener {
                    if (orderBean != null) {
                        FragmentContainerActivity
                                .from(mContext)
                                .setTitle("拍照")
                                .setNeedNetWorking(true)
                                .setClazz(OrderImageUpLoadFragment::class.java)
                                .setExtraBundle(bundleOf(Pair("oid", orderBean!!.qorder_id)))
                                .start()
                    }
                }
                mBtOrderDetailForWaitingWorkSubmit.setOnClickListener {
                    if (orderBean != null) {
                        mPresent.getDataByPost(0x1,
                                RequestParamsHelper.ORDER_MODEL,
                                RequestParamsHelper.ACT_EDIT_QORDER_STATUS,
                                RequestParamsHelper.getEditQorderStatusParam(orderBean!!.qorder_id, ServiceOrderPresent.OrderStatus.WAITING_CONFIRM.index))
                    }
                }
            } else {
                mTvOrderDetailLoadFail.visibility = View.VISIBLE
                mSvOrderDetailInfo.visibility = View.GONE
            }
        } else if (requestID == 0x1) {
            val check = checkResultCode(result)
            if (check != null) {
                when {
                    check.code == SUCCESS_CODE -> {
                        toast("确认完成")
                        serviceOrderPresent.updateOrderStatus(ServiceOrderPresent.OrderStatus.WAITING_WORK.index.toInt())
                        serviceOrderPresent.updateOrderStatus(ServiceOrderPresent.OrderStatus.HAVING_WORK.index.toInt())
                        finish()
                    }
                    check.code == "400" -> toast((check.obj as JSONObject).optString("msg"))
                    else -> toast("确认失败，请稍后重试……")
                }
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