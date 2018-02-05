package com.android.ql.lf.carapp.ui.fragments.community

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.adapter.ArticleListAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.2.5.
 * @author lf on 18.2.5
 */
class ArticleListFragment : BaseRecyclerViewFragment<String>() {

    override fun getLayoutId() = R.layout.fragment_article_list_layout

    override fun createAdapter(): BaseQuickAdapter<String, BaseViewHolder> =
            ArticleListAdapter(android.R.layout.simple_list_item_1, mArrayList)

    override fun initView(view: View?) {
        super.initView(view)
    }

    override fun onRefresh() {
//        super.onRefresh()
        (0..50).forEach {
            mArrayList.add("item --> $it")
        }
        mBaseAdapter.notifyDataSetChanged()
    }

}