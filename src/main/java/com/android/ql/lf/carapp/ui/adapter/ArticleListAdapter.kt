package com.android.ql.lf.carapp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.2.5.
 * @author lf on 18.2.5
 */
class ArticleListAdapter(resId: Int, list: ArrayList<String>) : BaseQuickAdapter<String, BaseViewHolder>(resId, list) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
    }
}