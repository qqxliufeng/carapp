package com.android.ql.lf.carapp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.1.25.
 * @author lf on 18.1.25
 */
class OrderListForQDAdapter(layoutId: Int, list: ArrayList<String>) : BaseQuickAdapter<String, BaseViewHolder>(layoutId, list) {

    override fun convert(helper: BaseViewHolder?, item: String?) {
    }
}