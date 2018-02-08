package com.android.ql.lf.carapp.ui.fragments.user

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.present.UserPresent
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import kotlinx.android.synthetic.main.fragment_login_layout.*

/**
 * Created by lf on 18.1.24.
 * @author lf on 18.1.24
 */
class LoginFragment : BaseNetWorkingFragment() {

    private val userPresent: UserPresent by lazy {
        UserPresent()
    }

    override fun getLayoutId() = R.layout.fragment_login_layout

    override fun initView(view: View?) {
        (mContext as FragmentContainerActivity).setToolBarBackgroundColor(Color.WHITE)
        (mContext as FragmentContainerActivity).setStatusBarLightColor(false)
        val toolbar = (mContext as FragmentContainerActivity).toolbar
        toolbar.setTitleTextColor(Color.DKGRAY)
        toolbar.navigationIcon!!.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP)
        mTvLoginRegister.setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "注册", RegisterFragment::class.java)
        }
        mTvLoginForgetPassword.setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "忘记密码", ForgetPasswordFragment::class.java)
        }
        mBtLogin.setOnClickListener {
            mPresent.getDataByPost(
                    0x0,
                    RequestParamsHelper.LOGIN_MODEL, RequestParamsHelper.ACT_LOGIN,
                    RequestParamsHelper.getLoginParams("", ""))
        }
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        userPresent.onLogin(result.toString())
    }
}