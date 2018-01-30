package com.android.ql.lf.carapp.ui.fragments.message

import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.adapter.SystemMessageListAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.1.27.
 * @author lf on 18.1.27
 */
class SystemMessageListFragment :BaseRecyclerViewFragment<String>() {

    override fun createAdapter(): BaseQuickAdapter<String, BaseViewHolder> =
            SystemMessageListAdapter(R.layout.adapter_system_message_list_item_layout,mArrayList)

    override fun onRefresh() {
//        super.onRefresh()
        testAdd("")
    }

}