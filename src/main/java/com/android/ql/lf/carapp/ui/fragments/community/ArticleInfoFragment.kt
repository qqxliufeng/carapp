package com.android.ql.lf.carapp.ui.fragments.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.adapter.ArticleCommentListAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carapp.ui.views.PopupWindowDialog
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_article_info_layout.*

/**
 * Created by lf on 18.2.5.
 * @author lf on 18.2.5
 */
class ArticleInfoFragment : BaseRecyclerViewFragment<String>() {

    override fun getLayoutId() = R.layout.fragment_article_info_layout

    override fun createAdapter(): BaseQuickAdapter<String, BaseViewHolder> =
            ArticleCommentListAdapter(R.layout.adapter_article_comment_item_layout, mArrayList)

    override fun initView(view: View?) {
        super.initView(view)
        mBaseAdapter.addHeaderView(View.inflate(mContext, R.layout.layout_article_info_top_layout, null))
        mBaseAdapter.setHeaderAndEmpty(true)
        mBaseAdapter.setEmptyView(emptyLayoutId)
        emptyMessage = emptyMessage
        mBaseAdapter.notifyDataSetChanged()
    }

    override fun getEmptyMessage() = "暂没有评价哦~~"

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (mContext as FragmentContainerActivity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        mTvArticleInfoReply.setOnClickListener {
            showReplyDialog()
        }
    }

    private fun showReplyDialog() {
        val contentView = LayoutInflater.from(context).inflate(R.layout.layout_repay_layout, null)
        val et_content = contentView.findViewById<EditText>(R.id.mEtReplyContent)
        val bt_send = contentView.findViewById<Button>(R.id.mBtReplaySend)
        val popupWindow = PopupWindowDialog.showReplyDialog(mContext, contentView)
    }

}