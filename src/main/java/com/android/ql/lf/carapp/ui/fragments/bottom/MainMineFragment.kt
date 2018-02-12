package com.android.ql.lf.carapp.ui.fragments.bottom

import android.view.View
import android.view.ViewGroup
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.UserInfo
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.activities.MainActivity
import com.android.ql.lf.carapp.ui.fragments.BaseFragment
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.ui.fragments.user.LoginFragment
import com.android.ql.lf.carapp.ui.fragments.user.SettingFragment
import com.android.ql.lf.carapp.ui.fragments.user.mine.*
import com.android.ql.lf.carapp.utils.GlideManager
import com.android.ql.lf.carapp.utils.RxBus
import kotlinx.android.synthetic.main.fragment_main_mine_layout.*
import q.rorbin.badgeview.QBadgeView

/**
 * Created by lf on 18.1.24.
 * @author lf on 18.1.24
 */
class MainMineFragment : BaseNetWorkingFragment() {

    companion object {
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

    override fun getLayoutId() = R.layout.fragment_main_mine_layout

    override fun initView(view: View?) {
        val height = (mContext as MainActivity).statusHeight
        val param = mRlMineTitleContainer.layoutParams as ViewGroup.MarginLayoutParams
        param.topMargin = height
        mRlMineTitleContainer.layoutParams = param
        mSrlMainMineContainer.setColorSchemeColors(resources.getColor(R.color.colorPrimary))

        //登录成功，刷新界面
        registerLoginSuccessBus()
        messageSubscription

        mLlMainMinePersonalInfoContainer.setOnClickListener {
            if (UserInfo.getInstance().isLogin) {
                FragmentContainerActivity.from(mContext).setClazz(MinePersonalInfoFragment::class.java).setTitle("个人中心").start()
            } else {
                FragmentContainerActivity.from(mContext).setClazz(LoginFragment::class.java).setTitle("登录").setNeedNetWorking(true).start()
            }
        }
        mLlMainMineStoreContainer.setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "店铺收藏", MineStoreCollectionFragment::class.java)
        }
        mLlMainMineGoodsContainer.setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "商品收藏", MineGoodsCollectionFragment::class.java)
        }
        mTvMainMineGrade.setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "我的等级", MineGradeFragment::class.java)
        }
        mTvMainServiceEdit.setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "个人服务信息", MinePersonalServiceEditFragment::class.java)
        }
        mTvMainMineStore.setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "我的店铺", MineStoreInfoFragment::class.java)
        }
        mTvMainMineQCode.setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "我的邀请码", MineQCodeFragment::class.java)
        }
        mTvMainMineWallet.setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "我的钱包", MineWalletFragment::class.java)
        }
        mLlMainMineFootPrintContainer.setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "我的足迹", MineFootPrintFragment::class.java)
        }
        mTvMainMineSetting.setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "设置", SettingFragment::class.java)
        }
        mTvMainMineArticle.setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "我的帖子", MineArticleFragment::class.java)
        }
        mTvMainMineApplyMaster.setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "申请成为商家", MineApplyMasterFragment::class.java)
        }
    }

    override fun onLoginSuccess(it: UserInfo?) {
        GlideManager.loadFaceCircleImage(mContext, it!!.memberPic, mIvMainMineFace)
        mTvMainMineEditNameNotify.visibility = View.VISIBLE
        mTvMainMineName.text = it.memberName
        mTvMainMinePhone.text = it.memberPhone.let {
            it.substring(0, 3) + "****" + it.substring(7, it.length)
        }
    }

    override fun onDestroyView() {
        if (messageSubscription != null && !messageSubscription.isUnsubscribed) {
            messageSubscription.unsubscribe()
        }
        super.onDestroyView()
    }

}