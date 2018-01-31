package com.android.ql.lf.carapp.ui.adapter

import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.fragments.order.OrderImageUpLoadFragment
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.1.29.
 * @author lf on 18.1.29
 */
class OrderImageUpLoadAdapter(layoutId: Int, list: ArrayList<OrderImageUpLoadFragment.ImageBean>) : BaseQuickAdapter<OrderImageUpLoadFragment.ImageBean, BaseViewHolder>(layoutId, list) {
    override fun convert(helper: BaseViewHolder?, item: OrderImageUpLoadFragment.ImageBean?) {
        if (item!!.resId != null){
            Glide.with(mContext).load(item.resId).into(helper!!.getView(R.id.mIvUploadImage))
        }else {
            Glide.with(mContext).load(item.uriPath).into(helper!!.getView(R.id.mIvUploadImage))
        }
    }
}