package com.android.ql.lf.carapp.ui.fragments.mall.normal

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.adapter.GoodsCommentAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.ui.views.BottomGoodsParamDialog
import kotlinx.android.synthetic.main.fragment_goods_info_layout.*

/**
 * Created by lf on 18.3.21.
 * @author lf on 18.3.21
 */
class GoodsInfoFragment : BaseNetWorkingFragment() {

    private val mArrayList: ArrayList<String> = arrayListOf()

    private var paramsDialog: BottomGoodsParamDialog? = null

    override fun getLayoutId() = R.layout.fragment_goods_info_layout

    private val commentAdapter by lazy {
        GoodsCommentAdapter(R.layout.adapter_goods_comment_item_layout, mArrayList)
    }

    private val footView by lazy {
        View.inflate(mContext, R.layout.layout_goods_info_foot_view_layout, null)
    }

    @SuppressLint("RestrictedApi")
    override fun initView(view: View?) {
        mCibGoodsInfoCollection.setOnClickListener {
            mCibGoodsInfoCollection.toggle()
        }
        mRvGoodsInfo.layoutManager = LinearLayoutManager(mContext)
        mRvGoodsInfo.adapter = commentAdapter
        commentAdapter.addFooterView(footView)
        mTvGoodsInfoBuy.setOnClickListener {
            if (paramsDialog == null) {
                paramsDialog = BottomGoodsParamDialog(mContext)
                paramsDialog!!.show()
            } else {
                paramsDialog!!.show()
            }
        }
    }
}
