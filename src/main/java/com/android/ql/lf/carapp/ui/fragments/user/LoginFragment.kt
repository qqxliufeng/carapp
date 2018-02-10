package com.android.ql.lf.carapp.ui.fragments.user

import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.present.UserPresent
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.utils.*
import kotlinx.android.synthetic.main.fragment_login_layout.*
import org.json.JSONObject

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
            FragmentContainerActivity.from(mContext)
                    .setTitle("注册")
                    .setNeedNetWorking(true)
                    .setClazz(RegisterFragment::class.java)
                    .start()
        }
        mTvLoginForgetPassword.setOnClickListener {
            FragmentContainerActivity.from(mContext)
                    .setTitle("忘记密码")
                    .setNeedNetWorking(true)
                    .setClazz(ForgetPasswordFragment::class.java)
                    .start()
        }
        mIvLoginClearName.setOnClickListener {
            mEtLoginName.setText("")
        }
        mIvLoginClearPassword.setOnClickListener {
            mEtLoginPassword.setText("")
        }
        mEtLoginName.setText("15910101117")
        mEtLoginPassword.setText("123456")
        mBtLogin.setOnClickListener {
            if (mEtLoginName.isEmpty()) {
                mEtLoginName.showSnackBar("手机号不能为空")
                return@setOnClickListener
            }
            if (!mEtLoginName.isPhone()) {
                mEtLoginName.showSnackBar("请输入正确的手机号")
                return@setOnClickListener
            }
            if (mEtLoginPassword.isEmpty()) {
                mEtLoginPassword.showSnackBar("密码不能为空")
                return@setOnClickListener
            }
            mPresent.getDataByPost(
                    0x0,
                    RequestParamsHelper.LOGIN_MODEL, RequestParamsHelper.ACT_LOGIN,
                    RequestParamsHelper.getLoginParams(mEtLoginName.text.toString(), mEtLoginPassword.text.toString()))
        }
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        progressDialog = ProgressDialog.show(mContext, null, "正在登录……")
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        val checkResult = checkResultCode(result)
        if (checkResult != null) {
            val jsonObject = checkResult.obj as JSONObject
            if (SUCCESS_CODE == checkResult.code) {
                toast("登录成功")
                userPresent.onLogin(jsonObject.optJSONObject("result"))
                finish()
            } else {

                toast(jsonObject.optString("msg"))
            }
        } else {
            toast("登录失败，请稍后重试……")
        }
    }

    override fun onRequestFail(requestID: Int, e: Throwable) {
        super.onRequestFail(requestID, e)
        toast("登录失败，请稍后重试……")
    }

}