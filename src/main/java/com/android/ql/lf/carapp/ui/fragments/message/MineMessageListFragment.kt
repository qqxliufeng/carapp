package com.android.ql.lf.carapp.ui.fragments.message

import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.adapter.MineMessageListAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.1.27.
 * @author lf on 18.1.27
 */
class MineMessageListFragment : BaseRecyclerViewFragment<String>() {

    override fun createAdapter(): BaseQuickAdapter<String, BaseViewHolder>
            = MineMessageListAdapter(R.layout.adapter_mine_message_list_item_layout, mArrayList)

    override fun onRefresh() {
//        super.onRefresh()
        testAdd("")
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        val itemDecoration = super.getItemDecoration() as DividerItemDecoration
        itemDecoration.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.recycler_view_height_divider))
        return itemDecoration
    }

    override fun onMyItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        FragmentContainerActivity.startFragmentContainerActivity(mContext, "系统消息", true, false, SystemMessageListFragment::class.java)
    }


}