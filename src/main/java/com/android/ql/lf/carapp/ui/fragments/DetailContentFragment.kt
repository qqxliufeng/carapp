package com.android.ql.lf.carapp.ui.fragments

import android.os.Bundle
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.utils.ApiParams

/**
 * Created by liufeng on 2018/2/22.
 */
class DetailContentFragment : BaseNetWorkingFragment() {

    companion object {
        val MODEL_NAME_FLAG = "model_name"
        val ACT_NAME_FLAG = "act_name"
        val PARAM_FLAG = "params"
    }

    override fun getLayoutId() = R.layout.fragment_detail_content_layout

    override fun initView(view: View?) {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mapParams = arguments.getSerializable(PARAM_FLAG) as HashMap<String,String>
        val apiParam = ApiParams()
        mapParams.forEach {
            apiParam.addParam(it.key,it.value)
        }
        mPresent.getDataByPost(0x0,
                arguments.getString(MODEL_NAME_FLAG),
                arguments.getString(ACT_NAME_FLAG),
                apiParam)
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        getFastProgressDialog("正在加载……")
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        val check = checkResultCode(result)
        if (check != null) {
            if (SUCCESS_CODE == check.code) {

            }
        }
    }

}