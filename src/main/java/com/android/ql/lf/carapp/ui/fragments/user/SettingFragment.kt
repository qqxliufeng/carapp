package com.android.ql.lf.carapp.ui.fragments.user

import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import kotlinx.android.synthetic.main.fragment_setting_layout.*

/**
 * Created by liufeng on 2018/2/3.
 */
class SettingFragment :BaseNetWorkingFragment(){

    override fun getLayoutId() = R.layout.fragment_setting_layout

    override fun initView(view: View?) {
        mTvPersonalInfoResetPassword.setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext,"修改密码",ResetPasswordFragment::class.java)
        }
    }
}