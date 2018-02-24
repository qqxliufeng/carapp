package com.android.ql.lf.carapp.ui.fragments.community

import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.ArticleBean
import com.android.ql.lf.carapp.data.UserInfo
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.adapter.ArticleListAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carapp.ui.fragments.user.LoginFragment
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.jetbrains.anko.bundleOf

/**
 * Created by lf on 18.2.5.
 * @author lf on 18.2.5
 */
class ArticleListFragment : BaseRecyclerViewFragment<ArticleBean>() {

    companion object {
        val ARTICLE_DETAIL_FLAG_FOR_ALL = "article_detail_flag_for_all"
    }

    private var currentArticle: ArticleBean? = null

    override fun getLayoutId() = R.layout.fragment_article_list_layout

    override fun createAdapter(): BaseQuickAdapter<ArticleBean, BaseViewHolder> =
            ArticleListAdapter(R.layout.adapter_article_item_layout, mArrayList)

    override fun initView(view: View?) {
        super.initView(view)
        registerLoginSuccessBus()
    }

    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0, RequestParamsHelper.QAA_MODEL, RequestParamsHelper.ACT_QUIZ_TYPE_SEARCH, RequestParamsHelper.getQuizTypeSearchParam(arguments.getString("type", ""), currentPage))
    }

    override fun getEmptyMessage() = "暂无帖子"

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        processList(result as String, ArticleBean::class.java)
    }

    override fun onMyItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemClick(adapter, view, position)
        currentArticle = mArrayList[position]
        if (UserInfo.getInstance().isLogin) {
            interArticleInfo()
        } else {
            UserInfo.loginToken = ARTICLE_DETAIL_FLAG_FOR_ALL
            LoginFragment.startLogin(mContext)
        }
    }

    private fun interArticleInfo() {
        FragmentContainerActivity
                .from(mContext)
                .setTitle("详情")
                .setClazz(ArticleInfoFragment::class.java)
                .setNeedNetWorking(true)
                .setExtraBundle(bundleOf(Pair(ArticleInfoFragment.ARTICLE_BEAN_FLAG, currentArticle!!)))
                .start()
    }

    override fun onLoginSuccess(userInfo: UserInfo?) {
        super.onLoginSuccess(userInfo)
        when(UserInfo.loginToken){
            ARTICLE_DETAIL_FLAG_FOR_ALL -> {
                interArticleInfo()
            }
        }
    }
}