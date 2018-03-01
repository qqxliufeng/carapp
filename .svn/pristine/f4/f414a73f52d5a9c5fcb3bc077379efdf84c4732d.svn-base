package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.graphics.Color
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.content.ContextCompat
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.UserInfo
import com.android.ql.lf.carapp.ui.fragments.BaseFragment
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.utils.*
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import kotlinx.android.synthetic.main.fragment_mine_wallet_account_layout.*
import kotlinx.android.synthetic.main.fragment_wallet_account_bind_ali_layout.*
import kotlinx.android.synthetic.main.fragment_wallet_account_bind_wx_layout.*
import org.jetbrains.anko.textColor
import org.json.JSONObject

/**
 * Created by liufeng on 2018/2/25.
 */
class MineWalletAccountFragment : BaseFragment() {

    private val titles by lazy {
        arrayListOf("微信", "支付宝")
    }

    override fun getLayoutId() = R.layout.fragment_mine_wallet_account_layout

    override fun initView(view: View?) {
        mVpMineWalletAccountContainer.adapter = MineWalletAccountViewPagerAdapter(childFragmentManager)
        mTlMineWalletAccountTitle.setupWithViewPager(mVpMineWalletAccountContainer)
    }

    inner class MineWalletAccountViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
        override fun getItem(position: Int) = when (position) {
            0 -> {
                MineWalletWXAccountFragment()
            }
            1 -> {
                MineWalletAliAccountFragment()
            }
            else -> {
                null
            }
        }

        override fun getCount() = titles.size

        override fun getPageTitle(position: Int) = titles[position]
    }

    class MineWalletWXAccountFragment : BaseNetWorkingFragment() {

        private val wxLoginSubscription by lazy {
            RxBus.getDefault().toObservable(BaseResp::class.java).subscribe {
                if (it is SendAuth.Resp) {
                    mPresent.getDataByPost(0x1, RequestParamsHelper.MEMBER_MODEL, RequestParamsHelper.ACT_WX_AUTHORIZATION, RequestParamsHelper.getWxAuthorizationParam(it.code))
                }
            }
        }

        override fun getLayoutId() = R.layout.fragment_wallet_account_bind_wx_layout

        override fun initView(view: View?) {
            if (UserInfo.getInstance().memberIswxAuth == "1") {
                mTvmBindWxWxOpenId.text = "已授权"
                mTvmBindWxWxOpenId.textColor = ContextCompat.getColor(mContext, R.color.colorPrimary)
            } else {
                mTvmBindWxWxOpenId.text = "未授权"
                mTvmBindWxWxOpenId.textColor = Color.RED
                wxLoginSubscription
                mTvmBindWxWxOpenId.setOnClickListener {
                    ThirdLoginManager.wxLogin(WXAPIFactory.createWXAPI(mContext, Constants.WX_APP_ID, true))
                }
            }
            mBtBindWxAccount.setOnClickListener {
                if (mEtBindWxAccountRealName.isEmpty()) {
                    toast(mEtBindWxAccountRealName.hint.toString())
                    return@setOnClickListener
                }
                if (mEtBindWxAccount.isEmpty()) {
                    toast(mEtBindWxAccount.hint.toString())
                    return@setOnClickListener
                }
                if (!mEtBindWxAccount.isIdCard()) {
                    toast("请输入合法的身份证号")
                    return@setOnClickListener
                }
                if (UserInfo.getInstance().memberIswxAuth != "1") {
                    toast("当前帐号未通过微信授权，请先授权")
                    return@setOnClickListener
                }
                mPresent.getDataByPost(0x0,
                        RequestParamsHelper.MEMBER_MODEL,
                        RequestParamsHelper.ACT_BIND_WXPAY,
                        RequestParamsHelper.getBindWxpayParam(mEtBindWxAccount.getTextString(), mEtBindWxAccountRealName.getTextString()))
            }
        }

        override fun onRequestStart(requestID: Int) {
            super.onRequestStart(requestID)
            if (requestID == 0x1) {
                getFastProgressDialog("正在认证……")
            } else {
                getFastProgressDialog("正在提交信息……")
            }
        }

        override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
            super.onRequestSuccess(requestID, result)
            if (requestID == 0x1) {
                val check = checkResultCode(result)
                if (check != null && check.code == SUCCESS_CODE) {
                    val json = (check.obj as JSONObject).optJSONObject("result")
                    UserInfo.getInstance().memberIswxAuth = json.optString("member_iswxauth")
                    UserInfo.getInstance().memberOpenid = json.optString("member_openid")
                    mTvmBindWxWxOpenId.text = "已授权"
                    mTvmBindWxWxOpenId.textColor = ContextCompat.getColor(mContext, R.color.colorPrimary)
                    toast("授权成功！")
                }
            } else if (requestID == 0x0) {
                val check = checkResultCode(result)
                if (check != null && check.code == SUCCESS_CODE) {
                    toast("账号绑定成功")
                } else {
                    toast("账号绑定失败，请稍后重试……")
                }
            }
        }

        override fun onRequestFail(requestID: Int, e: Throwable) {
            super.onRequestFail(requestID, e)
            if (requestID == 0x1) {
                toast("认证失败，请稍后重试……")
            } else {
                toast("账号绑定失败，请稍后重试……")
            }
        }

        override fun onDestroyView() {
            unsubscribe(wxLoginSubscription)
            super.onDestroyView()
        }
    }

    class MineWalletAliAccountFragment : BaseNetWorkingFragment() {

        override fun getLayoutId() = R.layout.fragment_wallet_account_bind_ali_layout

        override fun initView(view: View?) {
            mBtBindAliAccount.setOnClickListener {
                if (mEtBindAliAccountRealName.isEmpty()) {
                    toast(mEtBindAliAccountRealName.hint.toString())
                    return@setOnClickListener
                }
                if (mEtBindAliAccount.isEmpty()) {
                    toast(mEtBindAliAccount.hint.toString())
                    return@setOnClickListener
                }
                mPresent.getDataByPost(0x0, RequestParamsHelper.MEMBER_MODEL, RequestParamsHelper.ACT_BIND_ALIPAY, RequestParamsHelper.getBindAlipayParam(mEtBindAliAccount.getTextString(), mEtBindAliAccountRealName.getTextString()))
            }
        }

        override fun onRequestStart(requestID: Int) {
            super.onRequestStart(requestID)
            getFastProgressDialog("正在提交信息……")
        }

        override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
            super.onRequestSuccess(requestID, result)
            if (requestID == 0x0){
                val check = checkResultCode(result)
                if (check != null && check.code == SUCCESS_CODE) {
                    toast("账号绑定成功")
                } else {
                    toast("账号绑定失败，请稍后重试……")
                }
            }
        }

        override fun onRequestFail(requestID: Int, e: Throwable) {
            super.onRequestFail(requestID, e)
            toast("账号绑定失败，请稍后重试……")
        }
    }
}