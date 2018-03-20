package com.android.ql.lf.carapp.ui.fragments.mall.normal

import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carapp.ui.views.DividerGridItemDecoration
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.SectionEntity

/**
 * Created by lf on 18.3.20.
 * @author lf on 18.3.20
 */
class StoreClassifyFragment : BaseRecyclerViewFragment<StoreClassifyFragment.StoreClassifyItemEntity>() {

    override fun createAdapter(): BaseQuickAdapter<StoreClassifyItemEntity, BaseViewHolder> {
        return object : BaseSectionQuickAdapter<StoreClassifyItemEntity, BaseViewHolder>(
                R.layout.adapter_store_classify_content_layout,
                R.layout.adapter_store_classify_header_layout,
                mArrayList) {
            override fun convert(helper: BaseViewHolder?, item: StoreClassifyItemEntity?) {
            }

            override fun convertHead(helper: BaseViewHolder?, item: StoreClassifyItemEntity?) {
            }
        }
    }

    override fun initView(view: View?) {
        super.initView(view)
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager = GridLayoutManager(mContext, 2)

    override fun onRefresh() {
        super.onRefresh()
        (0..10).forEach {
            mArrayList.add(StoreClassifyItemEntity(true, "title"))
            (0..5).forEach {
                mArrayList.add(StoreClassifyItemEntity("content$it"))
            }
        }
        mBaseAdapter.notifyDataSetChanged()
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        val decoration = super.getItemDecoration() as DividerItemDecoration
        decoration.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.recycler_view_height_divider2))
        return decoration
    }

    override fun onLoadMore() {
        super.onLoadMore()
        setLoadEnable(false)
    }

    class StoreClassifyItemEntity : SectionEntity<String> {
        constructor(content: String) : super(content)
        constructor(isHeader: Boolean, header: String) : super(isHeader, header)
    }
}