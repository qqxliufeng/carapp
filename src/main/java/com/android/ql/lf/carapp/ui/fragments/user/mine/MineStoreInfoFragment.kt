package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.TextView
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.fragments.BaseFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_mine_store_info_layout.*

/**
 * Created by lf on 18.2.2.
 * @author lf on 18.2.2
 */
class MineStoreInfoFragment : BaseFragment() {


    private val mArrayList = arrayListOf<MineStoreInfoBean>()

    override fun getLayoutId() = R.layout.fragment_mine_store_info_layout

    override fun initView(view: View?) {
        mArrayList.add(MineStoreInfoBean(R.drawable.icon_mine_store_info_index, "店铺首页"))
        mArrayList.add(MineStoreInfoBean(R.drawable.icon_mine_store_info_info, "店铺信息"))
        mArrayList.add(MineStoreInfoBean(R.drawable.icon_mine_store_info_upload, "商品上传"))
        val gridLayoutManager = GridLayoutManager(mContext, 2)
        mRvMineStoreInfoContainer.layoutManager = gridLayoutManager
        val adapter: BaseQuickAdapter<MineStoreInfoBean, BaseViewHolder> = object : BaseQuickAdapter<MineStoreInfoBean, BaseViewHolder>(R.layout.adapter_mine_store_info_item_layout, mArrayList) {
            override fun convert(helper: BaseViewHolder?, item: MineStoreInfoBean?) {
                val textView = helper!!.getView<TextView>(R.id.mTvMineStoreInfoItem)
                textView.text = item!!.name
                textView.setCompoundDrawablesWithIntrinsicBounds(0, item!!.resId, 0, 0)
            }
        }
        mRvMineStoreInfoContainer.adapter = adapter
        adapter.addHeaderView(View.inflate(mContext, R.layout.layout_mine_store_info_top_layout, null))
    }

    class MineStoreInfoBean(var resId: Int, var name: String)


}