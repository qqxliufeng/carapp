package com.android.ql.lf.carapp.ui.fragments.order

import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.EventIsMasterAndMoneyBean
import com.android.ql.lf.carapp.data.OrderBean
import com.android.ql.lf.carapp.data.UserInfo
import com.android.ql.lf.carapp.present.ServiceOrderPresent
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.adapter.OrderListForQDAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carapp.ui.fragments.user.LoginFragment
import com.android.ql.lf.carapp.ui.fragments.user.mine.MineApplyMasterInfoSubmitFragment
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import com.android.ql.lf.carapp.utils.RxBus
import com.android.ql.lf.carapp.utils.toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_order_for_qd_layout.*

/**
 * Created by lf on 18.1.24.
 * @author lf on 18.1.24
 */
class OrderListForQDFragment : BaseRecyclerViewFragment<OrderBean>() {

    companion object {

        val RECEIVER_ORDER_FLAG = "receiver order"

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

    override fun createAdapter(): BaseQuickAdapter<OrderBean, BaseViewHolder>
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
        onPostRefresh()
    }

    private fun onLogoutSuccess() {
        showNotify()
        mBaseAdapter.notifyDataSetChanged()
    }

    private fun showNotify() {
        if (UserInfo.getInstance().isLogin) {
            if (UserInfo.getInstance().isMaster) {
                mTvOrderQDNotify.visibility = View.GONE
                mBaseAdapter.notifyDataSetChanged()
                return
            }
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
            mTvOrderQDNotify.visibility = View.VISIBLE
            if (!UserInfo.getInstance().isMaster) {
                mTvOrderQDNotify.text = "当前帐号未认证，暂无法接单，请立即认证"
            }
            if (UserInfo.getInstance().isCheckingMaster) {
                mTvOrderQDNotify.text = "当前帐号正在认证中……"
            }
            mBaseAdapter.notifyDataSetChanged()
        } else {
            mTvOrderQDNotify.visibility = View.VISIBLE
            mTvOrderQDNotify.text = "登录后显示订单"
            mTvOrderQDNotify.setOnClickListener {
                UserInfo.loginToken = RECEIVER_ORDER_FLAG
                LoginFragment.startLogin(mContext)
            }
        }
    }

    override fun getEmptyMessage(): String {
        return "这里还没有订单呢！"
    }

    override fun getEmptyLayoutId() = R.layout.layout_order_list_empty

    override fun onRefresh() {
        super.onRefresh()
        if (UserInfo.getInstance().isLogin) {
            mPresent.getDataByPost(0x0, RequestParamsHelper.ORDER_MODEL, RequestParamsHelper.ACT_QORDER, RequestParamsHelper.getQorderParam(page = currentPage))
        } else {
            showNotify()
            onRequestFail(-1, IllegalStateException("状态异常"))
            onRequestEnd(-1)
        }
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mPresent.getDataByPost(0x0, RequestParamsHelper.ORDER_MODEL, RequestParamsHelper.ACT_QORDER, RequestParamsHelper.getQorderParam(page = currentPage))
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        processList(result as String, OrderBean::class.java)
    }

    override fun onMyItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if (view!!.id == R.id.mBtOrderListForQDItem) {
            if (UserInfo.getInstance().isCheckingMaster) {
                toast("正在认证中，成功后方可接单……")
            } else {
                if (!UserInfo.getInstance().isMaster) {
                    FragmentContainerActivity.from(mContext).setTitle("申请成为师傅").setNeedNetWorking(true).setClazz(MineApplyMasterInfoSubmitFragment::class.java).start()
                }
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
        unsubscribe(masterAndMoneySubscription)
        unsubscribe(userLogoutSubscription)
        super.onDestroyView()
    }

}