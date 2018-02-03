package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.SectionEntity

/**
 * Created by lf on 18.2.3.
 * @author lf on 18.2.3
 */
class MineFootPrintFragment : BaseRecyclerViewFragment<MineFootPrintFragment.MyFootPrintBean>() {

    override fun createAdapter(): BaseQuickAdapter<MyFootPrintBean, BaseViewHolder>
            = MineFootPrintAdapter(R.layout.adapter_mine_foot_print_item_layout, android.R.layout.simple_list_item_1, mArrayList)


    override fun initView(view: View?) {
        super.initView(view)
    }

    override fun onRefresh() {
        (0..20).forEach {
            if (it % 5 == 0) {
                mArrayList.add(MyFootPrintBean(true, "this is header $it"))
            } else {
                mArrayList.add(MyFootPrintBean(""))
            }
        }
        mBaseAdapter.notifyDataSetChanged()
    }

    class MineFootPrintAdapter(resId: Int, headerResId: Int, list: ArrayList<MyFootPrintBean>) : BaseSectionQuickAdapter<MyFootPrintBean, BaseViewHolder>(resId, headerResId, list) {

        override fun convert(helper: BaseViewHolder?, item: MyFootPrintBean?) {
        }

        override fun convertHead(helper: BaseViewHolder?, item: MyFootPrintBean?) {
            helper!!.setText(android.R.id.text1, item!!.header)
        }
    }

    class MyFootPrintBean : SectionEntity<String> {
        constructor(isHeader: Boolean, header: String?) : super(isHeader, header)
        constructor(t: String?) : super(t)
    }

}