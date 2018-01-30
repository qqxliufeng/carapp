package com.android.ql.lf.carapp.ui.fragments.order

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.adapter.OrderImageUpLoadAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.1.29.
 * @author lf on 18.1.29
 */
class OrderImageUpLoadFragment : BaseRecyclerViewFragment<String>() {

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun createAdapter(): BaseQuickAdapter<String, BaseViewHolder> =
            OrderImageUpLoadAdapter(R.layout.adapter_order_image_up_load_item_layout, mArrayList)

    override fun initView(view: View?) {
        super.initView(view)
        mRecyclerView.layoutManager = GridLayoutManager(mContext, 2)
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        val itemDecoration = super.getItemDecoration() as DividerItemDecoration
        itemDecoration.setDrawable(ColorDrawable(Color.TRANSPARENT))
        return itemDecoration
    }

    override fun onRefresh() {
        super.onRefresh()
        testAdd("")
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.upload_image_menu, menu)
    }


}