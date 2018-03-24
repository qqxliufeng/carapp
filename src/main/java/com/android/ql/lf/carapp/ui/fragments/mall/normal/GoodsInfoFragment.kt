package com.android.ql.lf.carapp.ui.fragments.mall.normal

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.text.TextPaint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.AdsBean
import com.android.ql.lf.carapp.data.GoodsBean
import com.android.ql.lf.carapp.data.StoreInfoBean
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.adapter.GoodsCommentAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.ui.views.BottomGoodsParamDialog
import com.android.ql.lf.carapp.ui.views.HtmlTextView
import com.android.ql.lf.carapp.utils.GlideManager
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import com.google.gson.Gson
import com.youth.banner.BannerConfig
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.fragment_goods_info_layout.*
import kotlinx.android.synthetic.main.layout_goods_info_foot_view_layout.*
import org.jetbrains.anko.bundleOf
import org.json.JSONObject

/**
 * Created by lf on 18.3.21.
 * @author lf on 18.3.21
 */
@SuppressLint("RestrictedApi")
class GoodsInfoFragment : BaseNetWorkingFragment() {

    companion object {
        val GOODS_ID_FLAG = "goods_id_flag"
    }

    private val mArrayList: ArrayList<String> = arrayListOf()

    private var goodsInfoBean: GoodsInfoBean? = null

    private var paramsDialog: BottomGoodsParamDialog? = null

    override fun getLayoutId() = R.layout.fragment_goods_info_layout

    private val commentAdapter by lazy {
        GoodsCommentAdapter(R.layout.adapter_goods_comment_item_layout, mArrayList)
    }

    private val topView by lazy {
        View.inflate(mContext, R.layout.layout_goods_info_top_view_layout, null)
    }

    private val footView by lazy {
        View.inflate(mContext, R.layout.layout_goods_info_foot_view_layout, null)
    }

    private val mTvGoodsContent by lazy {
        footView.findViewById<HtmlTextView>(R.id.mHTvGoodsInfo)
    }

    /**                 店铺信息                          **/
    private val mIvStorePic by lazy {
        footView.findViewById<ImageView>(R.id.mIvGoodsInfoStorePic)
    }

    private val mTvStoreName by lazy {
        footView.findViewById<TextView>(R.id.mTvGoodsInfoStoreName)
    }

    private val mTvStorAllGoodsNum by lazy {
        footView.findViewById<TextView>(R.id.mTvGoodsInfoStoreAllGoodsNum)
    }

    private val mIvStoreFocuseNum by lazy {
        footView.findViewById<TextView>(R.id.mTvGoodsInfoStoreInfoFocusNum)
    }

    private val mIvStoreCommentNum by lazy {
        footView.findViewById<TextView>(R.id.mTvGoodsInfoStoreInfoCommentNum)
    }

    /**                 店铺信息                          **/

    private val mTvCommentCount by lazy {
        topView.findViewById<TextView>(R.id.mTvGoodsInfoTopViewCommentNum)
    }

    private val mTvCommentAll by lazy {
        topView.findViewById<TextView>(R.id.mTvGoodsInfoTopViewCommentAll)
    }

