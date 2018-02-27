package com.android.ql.lf.carapp.ui.adapter

import android.support.v4.content.ContextCompat
import android.text.Html
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.OrderBean
import com.android.ql.lf.carapp.ui.views.EasyCountDownTextureView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.1.25.
 * @author lf on 18.1.25
 */
class OrderListForQDAdapter(layoutId: Int, list: ArrayList<OrderBean>) : BaseQuickAdapter<OrderBean, BaseViewHolder>(layoutId, list) {

    override fun convert(helper: BaseViewHolder?, item: OrderBean?) {
        helper!!.addOnClickListener(R.id.mBtOrderListForQDItem)
        helper.setText(R.id.mTvOrderListForQDItemName, item!!.qorder_name)
        helper.setText(R.id.mTvOrderListForQDItemCountTime, item.qorder_jendtime)
        helper.setText(R.id.mTvOrderListForQDItemTime, item.qorder_time ?: "暂无")
        helper.setText(R.id.mTvOrderListForQDItemTitle, Html.fromHtml("<font color='${ContextCompat.getColor(mContext, R.color.colorPrimary)}'>项目：</font>${item.qorder_project}"))
        helper.setText(R.id.mTvOrderListForQDItemMoney, "￥${item.qorder_price}")
        helper.setText(R.id.mTvOrderListForQDItemContent, Html.fromHtml("<font color='${ContextCompat.getColor(mContext, R.color.colorPrimary)}'>备注：</font>${item.qorder_content}"))
//        val countDownTime = helper.getView<EasyCountDownTextureView>(R.id.mCDTVTime)
//        countDownTime.setTimeMinute(10)
//        countDownTime.setTimeSecond(60)
    }
}