package com.android.ql.lf.carapp.ui.fragments.order

import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.UserInfo
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.adapter.OrderListForQDAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carapp.ui.fragments.user.LoginFragment
import com.android.ql.lf.carapp.utils.toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_order_for_qd_layout.*

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

    override fun getLayoutId() = R.layout.fragment_order_for_qd_layout

    override fun createAdapter(): BaseQuickAdapter<String, BaseViewHolder>
            = OrderListForQDAdapter(R.layout.adapter_order_list_for_qd_item_layout, mArrayList)

    override fun initView(view: View?) {
        super.initView(view)
        registerLoginSuccessBus()
        showNotify()
        testAdd("111")
    }

    override fun onLoginSuccess(userInfo: UserInfo?) {
        showNotify()
    }

    private fun showNotify() {
        if (!mArrayList.isEmpty()) {
            if (UserInfo.getInstance().isLogin) {
                mTvOrderQDNotify.requestFocus()
                mTvOrderQDNotify.isSelected = true
                mTvOrderQDNotify.visibility = View.VISIBLE
                if (!UserInfo.getInstance().isMaster) {
                    mTvOrderQDNotify.text = "您当前帐号未认证成为维修师傅，暂无法接单，请立即认证"
                } else if (!UserInfo.getInstance().isPayEnsureMoney) {
                    mTvOrderQDNotify.text = "您还没有交纳保证金，暂无法接单，请立即交纳"
                }
                mBaseAdapter.notifyDataSetChanged()
            } else {
                mTvOrderQDNotify.visibility = View.GONE
            }
        } else {
            mTvOrderQDNotify.visibility = View.GONE
        }
    }

    override fun getEmptyMessage(): String {
        return "这里还没有订单呢！"
    }

    override fun getEmptyLayoutId() = R.layout.layout_order_list_empty

    override fun onRefresh() {
//        super.onRefresh()
        onRequestEnd(1)
//        setEmptyView()
//        val tempList = arrayListOf<String>()
//        (0 .. 2).forEach {
//            tempList.add("item new $it")
//        }
//        mBaseAdapter.addData(0, tempList)
//        mRecyclerView.smoothScrollToPosition(0)
    }


    override fun onMyItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if (view!!.id == R.id.mBtOrderListForQDItem) {
            if (!UserInfo.getInstance().isLogin) {
                FragmentContainerActivity
                        .from(mContext)
                        .setClazz(LoginFragment::class.java)
                        .setTitle("登录")
                        .setHiddenToolBar(false)
                        .setNeedNetWorking(true)
                        .start()
            }
        }
    }


    private fun showOrderNotifyDialog() {

        FragmentContainerActivity
                .from(mContext)
                .setClazz(LoginFragment::class.java)
                .setTitle("登录")
                .setHiddenToolBar(false)
                .setNeedNetWorking(true)
                .start()

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


//        val dialog = Dialog(mContext)
//        dialog.setCancelable(true)
//        dialog.setCanceledOnTouchOutside(false)
//        dialog.window.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(mContext, android.R.color.transparent)))
//        val contentView = View.inflate(mContext, R.layout.dialog_invite_code_layout, null)
////        val tv_time_count = contentView.findViewById<TextView>(R.id.mTvOrderNotifyDialogTimeCount)
////        val spannableString = SpannableString("5s")
//
//        dialog.setContentView(contentView)
//        dialog.show()


    }
}