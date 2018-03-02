package com.android.ql.lf.carapp.ui.fragments.message

import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.adapter.SystemMessageListAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import com.android.ql.lf.carapp.utils.RxBus
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.1.27.
 * @author lf on 18.1.27
 */
class SystemMessageListFragment : BaseRecyclerViewFragment<SystemMessageListFragment.SystemMessageItem>() {

    companion object {
        val MESSAGE_TYPE_FLAG = "message_type_flag"
    }

    override fun createAdapter(): BaseQuickAdapter<SystemMessageItem, BaseViewHolder> =
            SystemMessageListAdapter(R.layout.adapter_system_message_list_item_layout, mArrayList)

    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0, RequestParamsHelper.MEMBER_MODEL, RequestParamsHelper.ACT_MY_MSG_DETAIL, RequestParamsHelper.getMyMsgDetailParam(arguments.getString(MESSAGE_TYPE_FLAG), currentPage))
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mPresent.getDataByPost(0x0, RequestParamsHelper.MEMBER_MODEL, RequestParamsHelper.ACT_MY_MSG_DETAIL, RequestParamsHelper.getMyMsgDetailParam(arguments.getString(MESSAGE_TYPE_FLAG), currentPage))
    }

    override fun getEmptyMessage() = "暂无消息"

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        processList(result as String, SystemMessageItem::class.java)
        if (!mArrayList.isEmpty()) {
            mArrayList.forEach {
                it.isRead = it.message_status != "0"
            }
            mBaseAdapter.notifyDataSetChanged()
        }
    }


    override fun onMyItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemClick(adapter, view, position)
        val item = mArrayList[position]
        if (!item.isRead) {
            item.isRead = true
            mBaseAdapter.notifyItemChanged(position)
        }
        if (mArrayList.none { !it.isRead }) {
            RxBus.getDefault().post(MineMessageListFragment.SECOND_LEVEL_ALL_MESSAGE_HAVE_RED_FLAG)
        }
    }

    class SystemMessageItem {
        var message_id: String? = null
        var message_title: String? = null
        var message_content: String? = null
        var message_time: String? = null
        var message_uid: String? = null
        var message_qid: String? = null
        var message_status: String? = null
        var message_token: String? = null
        var message_sym: String? = null
        var isRead: Boolean = false
    }
}