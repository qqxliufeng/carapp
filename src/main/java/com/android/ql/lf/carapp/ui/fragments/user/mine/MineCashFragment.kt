package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import kotlinx.android.synthetic.main.fragment_mine_cash_fragment.*

/**
 * Created by lf on 18.2.3.
 * @author lf on 18.2.3
 */
class MineCashFragment :BaseNetWorkingFragment(){

    override fun getLayoutId() = R.layout.fragment_mine_cash_fragment

    override fun initView(view: View?) {
        mLlMineCashListContainer.setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext,"提现列表",MineCashListFragment::class.java)
        }
    }
}