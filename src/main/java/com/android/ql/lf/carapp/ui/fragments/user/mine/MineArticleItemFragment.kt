package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.os.Bundle
import com.android.ql.lf.carapp.ui.adapter.ArticleListAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by liufeng on 2018/2/5.
 */
class MineArticleItemFragment :BaseRecyclerViewFragment<String>(){

    companion object {
        fun newInstance(bundle:Bundle):MineArticleItemFragment{
            val fragment = MineArticleItemFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun createAdapter(): BaseQuickAdapter<String, BaseViewHolder> =
            ArticleListAdapter(android.R.layout.simple_list_item_1, mArrayList)

    override fun onRefresh() {
        super.onRefresh()
        testAdd("")
    }

}