package com.android.ql.lf.carapp.ui.fragments.bottom

import android.view.View
import android.view.ViewGroup
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.activities.MainActivity
import com.android.ql.lf.carapp.ui.fragments.BaseFragment
import com.android.ql.lf.carapp.ui.fragments.user.mine.MineGoodsCollectionFragment
import com.android.ql.lf.carapp.ui.fragments.user.mine.MineStoreCollectionFragment
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
        mLlMainMineStoreContainer.setOnClickListener{
            FragmentContainerActivity.startFragmentContainerActivity(mContext,"店铺收藏",true,false, MineStoreCollectionFragment::class.java)
        }
        mLlMainMineGoodsContainer.setOnClickListener{
            FragmentContainerActivity.startFragmentContainerActivity(mContext,"商品收藏",true,false,MineGoodsCollectionFragment::class.java)
        }
    }
}