package com.android.ql.lf.carapp.ui.fragments.message

import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.adapter.SystemMessageListAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carapp.utils.RxBus
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.1.27.
 * @author lf on 18.1.27
 */
class SystemMessageListFragment :BaseRecyclerViewFragment<SystemMessageListFragment.SystemMessageItem>() {

    override fun createAdapter(): BaseQuickAdapter<SystemMessageItem, BaseViewHolder> =
            SystemMessageListAdapter(R.layout.adapter_system_message_list_item_layout,mArrayList)

    override fun onRefresh() {
        (0 .. 1).forEach {
            mArrayList.add(SystemMessageItem("this is title","this is description","2018-10-10",false))
        }
        mBaseAdapter.notifyDataSetChanged()
        mBaseAdapter.disableLoadMoreIfNotFullPage()
        onRequestEnd(1)
    }

    override fun onMyItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemClick(adapter, view, position)
        val item = mArrayList[position]
        if (!item.isRead) {
            item.isRead = true
            mBaseAdapter.notifyItemChanged(position)
        }
        if(mArrayList.none { !it.isRead }){
            RxBus.getDefault().post(MineMessageListFragment.SECOND_LEVEL_ALL_MESSAGE_HAVE_RED_FLAG)
        }
    }

    data class SystemMessageItem(var title:String,var description:String,var time:String,var isRead:Boolean)
}