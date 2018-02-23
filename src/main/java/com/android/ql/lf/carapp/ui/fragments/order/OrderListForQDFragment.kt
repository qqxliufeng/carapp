package com.android.ql.lf.carapp.ui.fragments.order

import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.EventIsMasterAndMoneyBean
import com.android.ql.lf.carapp.data.UserInfo
import com.android.ql.lf.carapp.present.ServiceOrderPresent
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.adapter.OrderListForQDAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carapp.ui.fragments.user.LoginFragment
import com.android.ql.lf.carapp.ui.fragments.user.mine.MineApplyMasterInfoSubmitFragment
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import com.android.ql.lf.carapp.utils.RxBus
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

    private val serviceOrderPresent by lazy {
        ServiceOrderPresent()
    }

    //接收是否谁为师傅和是否交纳保证金的事件
    private val masterAndMoneySubscription by lazy {
        RxBus.getDefault().toObservable(EventIsMasterAndMoneyBean::class.java).subscribe {
            showNotify()
        }
    }

    //接收退出登录事件
    private val userLogoutSubscription by lazy {
        RxBus.getDefault().toObservable(String::class.java).subscribe {
            if (it == UserInfo.LOGOUT_FLAG) {
                onLogoutSuccess()
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_order_for_qd_layout

    override fun createAdapter(): BaseQuickAdapter<String, BaseViewHolder>
            = OrderListForQDAdapter(R.layout.adapter_order_list_for_qd_item_layout, mArrayList)

    override fun initView(view: View?) {
        super.initView(view)
        registerLoginSuccessBus()
        masterAndMoneySubscription
        userLogoutSubscription
        showNotify()
    }

    override fun onLoginSuccess(userInfo: UserInfo?) {
        showNotify()
    }

    private fun onLogoutSuccess() {
        showNotify()
        mBaseAdapter.notifyDataSetChanged()
    }

    private fun showNotify() {
        if (!mArrayList.isEmpty()) {
            if (UserInfo.getInstance().isLogin) {
                if (UserInfo.getInstance().isPayEnsureMoney && UserInfo.getInstance().isMaster) {
                    mTvOrderQDNotify.visibility = View.GONE
                    mBaseAdapter.notifyDataSetChanged()
                    return
                }
                mTvOrderQDNotify.visibility = View.VISIBLE
                mTvOrderQDNotify.setOnClickListener {
                    if (!UserInfo.getInstance().isMaster) {
                        FragmentContainerActivity.from(mContext).setTitle("申请成为师傅").setNeedNetWorking(true).setClazz(MineApplyMasterInfoSubmitFragment::class.java).start()
                        return@setOnClickListener
                    }
                    if (!UserInfo.getInstance().isPayEnsureMoney) {
                        serviceOrderPresent.doAuthEnsureMoney()
                        return@setOnClickListener
                    }
                }
                if (!UserInfo.getInstance().isMaster) {
                    mTvOrderQDNotify.text = "当前帐号未认证，暂无法接单，请立即认证"
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
        super.onRefresh()
        mPresent.getDataByPost(0x0,RequestParamsHelper.ORDER_MODEL,RequestParamsHelper.ACT_QORDER,RequestParamsHelper.getQorderParam(""))
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

    override fun onDestroyView() {
        if (!masterAndMoneySubscription.isUnsubscribed) {
            masterAndMoneySubscription.unsubscribe()
        }
        if (userLogoutSubscription != null && userLogoutSubscription.isUnsubscribed) {
            userLogoutSubscription.unsubscribe()
        }
        super.onDestroyView()
    }

}