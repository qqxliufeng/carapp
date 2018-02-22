package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import kotlinx.android.synthetic.main.fragment_mine_ensure_money_layout.*

/**
 * Created by liufeng on 2018/2/4.
 */
class MineEnsureMoneyFragment :BaseNetWorkingFragment(){

    override fun getLayoutId() = R.layout.fragment_mine_ensure_money_layout

    override fun initView(view: View?) {
        mLlMineEnsureMoneyContainer.minimumHeight = screenSize.height - statusBarHeight - actionBarHeight
        mBtMineEnsureMoneyBack.setOnClickListener {
            val dialog = Dialog(mContext)
            dialog.window.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(mContext, android.R.color.transparent)))
            val contentView = View.inflate(mContext,R.layout.dialog_ensure_money_back_layout,null)
            val iv_close = contentView.findViewById<ImageView>(R.id.mIvEnsureMoneyBackClose)
            iv_close.setColorFilter(Color.parseColor("#555555"))
            iv_close.setOnClickListener{
                dialog.dismiss()
            }
            dialog.setContentView(contentView)
            dialog.show()
        }
    }
}