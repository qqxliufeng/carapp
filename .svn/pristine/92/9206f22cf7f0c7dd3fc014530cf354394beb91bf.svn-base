package com.android.ql.lf.carapp.ui.fragments.user

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.CountDownTimer
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.utils.hiddenKeyBoard
import com.android.ql.lf.carapp.utils.isEmpty
import com.android.ql.lf.carapp.utils.isPhone
import com.android.ql.lf.carapp.utils.showSnackBar
import kotlinx.android.synthetic.main.fragment_forget_password_layout.*

/**
 * Created by lf on 18.1.24.
 * @author lf on 18.1.24
 */
class ForgetPasswordFragment : BaseNetWorkingFragment() {

    private var code: String? = null
    private val timeCount: CountDownTimer by lazy {
        object : CountDownTimer(1000 * 10, 1000) {
            override fun onFinish() {
                mTvForgetPasswordGetCode.text = "没有收到验证码？"
                mTvForgetPasswordGetCode.isEnabled = true
            }

            override fun onTick(millisUntilFinished: Long) {
                mTvForgetPasswordGetCode.text = "${millisUntilFinished / 1000}秒"
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_forget_password_layout

    override fun initView(view: View?) {
        (mContext as FragmentContainerActivity).setToolBarBackgroundColor(Color.WHITE)
        (mContext as FragmentContainerActivity).setStatusBarLightColor(false)
        val toolbar = (mContext as FragmentContainerActivity).toolbar
        toolbar.setTitleTextColor(Color.DKGRAY)
        toolbar.navigationIcon!!.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP)
        mIvForgetPasswordClearPhone.setOnClickListener {
            mEtForgetPasswordPhone.setText("")
        }
        mIvForgetPasswordClearPW.setOnClickListener {
            mEtForgetPasswordConfirmPW.setText("")
        }
        mIvForgetPasswordClearConfirmPW.setOnClickListener {
            mEtForgetPasswordConfirmPW.setText("")
        }
        mTvForgetPasswordGetCode.setOnClickListener {
            mContext.hiddenKeyBoard(mEtForgetPasswordPhone.windowToken)
            if (mEtForgetPasswordPhone.isEmpty()) {
                mEtForgetPasswordPhone.showSnackBar("手机号不能为空")
                return@setOnClickListener
            }
            if (!mEtForgetPasswordPhone.isPhone()) {
                mEtForgetPasswordPhone.showSnackBar("请输入正确的手机号")
                return@setOnClickListener
            }
            mTvForgetPasswordGetCode.isEnabled = false
            timeCount.start()
        }
        mBtForgetPassword.setOnClickListener {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timeCount.cancel()
    }


}