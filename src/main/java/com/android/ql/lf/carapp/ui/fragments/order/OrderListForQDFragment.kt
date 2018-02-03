package com.android.ql.lf.carapp.ui.fragments.order

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.adapter.OrderListForQDAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carapp.ui.fragments.user.LoginFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.1.24.
 * @author lf on 18.1.24
 */
class OrderListForQDFragment : BaseRecyclerViewFragment<String>() {

    companion object {
        fun newInstance(): OrderListForQDFragment {
            return OrderListForQDFragment()
        }
    }

    override fun createAdapter(): BaseQuickAdapter<String, BaseViewHolder>
            = OrderListForQDAdapter(R.layout.adapter_order_list_for_qd_item_layout, mArrayList)

    override fun getEmptyMessage(): String {
        return "这里还没有订单呢！"
    }

    override fun getEmptyLayoutId() = R.layout.layout_order_list_empty

    override fun onRefresh() {
//        super.onRefresh()
        testAdd("")
    }

    override fun onMyItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemClick(adapter, view, position)
        showOrderNotifyDialog()
    }

    private fun showOrderNotifyDialog() {

//        FragmentContainerActivity.startFragmentContainerActivity(mContext,"登录",true,false,LoginFragment::class.java)

//        val dialog = Dialog(mContext)
//        dialog.setCancelable(true)
//        dialog.setCanceledOnTouchOutside(false)
//        dialog.window.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(mContext, android.R.color.transparent)))
//        val contentView = View.inflate(mContext,R.layout.dialog_order_notify_layout,null)
////        val tv_time_count = contentView.findViewById<TextView>(R.id.mTvOrderNotifyDialogTimeCount)
////        val spannableString = SpannableString("5s")
//
//        dialog.setContentView(contentView)
//        dialog.show()


        val dialog = Dialog(mContext)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)
        dialog.window.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(mContext, android.R.color.transparent)))
        val contentView = View.inflate(mContext, R.layout.dialog_invite_code_layout, null)
//        val tv_time_count = contentView.findViewById<TextView>(R.id.mTvOrderNotifyDialogTimeCount)
//        val spannableString = SpannableString("5s")

        dialog.setContentView(contentView)
        dialog.show()


    }
}