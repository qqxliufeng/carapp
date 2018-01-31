package com.android.ql.lf.carapp.ui.fragments.user.mine

import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by liufeng on 2018/1/30.
 */
class MineStoreCollectionFragment : BaseRecyclerViewFragment<String>(){

    override fun createAdapter(): BaseQuickAdapter<String, BaseViewHolder> =
            MineStoreCollectionAdapter(R.layout.adapter_mine_store_collection_item_layout,mArrayList)

    override fun onRefresh() {
//        super.onRefresh()
        testAdd("")
    }


    class MineStoreCollectionAdapter(layout:Int, list: ArrayList<String>)
        : BaseQuickAdapter<String, BaseViewHolder>(layout,list){
        override fun convert(helper: BaseViewHolder?, item: String?) {
        }
    }

}