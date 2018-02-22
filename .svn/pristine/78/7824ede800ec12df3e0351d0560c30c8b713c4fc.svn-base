package com.android.ql.lf.carapp.ui.fragments.user

import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.utils.isEmpty
import com.android.ql.lf.carapp.utils.toast
import kotlinx.android.synthetic.main.fragment_reset_password_layout.*

/**
 * Created by lf on 18.2.5.
 * @author lf on 18.2.5
 */
class ResetPasswordFragment : BaseNetWorkingFragment() {

    override fun getLayoutId() = R.layout.fragment_reset_password_layout

    override fun initView(view: View?) {
        mBtEtResetPasswordSave.setOnClickListener {
            if (mEtResetPasswordOldPW.isEmpty()) {
                toast("请输入原密码")
                return@setOnClickListener
            }
            if (mEtResetPasswordNewOne.isEmpty()) {
                toast("请输入新密码")
                return@setOnClickListener
            }
            if (mEtResetPasswordNewTwo.isEmpty()) {
                toast("请再次输入新密码")
                return@setOnClickListener
            }
            if (mEtResetPasswordNewOne.text.toString().trim() != (mEtResetPasswordNewTwo.text.toString().trim())) {
                toast("两次密码不一致")
                return@setOnClickListener
            }
        }
    }
}