    override fun initView(view: View?) {
        mCibGoodsInfoCollection.setOnClickListener {
            mCibGoodsInfoCollection.toggle()
        }
        mRvGoodsInfoComment.layoutManager = LinearLayoutManager(mContext)
        mRvGoodsInfoComment.adapter = commentAdapter
        commentAdapter.addHeaderView(topView)
        commentAdapter.addFooterView(footView)
        mTvGoodsInfoBuy.setOnClickListener {
            if (goodsInfoBean != null) {
                if (paramsDialog == null) {
                    paramsDialog = BottomGoodsParamDialog(mContext)
                    paramsDialog!!.bindDataToView(
                            "￥:${goodsInfoBean!!.result!!.product_price}",
                            "库存${goodsInfoBean!!.result!!.product_entrepot}件",
                            goodsInfoBean!!.result!!.product_name,
                            goodsInfoBean!!.result!!.product_pic[0],
                            goodsInfoBean!!.result!!.product_specification)
                    paramsDialog!!.setOnGoodsConfirmClickListener { specification, picPath, num ->
                    }
                }
                paramsDialog!!.show()
            }
        }
        mCBPersonalGoodsInfo!!.setImageLoader(object : ImageLoader() {
            override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                GlideManager.loadImage(mContext, path as String, imageView)
            }
        })
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        if (requestID == 0x0) {
            getFastProgressDialog("正在加载详情……")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresent.getDataByPost(0x0,
                RequestParamsHelper.PRODUCT_MODEL,
                RequestParamsHelper.ACT_PRODUCT_DETAIL,
                RequestParamsHelper.getPoductDetailParams(arguments.getString(GOODS_ID_FLAG, "")))
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        val check = checkResultCode(result)
        if (check != null && check.code == SUCCESS_CODE) {
            goodsInfoBean = Gson().fromJson((check.obj as JSONObject).toString(), GoodsInfoBean::class.java)
            bindData()
        }
    }

    private fun bindData() {
        mCBPersonalGoodsInfo.setImages(goodsInfoBean!!.result!!.product_pic).setDelayTime(3000).setBannerStyle(BannerConfig.CIRCLE_INDICATOR).start()
        mCibGoodsInfoCollection.isChecked = goodsInfoBean!!.result!!.product_collect != "0"
        mTvGoodsInfoPrice.text = "￥ ${goodsInfoBean!!.result!!.product_price}"
        mTvGoodsInfoOldPrice.paint.flags = TextPaint.STRIKE_THRU_TEXT_FLAG
        mTvGoodsInfoOldPrice.text = "￥ ${goodsInfoBean!!.result!!.product_yprice}"
        mTvGoodsInfoInfoReleaseCount.text = goodsInfoBean!!.result!!.product_entrepot
        mTvGoodsInfoTitle.text = goodsInfoBean!!.result!!.product_name
        mTvGoodsInfoDescription.text = Html.fromHtml(goodsInfoBean!!.result!!.product_description)
        mTvGoodsContent.setHtmlFromString(goodsInfoBean!!.result!!.product_content, false)
        GlideManager.loadImage(mContext, goodsInfoBean!!.arr1!!.wholesale_shop_pic, mIvStorePic)
        mTvStoreName.text = goodsInfoBean!!.arr1!!.wholesale_shop_name
        mTvGoodsInfoStoreAllGoodsNum.text = goodsInfoBean!!.arr1!!.wholesale_shop_num
        mTvGoodsInfoStoreInfoFocusNum.text = goodsInfoBean!!.arr1!!.wholesale_shop_attention
        mTvGoodsInfoStoreInfoCommentNum.text = goodsInfoBean!!.arr1!!.wholesale_shop_attention
        footView.findViewById<TextView>(R.id.mTvGoodsInfoEnterStore).setOnClickListener {
            FragmentContainerActivity
                    .from(mContext)
                    .setNeedNetWorking(true)
                    .setHiddenToolBar(true)
                    .setClazz(StoreInfoFragment::class.java)
                    .setExtraBundle(bundleOf(Pair(StoreInfoFragment.STORE_ID_FLAG, goodsInfoBean!!.arr1!!)))
                    .start()
        }
    }

    override fun onStart() {
        super.onStart()
        mCBPersonalGoodsInfo.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        mCBPersonalGoodsInfo.stopAutoPlay()
    }

    override fun onDestroyView() {
        mCBPersonalGoodsInfo.releaseBanner()
        super.onDestroyView()
    }

    class GoodsInfoBean {
        var code: Int = 0
        var msg: String? = null
        var result: GoodsBean? = null
        var arr1: StoreInfoBean? = null
        var arr2: AdsBean? = null
    }

}
