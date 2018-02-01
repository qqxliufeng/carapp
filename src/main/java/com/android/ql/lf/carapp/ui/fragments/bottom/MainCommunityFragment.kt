package com.android.ql.lf.carapp.ui.fragments.bottom

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.activities.MainActivity
import com.android.ql.lf.carapp.ui.adapter.OrderListForMineForWaitingWorkAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sunfusheng.marqueeview.MarqueeView
import com.youth.banner.BannerConfig
import com.youth.banner.loader.ImageLoader
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.fragment_main_community_layout.*

/**
 * Created by lf on 18.1.24.
 * @author lf on 18.1.24
 */
class MainCommunityFragment : BaseRecyclerViewFragment<String>() {


    companion object {
        fun newInstance(): MainCommunityFragment {
            return MainCommunityFragment()
        }
    }

    private val topList = arrayListOf<Int>()
    private lateinit var topMarqueeView:MarqueeView

    override fun getLayoutId() = R.layout.fragment_main_community_layout

    override fun initView(view: View?) {
        super.initView(view)
        val height = (mContext as MainActivity).statusHeight
        val param = mRlCommunityTitleContainer.layoutParams as ViewGroup.MarginLayoutParams
        param.topMargin = height
        mRlCommunityTitleContainer.layoutParams = param
        mBannerMainCommunity.setImageLoader(object : ImageLoader() {
            override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                Glide.with(context).load(path as Int).into(imageView)
            }
        })
        mBannerMainCommunity
                .setImages(arrayListOf(R.drawable.test_pic01, R.drawable.test_pic01, R.drawable.test_pic01, R.drawable.test_pic01))
                .setDelayTime(3000)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .start()
        (0 until 10).forEach {
            topList.add(R.drawable.test_pic01)
        }
        val topView = View.inflate(mContext, R.layout.layout_main_community_top_layout, null)
        val topRecyclerView = topView.findViewById<RecyclerView>(R.id.mRvMainCommunityTopContainer)
        topMarqueeView = topView.findViewById<MarqueeView>(R.id.mMvMainCommunityTopContainer)
        topRecyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        topRecyclerView.adapter = TopRecyclerViewAdapter(R.layout.layout_main_community_top_item_layout, topList)

        val info = ArrayList<String>()
        info.add("1. 大家好，我是孙福生。")
        info.add("2. 欢迎大家关注我哦！")
        info.add("3. GitHub帐号：sfsheng0322")
        info.add("4. 新浪微博：孙福生微博")
        info.add("5. 个人博客：sunfusheng.com")
        info.add("6. 微信公众号：孙福生")
        topMarqueeView.startWithList(info)

        mBaseAdapter.addHeaderView(topView)
    }

    override fun createAdapter(): BaseQuickAdapter<String, BaseViewHolder> =
            OrderListForMineForWaitingWorkAdapter(R.layout.adapter_order_list_for_mine_for_having_calculate_item_layout, mArrayList)

    override fun onRefresh() {
        super.onRefresh()
        testAdd("")
    }

    override fun onResume() {
        super.onResume()
        mBannerMainCommunity.startAutoPlay()
        topMarqueeView.startFlipping()
    }

    override fun onStop() {
        super.onStop()
        mBannerMainCommunity.stopAutoPlay()
        topMarqueeView.stopFlipping()
    }

    class TopRecyclerViewAdapter(layoutId: Int, list: ArrayList<Int>) : BaseQuickAdapter<Int, BaseViewHolder>(layoutId, list) {
        override fun convert(helper: BaseViewHolder?, item: Int?) {
            val imageView = helper!!.getView<ImageView>(R.id.mIvMainCommunityTopItemImage)
            Glide.with(mContext)
                    .load(item)
                    .bitmapTransform(RoundedCornersTransformation(mContext, 8, 0))
                    .into(imageView)
            imageView.setColorFilter(Color.parseColor("#77000000"))
        }
    }
}