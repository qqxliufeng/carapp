package com.android.ql.lf.carapp.ui.fragments.bottom

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.BannerImageBean
import com.android.ql.lf.carapp.data.GoodsBean
import com.android.ql.lf.carapp.data.UserInfo
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.activities.MainActivity
import com.android.ql.lf.carapp.ui.adapter.GoodsMallItemAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carapp.ui.fragments.mall.normal.*
import com.android.ql.lf.carapp.ui.fragments.mall.shoppingcar.ShoppingCarFragment
import com.android.ql.lf.carapp.ui.fragments.user.LoginFragment
import com.android.ql.lf.carapp.ui.views.DividerGridItemDecoration
import com.android.ql.lf.carapp.ui.views.HotView
import com.android.ql.lf.carapp.utils.GlideManager
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import com.android.ql.lf.carapp.utils.doClickWithUserStatusStart
import com.android.ql.lf.carapp.utils.toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.fragment_main_mall_layout.*
import org.jetbrains.anko.collections.forEachWithIndex
import org.json.JSONObject

/**
 * Created by lf on 18.1.24.
 * @author lf on 18.1.24
 */
class MainMallFragment : BaseRecyclerViewFragment<GoodsBean>() {

    companion object {

        val MAIN_MALL_COLLECTION_FLAG = "main_mall_collection_flag"
        val MAIN_MALL_SHOPPING_CAR_FLAG = "main_mall_shopping_car_flag"

        fun newInstance(): MainMallFragment {
            return MainMallFragment()
        }
    }

    private var productContainer: ProductContainerBean? = null

    private val topView by lazy { View.inflate(mContext, R.layout.layout_main_mall_top_header_layout, null) }

    private val hotViewContainer by lazy {
        arrayListOf<HotView>(
                topView.findViewById(R.id.mHvMainMall1),
                topView.findViewById(R.id.mHvMainMall2),
                topView.findViewById(R.id.mHvMainMall3),
                topView.findViewById(R.id.mHvMainMall4)
        )
    }

    private val bannerView by lazy {
        topView.findViewById<Banner>(R.id.mBannerMainMall)
    }

    override fun getLayoutId() = R.layout.fragment_main_mall_layout

    override fun createAdapter(): BaseQuickAdapter<GoodsBean, BaseViewHolder> = GoodsMallItemAdapter(R.layout.adapter_main_mall_item_layout, mArrayList)

    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0, RequestParamsHelper.PRODUCT_MODEL, RequestParamsHelper.ACT_PRODUCT, RequestParamsHelper.getProductParams(currentPage))
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mPresent.getDataByPost(0x0, RequestParamsHelper.PRODUCT_MODEL, RequestParamsHelper.ACT_PRODUCT, RequestParamsHelper.getProductParams(currentPage))
    }

    override fun initView(view: View?) {
        super.initView(view)
        registerLoginSuccessBus()
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
            FragmentContainerActivity.from(mContext).setNeedNetWorking(true).setTitle("确认订单").setClazz(GoodsInfoFragment::class.java).start()
        }
        mFabShoppingCar.setImageResource(R.drawable.img_icon_shoppingcart_white_full)
        mFabShoppingCar.setOnClickListener {
            FragmentContainerActivity.from(mContext).setClazz(ShoppingCarFragment::class.java).setTitle("购物车").setNeedNetWorking(true).start()
        }
        bannerView!!.setImageLoader(object : ImageLoader() {
            override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                GlideManager.loadImage(mContext, (path as BannerImageBean).lunbo_pic, imageView)
            }
        })
        mBaseAdapter.addHeaderView(topView)
        mBaseAdapter.setHeaderAndEmpty(true)
    }

    override fun onStart() {
        super.onStart()
        bannerView.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        bannerView.startAutoPlay()
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        when (requestID) {
            0x0 -> {
                val check = checkResultCode(result)
                if (check != null) {
                    if (check.code == SUCCESS_CODE) {
                        processList(check.obj as JSONObject, GoodsBean::class.java)
                        if (currentPage == 0) {
                            productContainer = Gson().fromJson(check.obj.toString(), ProductContainerBean::class.java)
                            bannerView!!.setImages(productContainer!!.arr2).setDelayTime(3000).setBannerStyle(BannerConfig.CIRCLE_INDICATOR).start()
                            productContainer!!.arr1.forEachWithIndex { index, item ->
                                hotViewContainer[index].bindData(item.faddish_title, item.faddish_description, item.faddish_pic) {
                                    toast("${item.faddish_id}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        val manager = GridLayoutManager(mContext, 2)
        mManager = manager
        return manager
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        return DividerGridItemDecoration(mContext)
    }

    override fun onMyItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemClick(adapter, view, position)
    }

    override fun onMyItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemChildClick(adapter, view, position)
        when (view!!.id) {
            R.id.mIvGoodsInfoItemCollection -> {
                if (UserInfo.getInstance().isLogin) {
                    //收藏
                } else {
                    UserInfo.loginToken = MAIN_MALL_COLLECTION_FLAG
                    LoginFragment.startLogin(mContext)
                }
            }
            R.id.mTvGoodsInfoItemShoppingCar -> {
                if (UserInfo.getInstance().isLogin) {
                    //加购物车
                } else {
                    UserInfo.loginToken = MAIN_MALL_SHOPPING_CAR_FLAG
                    LoginFragment.startLogin(mContext)
                }
            }
        }
    }

    override fun onLoginSuccess(userInfo: UserInfo?) {
        super.onLoginSuccess(userInfo)
        when (UserInfo.loginToken) {
            MAIN_MALL_COLLECTION_FLAG -> {
                //收藏
            }
            MAIN_MALL_SHOPPING_CAR_FLAG -> {
                //加购物车
            }
        }
        UserInfo.resetLoginSuccessDoActionToken()
    }

    override fun onDestroyView() {
        bannerView.releaseBanner()
        super.onDestroyView()
    }

    class ProductContainerBean {
        lateinit var result: ArrayList<GoodsBean>
        lateinit var arr1: ArrayList<HotGoodsBean>
        lateinit var arr2: ArrayList<BannerImageBean>
    }

    class HotGoodsBean {
        var faddish_id: String? = null
        var faddish_title: String? = null
        var faddish_description: String? = null
        var faddish_pic: String? = null
        var faddish_time: String? = null
    }


}