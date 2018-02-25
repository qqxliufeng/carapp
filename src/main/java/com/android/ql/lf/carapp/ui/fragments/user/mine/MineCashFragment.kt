package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import com.android.ql.lf.carapp.utils.getTextString
import com.android.ql.lf.carapp.utils.toast
import kotlinx.android.synthetic.main.fragment_mine_cash_fragment.*
import org.json.JSONObject

/**
 * Created by lf on 18.2.3.
 * @author lf on 18.2.3
 */
class MineCashFragment : BaseNetWorkingFragment() {

    private var allMoney = 0.0
    private var cashType = 0

    override fun getLayoutId() = R.layout.fragment_mine_cash_fragment

    override fun initView(view: View?) {
        mLlMineCashListContainer.setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "提现列表", MineCashListFragment::class.java)
        }
        mBtMineCashMoneySubmit.setOnClickListener {
            try {
                val inputMoney = mEtMineCashMoney.getTextString()
                if (inputMoney.isEmpty()) {
                    toast("请输入提现金额！")
                    return@setOnClickListener
                }
                if (inputMoney.startsWith(".", true)) {
                    toast("请输入正确的提现金额！")
                    return@setOnClickListener
                }
                if (inputMoney.toDouble() > allMoney) {
                    toast("提现金额超限！")
                    return@setOnClickListener
                }
                cashType = when {
                    mRbMineCashWayWX.isChecked -> 1
                    mRbMineCashWayAlipay.isChecked -> 2
                    else -> 0
                }
                if (cashType == 0) {
                    toast("请选择提现方式！")
                    return@setOnClickListener
                }
                mPresent.getDataByPost(0x1,
                        RequestParamsHelper.MEMBER_MODEL,
                        RequestParamsHelper.ACT_MY_WITHDRAW_OPERATION,
                        RequestParamsHelper.getMyWithdrawOperationParam(inputMoney,"$cashType"))
            } catch (e: Exception) {
                toast("请输入正确的提现金额！")
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresent.getDataByPost(0x0, RequestParamsHelper.MEMBER_MODEL, RequestParamsHelper.ACT_MY_WITHDRAW, RequestParamsHelper.getMyWithdrawParam())
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        if (requestID == 0x0){
            getFastProgressDialog("正在加载数据……")
        }else if (requestID == 0x1){
            getFastProgressDialog("正在提交……")
        }
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        if (requestID == 0x0) {
            val check = checkResultCode(result)
            if (check != null && check.code == SUCCESS_CODE) {
                val resultJson = (check.obj as JSONObject).optJSONObject("result")
                val allMoney = resultJson.optString("member_withdrawal")
                this.allMoney = allMoney.toDouble()
                mTvMineCashAllMoney.text = allMoney
                mBtMineCashMoneySubmit.isEnabled = !TextUtils.isEmpty(allMoney) && allMoney.toDouble() > 0.0
                mEtMineCashMoney.isEnabled = mBtMineCashMoneySubmit.isEnabled
                mTvMineCashAlreadyCashMoney.text = resultJson.optString("member_already_price")
            }
        }else{

        }
    }

    override fun onRequestFail(requestID: Int, e: Throwable) {
        super.onRequestFail(requestID, e)
        if(requestID == 0x1){
            toast("提现失败，请稍后重试……")
        }else if (requestID == 0x0){
            toast("获取信息失败，请稍后重试……")
        }
    }
}