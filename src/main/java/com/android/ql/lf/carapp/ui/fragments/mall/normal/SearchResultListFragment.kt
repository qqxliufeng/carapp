package com.android.ql.lf.carapp.ui.fragments.mall.normal

import android.graphics.Color
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.GoodsBean
import com.android.ql.lf.carapp.ui.adapter.GoodsMallItemAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_search_list_layout.*

/**
 * Created by lf on 18.3.20.
 * @author lf on 18.3.20
 */
class SearchResultListFragment : BaseRecyclerViewFragment<GoodsBean>() {

    override fun createAdapter(): BaseQuickAdapter<GoodsBean, BaseViewHolder> = GoodsMallItemAdapter(R.layout.adapter_main_mall_item_layout, mArrayList)

    override fun getLayoutId() = R.layout.fragment_search_list_layout

    override fun initView(view: View?) {
        super.initView(view)
        mIvSearchGoodsBack.setColorFilter(Color.parseColor("#646464"))
        mIvSearchGoodsBack.setOnClickListener {
            finish()
        }
    }

}