package com.android.ql.lf.carapp.ui.fragments.message

import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.adapter.MineMessageListAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carapp.ui.fragments.bottom.MainOrderHouseFragment
import com.android.ql.lf.carapp.utils.RxBus
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.1.27.
 * @author lf on 18.1.27
 */
class MineMessageListFragment : BaseRecyclerViewFragment<MineMessageListFragment.MineMessageItem>() {

    companion object {
        val SECOND_LEVEL_ALL_MESSAGE_HAVE_RED_FLAG = "second level all message have read"
    }

    private var currentMessageItem: MineMessageItem? = null

    private val iconList = listOf(
            R.drawable.img_icon_message_for_system,
            R.drawable.img_icon_message_for_fix,
            /*R.drawable.img_icon_message_for_house,
            R.drawable.img_icon_message_for_shop,*/
            R.drawable.img_icon_message_for_community)
    private val titleList = listOf("系统消息", "维修订单", /*"商铺订单", "购物订单",*/ "帖子评论")

    override fun createAdapter(): BaseQuickAdapter<MineMessageItem, BaseViewHolder>
            = MineMessageListAdapter(R.layout.adapter_mine_message_list_item_layout, mArrayList)

    override fun initView(view: View?) {
        super.initView(view)
        subscription = RxBus.getDefault().toObservable(String::class.java).subscribe {
            if (SECOND_LEVEL_ALL_MESSAGE_HAVE_RED_FLAG == it) {
                if (currentMessageItem != null) {
                    if (!currentMessageItem!!.isRead) {
                        currentMessageItem!!.isRead = true
                        mBaseAdapter.notifyItemChanged(mArrayList.indexOf(currentMessageItem))
                    }
                }
                if (mArrayList.none { !it.isRead }) {
                    RxBus.getDefault().post(MainOrderHouseFragment.FIRST_LEVEL_ALL_MESSAGE_HAVE_READ_FLAG)
                }
            }
        }
        setRefreshEnable(false)
    }

    override fun onRefresh() {
        super.onRefresh()
        onRequestEnd(1)
        iconList.forEachIndexed { index, i ->
            mArrayList.add(MineMessageItem(i, titleList[index], "暂无消息", false))
        }
        mBaseAdapter.notifyDataSetChanged()
        setLoadEnable(false)
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        val itemDecoration = super.getItemDecoration() as DividerItemDecoration
        itemDecoration.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.recycler_view_height_divider))
        return itemDecoration
    }

    override fun onMyItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        currentMessageItem = mArrayList[position]
        FragmentContainerActivity
                .from(mContext)
                .setTitle("系统消息")
                .setNeedNetWorking(true)
                .setClazz(SystemMessageListFragment::class.java)
                .start()
    }

    data class MineMessageItem(
            var icon: Int,
            var title: String,
            var description: String,
            var isRead: Boolean)
}