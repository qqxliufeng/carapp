package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.UserInfo
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.utils.Constants
import com.android.ql.lf.carapp.utils.QRCodeUtil
import kotlinx.android.synthetic.main.fragment_mine_q_code_layout.*

/**
 * Created by lf on 18.2.2.
 * @author lf on 18.2.2
 */
class MineQCodeFragment : BaseNetWorkingFragment() {

    override fun getLayoutId() = R.layout.fragment_mine_q_code_layout

    override fun initView(view: View?) {
        mTvMineInviteCode.text = UserInfo.getInstance().memberMyInviteCode
        val address = "${Constants.BASE_IP}id=${UserInfo.getInstance().memberId}&code=${UserInfo.getInstance().memberMyInviteCode}"
        mIvMineQCode.setImageBitmap(QRCodeUtil.createQRCodeBitmap(address, 500, 500))
    }
}