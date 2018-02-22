package com.android.ql.lf.carapp.ui.adapter

import android.widget.CheckedTextView
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.fragments.message.SystemMessageListFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.1.27.
 * @author lf on 18.1.27
 */
class SystemMessageListAdapter(layoutId: Int, list: ArrayList<SystemMessageListFragment.SystemMessageItem>) : BaseQuickAdapter<SystemMessageListFragment.SystemMessageItem, BaseViewHolder>(layoutId, list) {
    override fun convert(helper: BaseViewHolder?, item: SystemMessageListFragment.SystemMessageItem?) {
        helper!!.setText(R.id.mTvSystemMessageItemTitle, item!!.title)
        helper.setText(R.id.mTvSystemMessageItemDescription, item.description)
        helper.setText(R.id.mTvSystemMessageItemTime, item.time)
        val ctvNotify = helper.getView<CheckedTextView>(R.id.mCtvSystemMessageItemNotify)
        ctvNotify.isChecked = !item.isRead
    }
}