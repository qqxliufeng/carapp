package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by liufeng on 2018/2/5.
 */
class MineArticleReplyItemFragment : BaseRecyclerViewFragment<String>() {

    companion object {
        fun newInstance(): MineArticleReplyItemFragment {
            return MineArticleReplyItemFragment()
        }
    }

    override fun createAdapter(): BaseQuickAdapter<String, BaseViewHolder> =
            MineReplyArticleListAdapter(R.layout.adapter_mine_reply_article_item_layout, mArrayList)

    override fun onRefresh() {
        super.onRefresh()
        testAdd("")
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        val itemDecoration = super.getItemDecoration() as DividerItemDecoration
        itemDecoration.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.recycler_view_height_divider))
        return itemDecoration
    }

    override fun onMyItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if (view!!.id == R.id.mBtArticleDelete) {
            val build = AlertDialog.Builder(mContext)
            build.setMessage("是否要删除此回帖？")
            build.setPositiveButton("是") { _, _ ->
            }
            build.setNegativeButton("否", null)
            build.create().show()
        }
    }

    class MineReplyArticleListAdapter(resId: Int, list: ArrayList<String>) : BaseQuickAdapter<String, BaseViewHolder>(resId, list) {
        override fun convert(helper: BaseViewHolder?, item: String?) {
            helper!!.addOnClickListener(R.id.mBtArticleDelete)
        }
    }
}