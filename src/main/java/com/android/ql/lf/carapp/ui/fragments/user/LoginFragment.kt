package com.android.ql.lf.carapp.ui.fragments.user

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import kotlinx.android.synthetic.main.fragment_login_layout.*

/**
 * Created by lf on 18.1.24.
 * @author lf on 18.1.24
 */
class LoginFragment : BaseNetWorkingFragment() {

    override fun getLayoutId() = R.layout.fragment_login_layout

    override fun initView(view: View?) {
        (mContext as FragmentContainerActivity).setToolBarBackgroundColor(Color.WHITE)
        (mContext as FragmentContainerActivity).setStatusBarLightColor(false)
        val toolbar = (mContext as FragmentContainerActivity).toolbar
        toolbar.setTitleTextColor(Color.DKGRAY)
        toolbar.navigationIcon!!.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP)
        mTvLoginRegister.setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "注册", true, false, RegisterFragment::class.java)
        }
        mTvLoginForgetPassword.setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "忘记密码", true, false, ForgetPasswordFragment::class.java)
        }
    }
}