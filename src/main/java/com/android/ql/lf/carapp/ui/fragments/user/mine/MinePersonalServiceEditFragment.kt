package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.app.TimePickerDialog
import android.view.View
import android.widget.TimePicker
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import kotlinx.android.synthetic.main.fragment_mine_personal_service_layout.*
import java.util.*

/**
 * Created by liufeng on 2018/2/1.
 */
class MinePersonalServiceEditFragment :BaseNetWorkingFragment(){

    override fun getLayoutId() = R.layout.fragment_mine_personal_service_layout

    override fun initView(view: View?) {
        mTvServiceEditWorkRange.setOnClickListener {
            val timePicker = TimePickerDialog(mContext, TimePickerDialog.OnTimeSetListener { p0, p1, p2 -> },Calendar.HOUR_OF_DAY,Calendar.MINUTE,true)
            timePicker.setTitle("请选择开始时间")
            timePicker.show()
        }
    }
}