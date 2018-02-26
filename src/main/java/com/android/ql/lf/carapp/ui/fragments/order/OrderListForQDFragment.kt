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
import com.android.ql.lf.carapp.ui.fragments.bottom.MainOrderHouseFragment
import com.android.ql.lf.carapp.ui.fragments.user.LoginFragment
import com.android.ql.lf.carapp.ui.fragments.user.mine.MineApplyMasterInfoSubmitFragment
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import com.android.ql.lf.carapp.utils.RxBus
import com.android.ql.lf.carapp.utils.toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_order_for_qd_layout.*
import org.json.JSONObject

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

    private var currentOrderBean: OrderBean? = null

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
            = OrderListForQDAdapter(mContext,R.layout.adapter_order_list_for_qd_item_layout, mArrayList)

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
        mArrayList.clear()
        mBaseAdapter.notifyDataSetChanged()
    }

    private fun showNotify() {
        if (UserInfo.getInstance().isLogin) {
            if (UserInfo.getInstance().isMaster) {
                mTvOrderQDNotify.visibility = View.GONE
                mBaseAdapter.notifyDataSetChanged()
                return
            }
            mTvOrderQDNotify.visibility = View.VISIBLE
            when (UserInfo.getInstance().authenticationStatus) {
                0 -> {
                    mTvOrderQDNotify.text = "当前帐号正在认证中……"
                }
                2 -> {
                    mTvOrderQDNotify.text = "审核失败，请重新提交资料……"
                    mTvOrderQDNotify.setOnClickListener {
                        FragmentContainerActivity.from(mContext).setTitle("申请成为师傅").setNeedNetWorking(true).setClazz(MineApplyMasterInfoSubmitFragment::class.java).start()
                    }
                }
                3 -> {
                    mTvOrderQDNotify.text = "当前帐号未认证，暂无法接单，请立即认证"
                    mTvOrderQDNotify.setOnClickListener {
                        FragmentContainerActivity.from(mContext).setTitle("申请成为师傅").setNeedNetWorking(true).setClazz(MineApplyMasterInfoSubmitFragment::class.java).start()
                    }
                }
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

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        if (requestID == 0x1) {
            getFastProgressDialog("正在抢单……")
        }
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        if (requestID == 0x0) {
            processList(result as String, OrderBean::class.java)
            val check = checkResultCode(result)
            if (check != null) {
                val arrInfo = (check.obj as JSONObject).optJSONObject("arr")
                if (arrInfo != null) {
                    val address = arrInfo.optString("shop_address")
                    (parentFragment as MainOrderHouseFragment).updateAddress(address)
                }
            }
        } else if (requestID == 0x1) {
            val check = checkResultCode(result)
            if (check != null && check.code == SUCCESS_CODE) {
                toast("恭喜，抢单成功，祝您工作愉快！")
                serviceOrderPresent.updateOrderStatus(1)
            } else {
                toast("该订单已被抢了~~")
            }
            val position = mArrayList.indexOf(currentOrderBean)
            mArrayList.remove(currentOrderBean)
            mBaseAdapter.notifyItemRemoved(position)
        }
    }

    override fun onRequestFail(requestID: Int, e: Throwable) {
        super.onRequestFail(requestID, e)
        if (requestID == 0x1) {
            toast("抢单失败")
        }
    }

    override fun onMyItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        if (view!!.id == R.id.mBtOrderListForQDItem) {
            when (UserInfo.getInstance().authenticationStatus) {
                0 -> {
                    mTvOrderQDNotify.text = "当前帐号正在认证中……"
                }
                1 -> {
                    if (UserInfo.getInstance().isMaster) {
                        currentOrderBean = mArrayList[position]
                        mPresent.getDataByPost(0x1, RequestParamsHelper.ORDER_MODEL, RequestParamsHelper.ACT_ORDER_RECEIVING, RequestParamsHelper.getOrderReceivingParam(currentOrderBean!!.qorder_id))
                    }
                }
                2 -> {
                    mTvOrderQDNotify.text = "审核失败，请重新提交资料……"
                    mTvOrderQDNotify.setOnClickListener {
                        FragmentContainerActivity.from(mContext).setTitle("申请成为师傅").setNeedNetWorking(true).setClazz(MineApplyMasterInfoSubmitFragment::class.java).start()
                    }
                }
                3 -> {
                    mTvOrderQDNotify.text = "当前帐号未认证，暂无法接单，请立即认证"
                    mTvOrderQDNotify.setOnClickListener {
                        FragmentContainerActivity.from(mContext).setTitle("申请成为师傅").setNeedNetWorking(true).setClazz(MineApplyMasterInfoSubmitFragment::class.java).start()
                    }
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