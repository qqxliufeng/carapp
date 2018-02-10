package com.android.ql.lf.carapp.ui.fragments.user

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.CountDownTimer
import android.view.View
import com.a.WebViewContentFragment
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.utils.*
import kotlinx.android.synthetic.main.fragment_register_layout.*
import org.jetbrains.anko.bundleOf

/**
 * Created by lf on 18.1.24.
 * @author lf on 18.1.24
 */
class RegisterFragment : BaseNetWorkingFragment() {

    private var code: String? = null
    private val timeCount: CountDownTimer by lazy {
        object : CountDownTimer(1000 * 10, 1000) {
            override fun onFinish() {
                mTvRegisterGetCode.text = "没有收到验证码？"
                mTvRegisterGetCode.isEnabled = true
            }

            override fun onTick(millisUntilFinished: Long) {
                mTvRegisterGetCode.text = "${millisUntilFinished / 1000}秒"
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_register_layout

    override fun initView(view: View?) {
        (mContext as FragmentContainerActivity).setToolBarBackgroundColor(Color.WHITE)
        (mContext as FragmentContainerActivity).setStatusBarLightColor(false)
        val toolbar = (mContext as FragmentContainerActivity).toolbar
        toolbar.setTitleTextColor(Color.DKGRAY)
        toolbar.navigationIcon!!.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP)
        mIvRegisterClearPhone.setOnClickListener {
            mEtRegisterPhone.setText("")
        }
        mIvRegisterClearPassword.setOnClickListener {
            mEtRegisterPassword.setText("")
        }
        mTvRegisterGetCode.setOnClickListener {
            mContext.hiddenKeyBoard(mEtRegisterPhone.windowToken)
            if (mEtRegisterPhone.isEmpty()) {
                mEtRegisterPhone.showSnackBar("手机号不能为空")
                return@setOnClickListener
            }
            if (!mEtRegisterPhone.isPhone()) {
                mEtRegisterPhone.showSnackBar("请输入正确的手机号")
                return@setOnClickListener
            }
            mTvRegisterGetCode.isEnabled = false
            timeCount.start()
//            mPresent.getDataByPost(0,
//                    RequestParamsHelper.LOGIN_MODEL,
//                    RequestParamsHelper.ACT_CODE,
//                    RequestParamsHelper.getCodeParams(mEtRegisterPhone.text.toString()))
        }
        mBtRegister.setOnClickListener {
            if (mEtRegisterPhone.isEmpty()) {
                mEtRegisterPhone.showSnackBar("手机号不能为空")
                return@setOnClickListener
            }
            if (!mEtRegisterPhone.isPhone()) {
                mEtRegisterPhone.showSnackBar("请输入正确的手机号")
                return@setOnClickListener
            }
            if (mEtRegisterCode.isEmpty()) {
                mEtRegisterCode.showSnackBar("请输入验证码")
                return@setOnClickListener
            }
            if (code != mEtRegisterCode.text.toString()) {
                mEtRegisterCode.showSnackBar("请输入正确的验证码")
                return@setOnClickListener
            }
            if (mEtRegisterPassword.isEmpty()) {
                mEtRegisterPassword.showSnackBar("请输入密码")
                return@setOnClickListener
            }
            mPresent.getDataByPost(
                    1,
                    RequestParamsHelper.LOGIN_MODEL,
                    RequestParamsHelper.ACT_REGISTER,
                    RequestParamsHelper.getRegisterParams(mEtRegisterPhone.text.toString(), mEtRegisterPassword.text.toString()))
        }
        mTvRegisterProtocol.setOnClickListener {
            FragmentContainerActivity.from(mContext)
                    .setNeedNetWorking(true)
                    .setTitle("用户服务协议")
                    .setExtraBundle(bundleOf(Pair(WebViewContentFragment.PATH_FLAG, "http://www.baidu.com")))
                    .setClazz(WebViewContentFragment::class.java)
                    .start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timeCount.cancel()
    }

}