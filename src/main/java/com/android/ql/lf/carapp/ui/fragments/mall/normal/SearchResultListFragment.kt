package com.android.ql.lf.carapp.ui.fragments.mall.normal

import android.graphics.Color
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.inputmethod.EditorInfo
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.GoodsBean
import com.android.ql.lf.carapp.data.RefreshData
import com.android.ql.lf.carapp.data.SearchParamBean
import com.android.ql.lf.carapp.data.UserInfo
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.adapter.GoodsMallItemAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carapp.ui.fragments.bottom.MainMallFragment
import com.android.ql.lf.carapp.ui.fragments.mall.shoppingcar.ShoppingCarFragment
import com.android.ql.lf.carapp.ui.fragments.user.LoginFragment
import com.android.ql.lf.carapp.ui.views.DividerGridItemDecoration
import com.android.ql.lf.carapp.utils.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_search_list_layout.*
import org.jetbrains.anko.bundleOf


/**
 * Created by lf on 18.3.20.
 * @author lf on 18.3.20
 */
class SearchResultListFragment : BaseRecyclerViewFragment<GoodsBean>() {

    companion object {
        val SEARCH_PARAM_FLAG = "search_param_flag"

        val RESULT_MALL_MY_SHOPPING_CAR_FLAG = "result_mall_my_shopping_car_flag"


    }

    private val searchParamBean by lazy {
        arguments.classLoader = this@SearchResultListFragment.javaClass.classLoader
        arguments.getParcelable<SearchParamBean>(SEARCH_PARAM_FLAG)
    }

    private val postParam by lazy {
        val apiParam = RequestParamsHelper.getWithPageParams(currentPage)
        searchParamBean.params.forEach {
            apiParam.put(it.key, it.value)
        }
        apiParam
    }

    private var sort = ""
    private var sortFlag = true
    private var saleFlag = true

    private var keyword = ""

    private var tempGoodsBean: GoodsBean? = null

    private val collectionSubscription by lazy {
        RxBus.getDefault().toObservable(RefreshData::class.java).subscribe {
            if (it.isRefresh && it.any == MainMallFragment.REFRESH_COLLECTION_STATUS_FLAG) {
                refreshCollectionStatus()
            }
        }
    }

    override fun createAdapter(): BaseQuickAdapter<GoodsBean, BaseViewHolder> = GoodsMallItemAdapter(R.layout.adapter_main_mall_item_layout, mArrayList)

    override fun getLayoutId() = R.layout.fragment_search_list_layout

    override fun initView(view: View?) {
        super.initView(view)
        val statusHeight = (mContext as FragmentContainerActivity).statusHeight
        mAlGoodsSearchContainer.setPadding(0, statusHeight, 0, 0)
        mAlGoodsSearchContainer.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            mTlGoodsSearchContainer.alpha = 1 - Math.abs(verticalOffset).toFloat() / appBarLayout.totalScrollRange.toFloat()
        }
        collectionSubscription
        mIvSearchGoodsBack.setColorFilter(Color.parseColor("#646464"))
        mIvSearchGoodsBack.setOnClickListener {
            finish()
        }
        mRbSearchListResultSort1.isChecked = true
        mRbSearchListResultSort1.setOnClickListener {
            if (sort == "") {
                return@setOnClickListener
            }
            sort = ""
            onPostRefresh()
        }
        mRbSearchListResultSort2.setOnClickListener {
            sort = if (saleFlag) {
                "sv1"
            } else {
                "sv2"
            }
            saleFlag = !saleFlag
            onPostRefresh()
        }
        mRbSearchListResultSort3.setOnClickListener {
            sort = if (sortFlag) {
                mRbSearchListResultSort3.text = "价格从高到低"
                "p1"
            } else {
                mRbSearchListResultSort3.text = "价格从低到高"
                "p2"
            }
            sortFlag = !sortFlag
            onPostRefresh()
        }
        mFabGoodsSearch.setImageResource(R.drawable.img_icon_shoppingcart_white_full)
        mFabGoodsSearch.doClickWithUserStatusStart(RESULT_MALL_MY_SHOPPING_CAR_FLAG) {
            FragmentContainerActivity.from(mContext).setClazz(ShoppingCarFragment::class.java).setTitle("购物车").setNeedNetWorking(true).start()
        }

        mEtSearchContent.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mTvSearchSubmit.performClick()
            }
            false
        }

        mTvSearchSubmit.setOnClickListener {
            mContext.hiddenKeyBoard(mEtSearchContent.windowToken)
            keyword = if (mEtSearchContent.isEmpty()) {
                ""
            } else {
                mEtSearchContent.getTextString()
            }
            onPostRefresh()
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

    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0, RequestParamsHelper.PRODUCT_MODEL,RequestParamsHelper.ACT_PRODUCT_SEARCH, postParam
                .addParam("page", currentPage)
                .addParam("sort", sort)
                .addParam("keyword", keyword))
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mPresent.getDataByPost(0x0, searchParamBean.model, searchParamBean.act, postParam.addParam("page", currentPage).addParam("sort", sort))
    }

    override fun getEmptyMessage() = "暂没有商品~~~"

    private fun refreshCollectionStatus() {
        if (tempGoodsBean != null) {
            if (tempGoodsBean!!.product_collect == "0") {
                tempGoodsBean!!.product_collect = "1"
            } else {
                tempGoodsBean!!.product_collect = "0"
            }
            mBaseAdapter.notifyItemChanged(mArrayList.indexOf(tempGoodsBean))
        }
    }

    /**
     * 进入商品详情
     */
    private fun enterGoodsInfo(goodsBean: GoodsBean) {
        FragmentContainerActivity.from(mContext)
                .setNeedNetWorking(true)
                .setTitle("商品详情")
                .setExtraBundle(bundleOf(Pair(GoodsInfoFragment.GOODS_ID_FLAG, goodsBean.product_id)))
                .setClazz(GoodsInfoFragment::class.java)
                .start()
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        processList(result as String, GoodsBean::class.java)
    }

    override fun onMyItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        tempGoodsBean = mArrayList[position]
        if (UserInfo.getInstance().isLogin) {
            enterGoodsInfo(tempGoodsBean!!)
        } else {
            UserInfo.loginToken = MainMallFragment.MAIN_MALL_ENTER_GOODS_INFO_FLAG
            LoginFragment.startLogin(mContext)
        }
    }

    override fun onDestroyView() {
        unsubscribe(collectionSubscription)
        super.onDestroyView()
    }

}