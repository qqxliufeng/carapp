package com.android.ql.lf.carapp.ui.fragments.bottom

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.activities.MainActivity
import com.android.ql.lf.carapp.ui.adapter.GoodsMallItemAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carapp.ui.fragments.mall.normal.*
import com.android.ql.lf.carapp.ui.fragments.mall.order.OrderSubmitFragment
import com.android.ql.lf.carapp.ui.fragments.mall.shoppingcar.ShoppingCarFragment
import com.android.ql.lf.carapp.ui.views.DividerGridItemDecoration
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.youth.banner.Banner
import kotlinx.android.synthetic.main.fragment_main_mall_layout.*

/**
 * Created by lf on 18.1.24.
 * @author lf on 18.1.24
 */
class MainMallFragment : BaseRecyclerViewFragment<String>() {

    companion object {
        fun newInstance(): MainMallFragment {
            return MainMallFragment()
        }
    }

    private val topView by lazy { View.inflate(mContext, R.layout.layout_main_mall_top_header_layout, null) }

    private val bannerView by lazy {
        topView.findViewById<Banner>(R.id.mBannerMainMall)
    }

    override fun getLayoutId() = R.layout.fragment_main_mall_layout

    override fun createAdapter(): BaseQuickAdapter<String, BaseViewHolder> = GoodsMallItemAdapter(R.layout.adapter_main_mall_item_layout, mArrayList)

    override fun onRefresh() {
        super.onRefresh()
        testAdd("11111111")
    }

    override fun initView(view: View?) {
        super.initView(view)
        val height = (mContext as MainActivity).statusHeight
        val param = mTvMainMallTitle.layoutParams as ViewGroup.MarginLayoutParams
        param.topMargin = height
        mTvMainMallTitle.layoutParams = param
        mLlMainMallSearchContainer.setOnClickListener {
            //            FragmentContainerActivity
//                    .from(mContext)
//                    .setTitle("列表页")
//                    .setNeedNetWorking(true)
//                    .setClazz(StoreClassifyFragment::class.java)
//                    .start()
//            FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setHiddenToolBar(true).setClazz(SearchResultListFragment::class.java).start()
            FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setTitle("确认订单").setClazz(OrderSubmitFragment::class.java).start()
        }
        mFabShoppingCar.setImageResource(R.drawable.img_icon_shoppingcart_white_full)
        mFabShoppingCar.setOnClickListener {
            FragmentContainerActivity.from(mContext).setClazz(ShoppingCarFragment::class.java).setTitle("购物车").setNeedNetWorking(true).start()
        }
        mBaseAdapter.addHeaderView(topView)
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        val manager = GridLayoutManager(mContext, 2)
        mManager = manager
        return manager
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        return DividerGridItemDecoration(mContext)
    }
}