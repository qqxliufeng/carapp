package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.TextView
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.fragments.BaseFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.listener.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_mine_store_info_layout.*

/**
 * Created by lf on 18.2.2.
 * @author lf on 18.2.2
 */
class MineStoreInfoFragment : BaseFragment() {


    private val mArrayList = arrayListOf<MineStoreIndexInfoBean>()

    override fun getLayoutId() = R.layout.fragment_mine_store_info_layout

    override fun initView(view: View?) {
        mArrayList.add(MineStoreIndexInfoBean(0, R.drawable.icon_mine_store_info_index, "店铺首页"))
        mArrayList.add(MineStoreIndexInfoBean(1, R.drawable.icon_mine_store_info_info, "店铺信息"))
        mArrayList.add(MineStoreIndexInfoBean(2, R.drawable.icon_mine_store_info_upload, "商品上传"))
        val gridLayoutManager = GridLayoutManager(mContext, 2)
        mRvMineStoreInfoContainer.layoutManager = gridLayoutManager
        val adapter: BaseQuickAdapter<MineStoreIndexInfoBean, BaseViewHolder> = object : BaseQuickAdapter<MineStoreIndexInfoBean, BaseViewHolder>(R.layout.adapter_mine_store_info_item_layout, mArrayList) {
            override fun convert(helper: BaseViewHolder?, item: MineStoreIndexInfoBean?) {
                val textView = helper!!.getView<TextView>(R.id.mTvMineStoreInfoItem)
                textView.text = item!!.name
                textView.setCompoundDrawablesWithIntrinsicBounds(0, item!!.resId, 0, 0)
            }
        }
        mRvMineStoreInfoContainer.adapter = adapter
        adapter.addHeaderView(View.inflate(mContext, R.layout.layout_mine_store_info_top_layout, null))
        mRvMineStoreInfoContainer.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                when (mArrayList[position].index) {
                    0 -> FragmentContainerActivity.from(mContext).setTitle("店铺首页")
                    1 -> FragmentContainerActivity.from(mContext).setTitle("店铺信息").setNeedNetWorking(true).setClazz(MineStoreDetailFragment::class.java).start()
                    2 -> FragmentContainerActivity.from(mContext).setTitle("商品上传")
                }
            }
        })
    }

    class MineStoreIndexInfoBean(var index:Int, var resId: Int, var name: String)


}