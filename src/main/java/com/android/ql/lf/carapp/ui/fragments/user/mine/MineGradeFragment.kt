package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.UserInfo
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import kotlinx.android.synthetic.main.fragment_mine_grade_layout.*

/**
 * Created by liufeng on 2018/2/1.
 */
class MineGradeFragment : BaseNetWorkingFragment() {

    private val gradePair by lazy {
        mapOf(Pair("0", "兵")
                , Pair("1", "班")
                , Pair("2", "排")
                , Pair("3", "连")
                , Pair("4", "营")
                , Pair("5", "团")
                , Pair("6", "旅")
                , Pair("7", "师")
                , Pair("8", "军")
        )
    }

    override fun getLayoutId() = R.layout.fragment_mine_grade_layout

    override fun initView(view: View?) {
        val grade = gradePair[UserInfo.getInstance().memberRank]
        mTvMineGradeCurrentGrade.text = "会员当前等级：$grade"
        mTvMineGradeGrade.text = grade
        mTvMineGradeComment.text = UserInfo.getInstance().memberGrade
        mTvMineGradeOrderNum.text = UserInfo.getInstance().memberOrderNum
    }
}