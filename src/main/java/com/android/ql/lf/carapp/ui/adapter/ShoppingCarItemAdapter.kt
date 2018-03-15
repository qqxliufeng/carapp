package com.android.ql.lf.carapp.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.ShoppingCarItemBean
import com.android.ql.lf.carapp.utils.GlideManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 2017/11/8 0008.
 * @author lf on 2017/11/8 0008
 */
class ShoppingCarItemAdapter(layoutId: Int, list: ArrayList<ShoppingCarItemBean>) : BaseQuickAdapter<ShoppingCarItemBean, BaseViewHolder>(layoutId, list) {

    override fun convert(helper: BaseViewHolder?, item: ShoppingCarItemBean?) {
        helper!!.addOnClickListener(R.id.mIvShoppingCarItemSelector)
        val ivSelector = helper.getView<ImageView>(R.id.mIvShoppingCarItemSelector)
        val llInfoContainer = helper.getView<LinearLayout>(R.id.mLlShoppingCarItemInfoContainer)
        if (item!!.isSelector) {
            ivSelector?.setImageResource(R.drawable.img_shopping_car_selector_icon)
        } else {
            ivSelector?.setImageResource(R.drawable.img_shopping_car_unselector_icon)
        }

        helper.setText(R.id.mTvShoppingCarItemName, item.shopcart_name)
        val tv_k_type = helper.getView<TextView>(R.id.mTvShoppingCarItemKType)
        helper.setText(R.id.mTvShoppingCarItemPrice, "￥ ${item.shopcart_price}")
        helper.setText(R.id.mTvShoppingCarItemSpe, item.shopcart_specification)
        helper.setText(R.id.mTvShoppingCarItemNum, "X ${item.shopcart_num}")
        val goods_pic = helper.getView<ImageView>(R.id.mTvShoppingCarItemPic)
        if (!item.shopcart_pic.isEmpty()) {
            GlideManager.loadImage(goods_pic.context, item.shopcart_pic[0], goods_pic)
        }
    }
}