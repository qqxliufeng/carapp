package com.android.ql.lf.carapp.ui.fragments.community

import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_article_search_layout.*

/**
 * Created by lf on 18.2.13.
 * @author lf on 18.2.13
 */
class ArticleSearchFragment : BaseRecyclerViewFragment<String>() {

    override fun getLayoutId() = R.layout.fragment_article_search_layout

    override fun createAdapter(): BaseQuickAdapter<String, BaseViewHolder> = ArticleSearchAdapter(android.R.layout.simple_list_item_1, mArrayList)

    override fun initView(view: View?) {
        mTlArticleSearch.setPadding(0, (mContext as FragmentContainerActivity).statusHeight, 0, 0)
        (mContext as FragmentContainerActivity).setSupportActionBar(mTlArticleSearch)
        (mContext as FragmentContainerActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mTlArticleSearch.setNavigationOnClickListener {
            finish()
        }
        super.initView(view)
        setRefreshEnable(false)
    }

    override fun onRefresh() {
        onRequestEnd(-1)
        testAdd("this is new line ,this is result this is new line ,this is result ")
    }


    class ArticleSearchAdapter(resId: Int, list: ArrayList<String>) : BaseQuickAdapter<String, BaseViewHolder>(resId, list) {
        override fun convert(helper: BaseViewHolder?, item: String?) {
            helper!!.setText(android.R.id.text1, item)
        }
    }
}