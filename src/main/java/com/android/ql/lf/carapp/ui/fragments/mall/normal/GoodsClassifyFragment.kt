package com.android.ql.lf.carapp.ui.fragments.mall.normal

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.CheckedTextView
import android.widget.ImageView
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.ClassifyBean
import com.android.ql.lf.carapp.data.ClassifyItemEntity
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.utils.GlideManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.listener.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_mall_classify_layout.*

/**
 * Created by lf on 18.3.20.
 * @author lf on 18.3.20
 */
class GoodsClassifyFragment : BaseNetWorkingFragment() {

    private val mMenuArrayList = arrayListOf<ClassifyBean>()
    private val mItemArrayList = arrayListOf<ClassifyItemEntity>()

    private lateinit var menuAdapter: MenuAdapter
    private lateinit var contentAdapter: ContentAdapter

    private lateinit var menuItem: ClassifyBean
    private lateinit var contentItem: ClassifyItemEntity

    override fun getLayoutId() = R.layout.fragment_mall_classify_layout

    override fun initView(view: View?) {
        (0..10).forEach {
            val element = ClassifyBean()
            element.classify_title = "$it"
            element.isChecked = it == 0
            mMenuArrayList.add(element)
        }

        mMenuArrayList.forEach {
            val contentEntity = ClassifyItemEntity(true, it.classify_title)
            mItemArrayList.add(contentEntity)
            (0..10).forEach {
                val classifySubItemBean = ClassifyBean.ClassifySubItemBean()
                classifySubItemBean.classify_title = "goods"
                mItemArrayList.add(ClassifyItemEntity(classifySubItemBean))
            }
        }

        val gridLayoutManager = GridLayoutManager(mContext, 3)
        mRcContent.layoutManager = gridLayoutManager
        contentAdapter = ContentAdapter(R.layout.adapter_search_and_classify_content_item_layout,
                R.layout.adapter_search_and_classify_content_header_item_layout, mItemArrayList)
        mRcContent.adapter = contentAdapter
        mRcContent.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
            }
        })
        mRcMenu.layoutManager = LinearLayoutManager(mContext)
        menuAdapter = MenuAdapter(R.layout.adapter_search_and_classify_menu_item_layout, mMenuArrayList)
        mRcMenu.adapter = menuAdapter
        mRcMenu.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                menuItem = mMenuArrayList[position]
                mMenuArrayList.forEachIndexed { index, searchMenuItemBean ->
                    searchMenuItemBean.isChecked = index == position
                }
                menuAdapter.notifyDataSetChanged()
                val contentItemIndex = mItemArrayList.firstOrNull { it.header != null && it.header == mMenuArrayList[position].classify_title }?.let { mItemArrayList.indexOf(it) } ?: 0
                gridLayoutManager.scrollToPositionWithOffset(contentItemIndex, 0)
            }
        })
        mRcContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val position = gridLayoutManager.findFirstVisibleItemPosition()
                val entity = mItemArrayList[position]
                if (entity.header != null) {
                    var index = 0
                    mMenuArrayList.forEachIndexed { _, searchMenuItemBean ->
                        searchMenuItemBean.isChecked = false
                        if (entity.header == searchMenuItemBean.classify_title) {
                            index = mMenuArrayList.indexOf(searchMenuItemBean)
                        }
                    }
                    mMenuArrayList[index].isChecked = true
                    menuAdapter.notifyDataSetChanged()
                    mRcMenu.smoothScrollToPosition(index)
                } else if (!mRcContent.canScrollVertically(1)) {
                    mMenuArrayList.forEachIndexed { _, searchMenuItemBean ->
                        searchMenuItemBean.isChecked = false
                    }
                    mMenuArrayList[mMenuArrayList.size - 1].isChecked = true
                    menuAdapter.notifyDataSetChanged()
                    mRcMenu.smoothScrollToPosition(mMenuArrayList.size - 1)
                }
            }
        })
    }


    class MenuAdapter(layoutId: Int, list: ArrayList<ClassifyBean>) : BaseQuickAdapter<ClassifyBean, BaseViewHolder>(layoutId, list) {

        override fun convert(helper: BaseViewHolder?, item: ClassifyBean?) {
            helper?.setText(R.id.mSearchClassifyItemName, item?.classify_title)
            val ckName = helper?.getView<CheckedTextView>(R.id.mSearchClassifyItemName)
            ckName?.isChecked = item?.isChecked!!
        }
    }

    class ContentAdapter(layoutId: Int, headerLayoutId: Int, list: ArrayList<ClassifyItemEntity>) : BaseSectionQuickAdapter<ClassifyItemEntity, BaseViewHolder>(layoutId, headerLayoutId, list) {

        override fun convertHead(helper: BaseViewHolder?, item: ClassifyItemEntity?) {
            helper?.setText(R.id.mSearchClassifyContentItemTitle, item!!.header)
        }

        override fun convert(helper: BaseViewHolder?, item: ClassifyItemEntity?) {
            helper!!.setText(R.id.mSearchClassifyContentItemName, item!!.t.classify_title)
//            val iv_icon = helper.getView<ImageView>(R.id.mSearchClassifyContentItemIcon)
//            GlideManager.loadCircleImage(iv_icon.context, item.t.classify_pic, iv_icon)
        }
    }
}