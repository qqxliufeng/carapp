package com.android.ql.lf.carapp.ui.fragments.mall.normal

import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.GoodsBean
import com.android.ql.lf.carapp.data.StoreInfoBean
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.adapter.GoodsMallItemAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carapp.utils.GlideManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_store_info_layout.*

/**
 * Created by lf on 18.3.20.
 * @author lf on 18.3.20
 */
class StoreInfoFragment : BaseRecyclerViewFragment<GoodsBean>() {

    companion object {
        val STORE_ID_FLAG = "store_id_flag"
    }

    private val storeInfoBean by lazy {
        arguments.classLoader = this@StoreInfoFragment.javaClass.classLoader
        arguments.getParcelable<StoreInfoBean>(STORE_ID_FLAG)
    }

    override fun createAdapter(): BaseQuickAdapter<GoodsBean, BaseViewHolder> = GoodsMallItemAdapter(R.layout.adapter_main_mall_item_layout, mArrayList)

    override fun getLayoutId() = R.layout.fragment_store_info_layout

    override fun initView(view: View?) {
        super.initView(view)
        val statusHeight = (mContext as FragmentContainerActivity).statusHeight
        mAblStoreInfoContainer.setPadding(0, statusHeight, 0, 0)
        mAblStoreInfoContainer.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            mTlStoreInfoContainer.alpha = 1 - Math.abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange.toFloat()
        }
        mIvSearchGoodsBack.setOnClickListener {
            finish()
        }
        GlideManager.loadImage(mContext, storeInfoBean.wholesale_shop_pic, mIvStoreInfoPic)
        mTvStoreInfoName.text = storeInfoBean.wholesale_shop_name
        mTvStoreInfoFansCount.text = storeInfoBean.wholesale_shop_attention
        mTvStoreInfoFocus.setOnClickListener {
        }
        mTvStoreInfoTopProductClassify.setOnClickListener {
            FragmentContainerActivity
                    .from(mContext)
                    .setNeedNetWorking(true)
                    .setTitle("产品分类")
                    .setClazz(StoreClassifyFragment::class.java)
                    .start()
        }
        mTvStoreInfoProductClassify.setOnClickListener {
            mTvStoreInfoTopProductClassify.performClick()
        }
    }
}