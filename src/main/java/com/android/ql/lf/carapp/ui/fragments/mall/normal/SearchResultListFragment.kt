package com.android.ql.lf.carapp.ui.fragments.mall.normal

import android.graphics.Color
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.GoodsBean
import com.android.ql.lf.carapp.data.SearchParamBean
import com.android.ql.lf.carapp.ui.adapter.GoodsMallItemAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carapp.ui.views.DividerGridItemDecoration
import com.android.ql.lf.carapp.utils.ApiParams
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import com.android.ql.lf.carapp.utils.getTextString
import com.android.ql.lf.carapp.utils.isEmpty
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_search_list_layout.*

/**
 * Created by lf on 18.3.20.
 * @author lf on 18.3.20
 */
class SearchResultListFragment : BaseRecyclerViewFragment<GoodsBean>() {

    companion object {
        val SEARCH_PARAM_FLAG = "search_param_flag"
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

    override fun createAdapter(): BaseQuickAdapter<GoodsBean, BaseViewHolder> = GoodsMallItemAdapter(R.layout.adapter_main_mall_item_layout, mArrayList)

    override fun getLayoutId() = R.layout.fragment_search_list_layout

    override fun initView(view: View?) {
        super.initView(view)
        mIvSearchGoodsBack.setColorFilter(Color.parseColor("#646464"))
        mIvSearchGoodsBack.setOnClickListener {
            finish()
        }
        mRbSearchListResultSort1.setOnClickListener {
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
                "p1"
            } else {
                "p2"
            }
            sortFlag = !sortFlag
            onPostRefresh()
        }
        mTvSearchSubmit.setOnClickListener {
            keyword = if (mEtSearchContent.isEmpty()){""}else{mEtSearchContent.getTextString()}
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
        mPresent.getDataByPost(0x0, searchParamBean.model, searchParamBean.act, postParam
                .addParam("page", currentPage)
                .addParam("sort", sort)
                .addParam("keyword", keyword))
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mPresent.getDataByPost(0x0, searchParamBean.model, searchParamBean.act, postParam.addParam("page", currentPage).addParam("sort", sort))
    }

    override fun getEmptyMessage() = "暂没有商品~~~"

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        processList(result as String, GoodsBean::class.java)
    }

}