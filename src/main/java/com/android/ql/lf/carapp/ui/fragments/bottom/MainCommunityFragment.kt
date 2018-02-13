package com.android.ql.lf.carapp.ui.fragments.bottom

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.activities.MainActivity
import com.android.ql.lf.carapp.ui.adapter.ArticleListAdapter
import com.android.ql.lf.carapp.ui.adapter.OrderListForMineForWaitingWorkAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carapp.ui.fragments.community.ArticleInfoFragment
import com.android.ql.lf.carapp.ui.fragments.community.ArticleListFragment
import com.android.ql.lf.carapp.ui.fragments.community.ArticleSearchFragment
import com.android.ql.lf.carapp.ui.fragments.community.WriteArticleFragment
import com.android.ql.lf.carapp.ui.fragments.user.mine.MineApplyMasterFragment
import com.android.ql.lf.carapp.ui.fragments.user.mine.MineArticleFragment
import com.android.ql.lf.carapp.ui.views.VerticalTextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.sunfusheng.marqueeview.MarqueeView
import com.youth.banner.Banner
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

    private val topList = arrayListOf(R.drawable.test_pic1, R.drawable.test_pic2, R.drawable.test_pic3, R.drawable.test_pic4, R.drawable.test_pic5)
    private lateinit var topMarqueeView: MarqueeView
    private var mBannerMainCommunity: Banner? = null

    override fun getLayoutId() = R.layout.fragment_main_community_layout

    override fun initView(view: View?) {
        super.initView(view)
        val height = (mContext as MainActivity).statusHeight
        val param = mRlCommunityTitleContainer.layoutParams as ViewGroup.MarginLayoutParams
        param.topMargin = height
        mRlCommunityTitleContainer.layoutParams = param
        mFabWriteNote.setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "发布帖子", WriteArticleFragment::class.java)
        }
        mIvMainCommunityMine.setOnClickListener {
            FragmentContainerActivity.from(mContext).setClazz(MineArticleFragment::class.java).setTitle("我的帖子").start()
        }
        mIvMainCommunitySearch.setOnClickListener {
            FragmentContainerActivity.from(mContext).setClazz(ArticleSearchFragment::class.java).setNeedNetWorking(true).setHiddenToolBar(true).start()
        }
        val topView = View.inflate(mContext, R.layout.layout_main_community_top_layout, null)
        val topRecyclerView = topView.findViewById<RecyclerView>(R.id.mRvMainCommunityTopContainer)
        val mBannerMainCommunity = topView.findViewById<Banner>(R.id.mBannerMainCommunity)
        topMarqueeView = topView.findViewById(R.id.mMvMainCommunityTopContainer)
        topRecyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        topRecyclerView.adapter = TopRecyclerViewAdapter(R.layout.layout_main_community_top_item_layout, topList)
        topRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                FragmentContainerActivity.startFragmentContainerActivity(mContext, "文章详情", ArticleInfoFragment::class.java)
            }
        })
        topView.findViewById<TextView>(R.id.mTvMainCommunityTopMoreArticle).setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "所有发布", ArticleListFragment::class.java)
        }
        mBannerMainCommunity.setImageLoader(object : ImageLoader() {
            override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                Glide.with(context).load(path as Int).into(imageView)
            }
        })
        mBannerMainCommunity
                .setImages(arrayListOf(R.drawable.test_banner))
                .setDelayTime(3000)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .start()
        val info = ArrayList<String>()
        info.add("大家好，我是孙福生。")
        info.add("欢迎大家关注我哦！")
        info.add("GitHub帐号：sfsheng0322")
        info.add("新浪微博：孙福生微博")
        info.add("个人博客：sunfusheng.com")
        info.add("微信公众号：孙福生")
        topMarqueeView.startWithList(info)

        mBaseAdapter.addHeaderView(topView)
        mBaseAdapter.setHeaderAndEmpty(true)
    }

    override fun createAdapter(): BaseQuickAdapter<String, BaseViewHolder> =
            ArticleListAdapter(R.layout.adapter_article_item_layout, mArrayList)

    override fun onRefresh() {
//        super.onRefresh()
        testAdd("")
//        setEmptyView()
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        val itemDecoration = super.getItemDecoration() as DividerItemDecoration
        itemDecoration.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.recycler_view_height_divider))
        return itemDecoration
    }

    override fun onStart() {
        super.onStart()
        mBannerMainCommunity?.startAutoPlay()
        topMarqueeView.startFlipping()
    }

    override fun onStop() {
        super.onStop()
        mBannerMainCommunity?.stopAutoPlay()
        topMarqueeView.stopFlipping()
    }

    override fun onMyItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemClick(adapter, view, position)
        FragmentContainerActivity.startFragmentContainerActivity(mContext, "详情", ArticleInfoFragment::class.java)
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