package com.android.ql.lf.carapp.ui.fragments.bottom

import android.app.Fragment
import android.view.View
import android.view.ViewGroup
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.activities.MainActivity
import com.android.ql.lf.carapp.ui.fragments.BaseFragment
import com.android.ql.lf.carapp.ui.fragments.user.LoginFragment
import com.android.ql.lf.carapp.ui.fragments.user.SettingFragment
import com.android.ql.lf.carapp.ui.fragments.user.mine.*
import kotlinx.android.synthetic.main.fragment_main_mine_layout.*

/**
 * Created by lf on 18.1.24.
 * @author lf on 18.1.24
 */
class MainMineFragment : BaseFragment() {

    companion object {
        fun newInstance(): MainMineFragment {
            return MainMineFragment()
        }
    }

    override fun getLayoutId() = R.layout.fragment_main_mine_layout

    override fun initView(view: View?) {
        val height = (mContext as MainActivity).statusHeight
        val param = mRlMineTitleContainer.layoutParams as ViewGroup.MarginLayoutParams
        param.topMargin = height
        mRlMineTitleContainer.layoutParams = param
        mSrlMainMineContainer.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        mLlMainMinePersonalInfoContainer.setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "个人信息", LoginFragment::class.java)
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
}