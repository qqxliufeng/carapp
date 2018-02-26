package com.android.ql.lf.carapp.ui.adapter

import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.views.EasyCountDownTextureView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.1.27.
 * @author lf on 18.1.27
 */
class OrderListForAfterSaleAdapter(layoutId: Int, list: ArrayList<String>) : BaseQuickAdapter<String, BaseViewHolder>(layoutId, list) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        val easyCountTime = helper!!.getView<EasyCountDownTextureView>(R.id.mTestTv)
        easyCountTime.setTimeMinute(10)
        easyCountTime.setTimeSecond(60)
    }
}