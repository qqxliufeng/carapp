package com.android.ql.lf.carapp.ui.fragments.mall.normal

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.inputmethod.EditorInfo
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.GoodsBean
import com.android.ql.lf.carapp.data.StoreInfoBean
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.adapter.GoodsMallItemAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carapp.ui.views.DividerGridItemDecoration
import com.android.ql.lf.carapp.utils.GlideManager
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import com.android.ql.lf.carapp.utils.toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_store_info_layout.*
import org.jetbrains.anko.bundleOf
import org.json.JSONObject

/**
 * Created by lf on 18.3.20.
 * @author lf on 18.3.20
 */
class StoreInfoFragment : BaseRecyclerViewFragment<GoodsBean>() {

    companion object {
        val STORE_ID_FLAG = "store_id_flag"
    }

    private var collectStatus = 0

    private val storeInfoBean by lazy {
        arguments.classLoader = this@StoreInfoFragment.javaClass.classLoader
        arguments.getParcelable<StoreInfoBean>(STORE_ID_FLAG)
    }

    override fun createAdapter(): BaseQuickAdapter<GoodsBean, BaseViewHolder> = GoodsMallItemAdapter(R.layout.adapter_main_mall_item_layout, mArrayList)

    override fun getLayoutId() = R.layout.fragment_store_info_layout

    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0,
                RequestParamsHelper.PRODUCT_MODEL,
                RequestParamsHelper.ACT_PRODUCT_SHOP,
                RequestParamsHelper.getProductShopParams(storeInfoBean.wholesale_shop_id, "", currentPage))
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mPresent.getDataByPost(0x0,
                RequestParamsHelper.PRODUCT_MODEL,
                RequestParamsHelper.ACT_PRODUCT_SHOP,
                RequestParamsHelper.getProductShopParams(storeInfoBean.wholesale_shop_id, "", currentPage))
    }

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
        if (storeInfoBean.wholesale_shop_pic != null && !storeInfoBean.wholesale_shop_pic.isEmpty()) {
            GlideManager.loadImage(mContext, storeInfoBean.wholesale_shop_pic[0], mIvStoreInfoPic)
        }
        mTvStoreInfoName.text = storeInfoBean.wholesale_shop_name
        mTvStoreInfoFansCount.text = storeInfoBean.wholesale_shop_attention
        mTvStoreInfoFocus.setOnClickListener {
            mPresent.getDataByPost(0x1, RequestParamsHelper.PRODUCT_MODEL, RequestParamsHelper.ACT_CONCERM_SHOP, RequestParamsHelper.getConcermShopParams(storeInfoBean!!.wholesale_shop_id))
        }
        mTvStoreInfoTopProductClassify.setOnClickListener {
            FragmentContainerActivity
                    .from(mContext)
                    .setNeedNetWorking(true)
                    .setTitle("产品分类")
                    .setExtraBundle(bundleOf(Pair(StoreClassifyFragment.SID_FLAG, storeInfoBean.wholesale_shop_id)))
                    .setClazz(StoreClassifyFragment::class.java)
                    .start()
        }
        mTvStoreInfoProductClassify.setOnClickListener {
            mTvStoreInfoTopProductClassify.performClick()
        }
        mTvStoreInfoCollection.setOnClickListener {
        }
        mEtSearchContent.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

            }
            false
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

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        when (requestID) {
            0x1 -> getFastProgressDialog("正在关注……")
        }
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        when (requestID) {
            0x0 -> {
                processList(result as String, GoodsBean::class.java)
                val check = checkResultCode(result)
                if (check != null && check.code == SUCCESS_CODE) {
                    collectStatus = (check.obj as JSONObject).optInt("arr")
                    setFocusText()
                }
            }
            0x1 -> {
                val check = checkResultCode(result)
                if (check != null) {
                    if (check.code == SUCCESS_CODE) {
                        collectStatus = if (collectStatus == 0) {
                            1
                        } else {
                            0
                        }
                        setFocusText()
                    }
                    toast((check.obj as JSONObject).optString(MSG_FLAG))
                }
            }
        }
    }

    private fun setFocusText() {
        mTvStoreInfoFocus.text = if (collectStatus == 0) {
            "+ 关注"
        } else {
            "已关注"
        }
    }
}