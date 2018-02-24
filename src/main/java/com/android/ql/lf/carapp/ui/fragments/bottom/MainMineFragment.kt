package com.android.ql.lf.carapp.ui.fragments.bottom

import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.view.ViewGroup
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.UserInfo
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.activities.MainActivity
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.ui.fragments.user.SettingFragment
import com.android.ql.lf.carapp.ui.fragments.user.mine.*
import com.android.ql.lf.carapp.utils.*
import kotlinx.android.synthetic.main.fragment_main_mine_layout.*

/**
 * Created by lf on 18.1.24.
 * @author lf on 18.1.24
 */
class MainMineFragment : BaseNetWorkingFragment(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        val MINE_PERSONAL_INFO_TOKEN = "personal_info_token"
        val MINE_STORE_COLLECTION_TOKEN = "store_collection_token"
        val MINE_GOODS_COLLECTION_TOKEN = "goods_collection_token"
        val MINE_GRADE_TOKEN = "grade_token"
        val MINE_PERSONAL_EDIT_INFO_TOKEN = "personal_edit_info_token"
        val MINE_STORE_INFO_TOKEN = "store_info_token"
        val MINE_Q_CODE_TOKEN = "q_code_token"
        val MINE_MY_WALLET_TOKEN = "my_wallet_token"
        val MINE_FOOT_PRINT_TOKEN = "foot_print_token"
        val MINE_SETTING_TOKEN = "setting_token"
        val MINE_MY_ARTICLE_TOKEN = "my_article_token"
        val MINE_APPLY_MASTER_TOKEN = "apply_master_token"
        fun newInstance(): MainMineFragment {
            return MainMineFragment()
        }
    }

    private val messageSubscription by lazy {
        RxBus.getDefault().toObservable(String::class.java).subscribe {
            if (MainOrderHouseFragment.FIRST_LEVEL_ALL_MESSAGE_HAVE_READ_FLAG == it) {
                if (mViewMainMineMessageNotify.visibility == View.VISIBLE) {
                    mViewMainMineMessageNotify.visibility = View.GONE
                }
            }
        }
    }

    private val userLogoutSubscription by lazy {
        RxBus.getDefault().toObservable(String::class.java).subscribe {
            if (it == UserInfo.LOGOUT_FLAG) {
                onLogoutSuccess()
            }
        }
    }

    private val modifyInfoSubscription by lazy {
        RxBus.getDefault().toObservable(String::class.java).subscribe {
            if (it == "modify info success") {
                mTvMainMineName.text = UserInfo.getInstance().memberName
                GlideManager.loadFaceCircleImage(mContext, UserInfo.getInstance().memberPic, mIvMainMineFace)
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_main_mine_layout

    override fun initView(view: View?) {
        val height = (mContext as MainActivity).statusHeight
        val param = mRlMineTitleContainer.layoutParams as ViewGroup.MarginLayoutParams
        param.topMargin = height
        mRlMineTitleContainer.layoutParams = param
        mSrlMainMineContainer.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        mSrlMainMineContainer.setOnRefreshListener(this)

        //注册登录成功事件，刷新界面
        registerLoginSuccessBus()
        //注册消息事件
        messageSubscription
        //注册用户退出事件
        userLogoutSubscription
        //修改个人信息
        modifyInfoSubscription


        mLlMainMinePersonalInfoContainer.doClickWithUserStatusStart(MINE_PERSONAL_INFO_TOKEN) {
            FragmentContainerActivity.from(mContext).setClazz(MinePersonalInfoFragment::class.java).setTitle("个人中心").setNeedNetWorking(true).start()
        }
        mLlMainMineStoreContainer.setOnClickListener {
            toast(Constants.NO_FUNCTION_NOTIFY_MESSAGE)
        }
//        mLlMainMineStoreContainer.doClickWithUserStatusStart(MINE_STORE_COLLECTION_TOKEN) {
//            FragmentContainerActivity.startFragmentContainerActivity(mContext, "店铺收藏", MineStoreCollectionFragment::class.java)
//        }
        mLlMainMineGoodsContainer.setOnClickListener {
            toast(Constants.NO_FUNCTION_NOTIFY_MESSAGE)
        }
//        mLlMainMineGoodsContainer.doClickWithUserStatusStart(MINE_GOODS_COLLECTION_TOKEN) {
//            FragmentContainerActivity.startFragmentContainerActivity(mContext, "商品收藏", MineGoodsCollectionFragment::class.java)
//        }
        mTvMainMineGrade.doClickWithUserStatusStart(MINE_GRADE_TOKEN) {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "我的等级", MineGradeFragment::class.java)
        }
        mTvMainServiceEdit.doClickWithUserStatusStart(MINE_PERSONAL_EDIT_INFO_TOKEN) {
            if (UserInfo.getInstance().isCheckingMaster){
                toast("认证资料正在审核中……")
                return@doClickWithUserStatusStart
            }
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "个人服务信息", MinePersonalServiceEditFragment::class.java)
        }
        mTvMainMineStore.setOnClickListener {
            toast(Constants.NO_FUNCTION_NOTIFY_MESSAGE)
        }
//        mTvMainMineStore.doClickWithUserStatusStart(MINE_STORE_INFO_TOKEN) {
//            FragmentContainerActivity.startFragmentContainerActivity(mContext, "我的店铺", MineStoreInfoFragment::class.java)
//        }
        mTvMainMineQCode.doClickWithUserStatusStart(MINE_Q_CODE_TOKEN) {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "我的邀请码", MineQCodeFragment::class.java)
        }

        mTvMainMineShopOrder.setOnClickListener {
            toast(Constants.NO_FUNCTION_NOTIFY_MESSAGE)
        }

        mTvMainMineWallet.doClickWithUserStatusStart(MINE_MY_WALLET_TOKEN) {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "我的钱包", MineWalletFragment::class.java)
        }

        mLlMainMineFootPrintContainer.setOnClickListener {
            toast(Constants.NO_FUNCTION_NOTIFY_MESSAGE)
        }
