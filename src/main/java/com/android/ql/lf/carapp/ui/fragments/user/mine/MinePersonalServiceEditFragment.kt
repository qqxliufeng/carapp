package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.app.TimePickerDialog
import android.support.design.widget.BottomSheetDialog
import android.view.View
import android.widget.TimePicker
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.UserInfo
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import com.android.ql.lf.carapp.utils.toast
import kotlinx.android.synthetic.main.fragment_mine_personal_service_layout.*
import java.util.*

/**
 * Created by liufeng on 2018/2/1.
 */
class MinePersonalServiceEditFragment : BaseNetWorkingFragment() {

    private var startTime: String? = null
    private var endTime: String? = null

    override fun getLayoutId() = R.layout.fragment_mine_personal_service_layout

    override fun initView(view: View?) {
        mEtMinePersonalServicePhone.setText(UserInfo.getInstance().memberPhone)
        mEtMinePersonalServiceAddress.setText(UserInfo.getInstance().memberAddress)
        mTvServiceEditWorkRange.setOnClickListener {
        }
        mTvServiceEditWorkStartTime.setOnClickListener {
            val timePicker = TimePickerDialog(mContext, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                startTime = if (minute == 0) "${hourOfDay}00" else {
                    "$hourOfDay$minute"
                }
                if (endTime != null) {
                    if (startTime!!.toInt() >= endTime!!.toInt()) {
                        toast("结束时间必须大于开始时间")
                        return@OnTimeSetListener
                    }
                }
                mTvServiceEditWorkStartTime.text = if (minute == 0) "$hourOfDay:00" else {
                    "$hourOfDay:$minute"
                }
            }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true)
            timePicker.setTitle("请选择开始时间")
            timePicker.show()
        }
        mTvServiceEditWorkEndTime.setOnClickListener {
            val timePicker = TimePickerDialog(mContext, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                endTime = if (minute == 0) "${hourOfDay}00" else {
                    "$hourOfDay$minute"
                }
                if (startTime != null) {
                    if (startTime!!.toInt() >= endTime!!.toInt()) {
                        toast("结束时间必须大于开始时间")
                        return@OnTimeSetListener
                    }
                }
                mTvServiceEditWorkEndTime.text = if (minute == 0) "$hourOfDay:00" else {
                    "$hourOfDay:$minute"
                }
            }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true)
            timePicker.setTitle("请选择结束时间")
            timePicker.show()
        }
        mPresent.getDataByPost(0x0,RequestParamsHelper.MEMBER_MODEL,RequestParamsHelper.ACT_PERSONAL_SERVICE,RequestParamsHelper.getPersonalServiceParam())
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        if (requestID == 0x0){
            getFastProgressDialog("正在加载……")
        }
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
    }
}