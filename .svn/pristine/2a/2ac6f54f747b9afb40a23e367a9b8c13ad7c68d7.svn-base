package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.os.Bundle
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.adapter.MineEvaluateItemAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by liufeng on 2018/2/25.
 */
class MineEvaluateItemFragment : BaseRecyclerViewFragment<String>(){

    companion object {
        fun newInstance(bundle: Bundle):MineEvaluateItemFragment{
            val mineEvaluateItemFragment = MineEvaluateItemFragment()
            mineEvaluateItemFragment.arguments = bundle
            return mineEvaluateItemFragment
        }
    }

    override fun createAdapter(): BaseQuickAdapter<String, BaseViewHolder> =
            MineEvaluateItemAdapter(R.layout.adapter_mine_evaluate_item_layout,mArrayList)

    override fun onRefresh() {
        super.onRefresh()
        testAdd("")
    }

}