//        mLlMainMineFootPrintContainer.doClickWithUserStatusStart(MINE_FOOT_PRINT_TOKEN) {
//            FragmentContainerActivity.startFragmentContainerActivity(mContext, "我的足迹", MineFootPrintFragment::class.java)
//        }
        mTvMainMineSetting.doClickWithUserStatusStart(MINE_SETTING_TOKEN) {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "设置", SettingFragment::class.java)
        }
        mTvMainMineArticle.doClickWithUserStatusStart(MINE_MY_ARTICLE_TOKEN) {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "我的帖子", MineArticleFragment::class.java)
        }
        mTvMainMineApplyMaster.doClickWithUserStatusStart(MINE_APPLY_MASTER_TOKEN) {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "申请成为商家", MineApplyMasterFragment::class.java)
        }
    }

    override fun onRefresh() {
        if (mSrlMainMineContainer.isRefreshing) {
            mSrlMainMineContainer.post {
                mSrlMainMineContainer.isRefreshing = false
            }
        }
    }

    /**
     * 登录成功
     */
    override fun onLoginSuccess(it: UserInfo?) {
        GlideManager.loadFaceCircleImage(mContext, it!!.memberPic, mIvMainMineFace)
        mTvMainMineEditNameNotify.visibility = View.VISIBLE
        mTvMainMineName.text = it.memberName
        mTvMainMinePhone.text = it.memberPhone.let {
            it.substring(0, 3) + "****" + it.substring(7, it.length)
        }
        when (UserInfo.loginToken) {
            MINE_PERSONAL_INFO_TOKEN -> {
                mLlMainMinePersonalInfoContainer.doClickWithUseStatusEnd()
            }
            MINE_STORE_COLLECTION_TOKEN -> {
                mLlMainMineStoreContainer.doClickWithUseStatusEnd()
            }
            MINE_GOODS_COLLECTION_TOKEN -> {
                mLlMainMineGoodsContainer.doClickWithUseStatusEnd()
            }
            MINE_GRADE_TOKEN -> {
                mTvMainMineGrade.doClickWithUseStatusEnd()
            }
            MINE_PERSONAL_EDIT_INFO_TOKEN -> {
                mTvMainServiceEdit.doClickWithUseStatusEnd()
            }
            MINE_STORE_INFO_TOKEN -> {
                mTvMainMineStore.doClickWithUseStatusEnd()
            }
            MINE_Q_CODE_TOKEN -> {
                mTvMainMineQCode.doClickWithUseStatusEnd()
            }
            MINE_MY_WALLET_TOKEN -> {
                mTvMainMineWallet.doClickWithUseStatusEnd()
            }
            MINE_FOOT_PRINT_TOKEN -> {
                mLlMainMineFootPrintContainer.doClickWithUseStatusEnd()
            }
            MINE_SETTING_TOKEN -> {
                mTvMainMineSetting.doClickWithUseStatusEnd()
            }
            MINE_MY_ARTICLE_TOKEN -> {
                mTvMainMineArticle.doClickWithUseStatusEnd()
            }
            MINE_APPLY_MASTER_TOKEN -> {
                mTvMainMineApplyMaster.doClickWithUseStatusEnd()
            }
        }
    }

    /**
     * 登录失败
     */
    private fun onLogoutSuccess() {
        mIvMainMineFace.setImageResource(R.drawable.img_default_mine_icon)
        mTvMainMineName.text = "登录/注册"
        mTvMainMinePhone.text = "暂无"
        mTvMainMineCollectionGoodsCount.text = "0"
        mTvMainMineCollectionStoreCount.text = "0"
        mTvMainMineCollectionFootPrintCount.text = "0"
    }

    override fun onDestroyView() {
        unsubscribe(messageSubscription)
        unsubscribe(userLogoutSubscription)
        unsubscribe(modifyInfoSubscription)
        super.onDestroyView()
    }
}