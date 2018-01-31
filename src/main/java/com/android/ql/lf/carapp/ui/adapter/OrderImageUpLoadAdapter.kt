package com.android.ql.lf.carapp.ui.adapter

import android.net.Uri
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.utils.GlideManager
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.1.29.
 * @author lf on 18.1.29
 */
class OrderImageUpLoadAdapter(layoutId: Int, list: ArrayList<Uri>) : BaseQuickAdapter<Uri, BaseViewHolder>(layoutId, list) {
    override fun convert(helper: BaseViewHolder?, item: Uri?) {
        Glide.with(mContext).load(item).into(helper!!.getView(R.id.mIvUploadImage))
    }
}