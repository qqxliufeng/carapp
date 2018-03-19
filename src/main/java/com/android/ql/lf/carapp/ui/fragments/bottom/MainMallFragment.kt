package com.android.ql.lf.carapp.ui.fragments.bottom

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.activities.MainActivity
import com.android.ql.lf.carapp.ui.fragments.BaseFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_main_mall_layout.*

/**
 * Created by lf on 18.1.24.
 * @author lf on 18.1.24
 */
class MainMallFragment : BaseFragment() {

    companion object {
        fun newInstance(): MainMallFragment {
            return MainMallFragment()
        }
    }

    override fun getLayoutId() = R.layout.fragment_main_mall_layout

    override fun initView(view: View?) {
        val height = (mContext as MainActivity).statusHeight
        val param = mTvMainMallTitle.layoutParams as ViewGroup.MarginLayoutParams
        param.topMargin = height
        mTvMainMallTitle.layoutParams = param
        val gridLayoutManager = GridLayoutManager(mContext,2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = 1
        }
        id_rv_base_recycler_view.layoutManager = GridLayoutManager(mContext,2)
        id_rv_base_recycler_view.adapter = object : BaseQuickAdapter<String, BaseViewHolder>(android.R.layout.simple_list_item_1,
                arrayListOf("1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2", "1", "2")) {
            override fun convert(helper: BaseViewHolder?, item: String?) {
                helper!!.setText(android.R.id.text1, item)
            }
        }
    }
}