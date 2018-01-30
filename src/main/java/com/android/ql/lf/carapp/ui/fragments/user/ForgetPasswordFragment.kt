package com.android.ql.lf.carapp.ui.fragments.user

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment

/**
 * Created by lf on 18.1.24.
 * @author lf on 18.1.24
 */
class ForgetPasswordFragment :BaseNetWorkingFragment(){

    override fun getLayoutId() = R.layout.fragment_forget_password_layout

    override fun initView(view: View?) {
        (mContext as FragmentContainerActivity).setToolBarBackgroundColor(Color.WHITE)
        (mContext as FragmentContainerActivity).setStatusBarLightColor(false)
        val toolbar = (mContext as FragmentContainerActivity).toolbar
        toolbar.setTitleTextColor(Color.DKGRAY)
        toolbar.navigationIcon!!.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP)
    }
}