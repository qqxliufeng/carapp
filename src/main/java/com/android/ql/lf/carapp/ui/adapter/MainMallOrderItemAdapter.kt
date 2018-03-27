package com.android.ql.lf.carapp.ui.adapter

import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.MallSaleOrderBean
import com.android.ql.lf.carapp.utils.GlideManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.3.27.
 * @author lf on 18.3.27
 */
class MainMallOrderItemAdapter(layoutId: Int, list: ArrayList<MallSaleOrderBean>) : BaseQuickAdapter<MallSaleOrderBean, BaseViewHolder>(layoutId, list) {

    override fun convert(helper: BaseViewHolder?, item: MallSaleOrderBean?) {
//        GlideManager.loadImage(mContext,item!!.)
        if (!item!!.product_pic.isEmpty()) {
            GlideManager.loadImage(mContext, item.product_pic[0], helper!!.getView(R.id.mIvOrderListItemPic))
        }
        helper!!.setText(R.id.mTvOrderListItemTitle, item.product_name)
        helper.setText(R.id.mTvOrderListItemSpecification, item.order_specification)
        helper.setText(R.id.mIvOrderListItemNum, "X${item.order_num}")
        helper.setText(R.id.mTvOrderListItemPrice, "￥${item.product_price}")
    }
}