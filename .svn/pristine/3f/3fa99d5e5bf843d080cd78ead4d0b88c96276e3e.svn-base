package com.android.ql.lf.carapp.ui.adapter

import android.support.v4.content.ContextCompat
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import com.android.ql.lf.carapp.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.1.26.
 * @author lf on 18.1.26
 */
class OrderListForMineForWaitingWorkAdapter(layoutId: Int, list: ArrayList<String>) : BaseQuickAdapter<String, BaseViewHolder>(layoutId, list) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        val spannableString = SpannableString("备注：汽车轮胎更换汽车轮胎更换汽车轮胎更换汽车轮胎更换汽车轮胎更换汽车轮胎更换汽车轮胎更换")
        spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorPrimary)), 0, 3, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        helper!!.setText(R.id.mTvWaitingWorkProjectContent, spannableString)
        val tv_project_name = helper.getView<TextView>(R.id.mTvWaitingWorkProjectName)
        tv_project_name.text = Html.fromHtml(
                "<font color='${ContextCompat.getColor(mContext, R.color.colorPrimary)}'>项目：</font>汽车轮" +
                        "" +
                        "" +
                        "" +
                        "胎更换")
        helper.addOnClickListener(R.id.mBtOrderListForWaitingWorkCamera)
    }
}