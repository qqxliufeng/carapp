package com.android.ql.lf.carapp.ui.fragments.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.ui.views.PopupWindowDialog
import kotlinx.android.synthetic.main.fragment_article_info_layout.*

/**
 * Created by lf on 18.2.5.
 * @author lf on 18.2.5
 */
class ArticleInfoFragment : BaseNetWorkingFragment() {

    override fun getLayoutId() = R.layout.fragment_article_info_layout

    override fun initView(view: View?) {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (mContext as FragmentContainerActivity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
//        SoftKeyBoardListener.setListener(mContext as FragmentContainerActivity, object : SoftKeyBoardListener.OnSoftKeyBoardChangeListener {
//            override fun keyBoardHide(height: Int) {
//            }
//
//            override fun keyBoardShow(height: Int) {
//            }
//        })

        mEtTest.setOnClickListener {
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