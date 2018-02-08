package com.android.ql.lf.carapp.ui.fragments.user

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import kotlinx.android.synthetic.main.fragment_register_layout.*

/**
 * Created by lf on 18.1.24.
 * @author lf on 18.1.24
 */
class RegisterFragment : BaseNetWorkingFragment() {

    override fun getLayoutId() = R.layout.fragment_register_layout

    override fun initView(view: View?) {
        (mContext as FragmentContainerActivity).setToolBarBackgroundColor(Color.WHITE)
        (mContext as FragmentContainerActivity).setStatusBarLightColor(false)
        val toolbar = (mContext as FragmentContainerActivity).toolbar
        toolbar.setTitleTextColor(Color.DKGRAY)
        toolbar.navigationIcon!!.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP)
        mBtRegister.setOnClickListener {
            mPresent.getDataByPost(
                    0,
                    RequestParamsHelper.LOGIN_MODEL,
                    RequestParamsHelper.ACT_REGISTER,
                    RequestParamsHelper.getRegisterParams(mEtRegisterPhone.text.toString(), mEtRegisterPassword.text.toString()))
        }

        mIvRegisterClearPhone.setOnClickListener {
            mEtRegisterPhone.setText("")
        }
        mIvRegisterClearPassword.setOnClickListener {
            mEtRegisterPassword.setText("")
        }
    }
}