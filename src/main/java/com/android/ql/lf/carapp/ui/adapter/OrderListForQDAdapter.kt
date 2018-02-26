package com.android.ql.lf.carapp.ui.adapter

import android.content.Context
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.text.Html
import android.view.View
import android.view.ViewGroup
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.OrderBean
import com.android.ql.lf.carapp.ui.views.EasyCountDownTextureView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.android.ql.lf.carapp.ui.adapter.OrderListForQDAdapter.MyViewHolder
import android.util.SparseArray
import android.view.LayoutInflater
import java.util.*
import cn.iwgang.countdownview.CountdownView
import com.android.ql.lf.carapp.application.CarApplication


/**
 * Created by lf on 18.1.25.
 * @author lf on 18.1.25
 */
class OrderListForQDAdapter(var context: Context,var layoutId: Int, var list: ArrayList<OrderBean>) : BaseQuickAdapter<OrderBean, BaseViewHolder>(layoutId, list) {

    private var mCountdownVHList: SparseArray<MyViewHolder>? = null
    private val mHandler = Handler()
    private var mTimer: Timer? = null
    private var isCancel = true

    init {
        mCountdownVHList = SparseArray()
    }

    override fun convert(helper: BaseViewHolder?, item: OrderBean?) {
        (helper as MyViewHolder).bindData(item!!)
    }

//    fun startRefreshTime() {
//        if (!isCancel) return
//        mTimer?.cancel()
//        isCancel = false
//        mTimer = Timer()
//        mTimer!!.schedule(object :TimerTask(){
//            override fun run() {
//                mHandler.post(mRefreshTimeRunnable)
//            }
//        },0,10)
//    }

//    fun cancelRefreshTime() {
//        isCancel = true
//        if (null != mTimer) {
//            mTimer!!.cancel()
//        }
//        mHandler.removeCallbacks(mRefreshTimeRunnable)
//    }

    override fun onCreateDefViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(layoutId,parent,false))
    }

    override fun onViewRecycled(holder: BaseViewHolder?) {
        super.onViewRecycled(holder)
    }

    class MyViewHolder(var view:View) : BaseViewHolder(view){

        private var orderBean:OrderBean? = null

        private val mCvCountdownView by lazy {
            view.findViewById<CountdownView>(R.id.mCvOrderListForQDItemTime)
        }

        fun bindData(orderBean: OrderBean){
            this.orderBean = orderBean
            addOnClickListener(R.id.mBtOrderListForQDItem)
            setText(R.id.mTvOrderListForQDItemName, orderBean.qorder_name)
            setText(R.id.mTvOrderListForQDItemCountTime, orderBean.qorder_jendtime)
            setText(R.id.mTvOrderListForQDItemTime, orderBean.qorder_time ?: "暂无")
            setText(R.id.mTvOrderListForQDItemTitle, Html.fromHtml("<font color='${ContextCompat.getColor(CarApplication.application, R.color.colorPrimary)}'>项目：</font>${orderBean.qorder_project}"))
            setText(R.id.mTvOrderListForQDItemMoney, "￥${orderBean.qorder_price}")
            setText(R.id.mTvOrderListForQDItemContent, Html.fromHtml("<font color='${ContextCompat.getColor(CarApplication.application, R.color.colorPrimary)}'>备注：</font>${orderBean.qorder_content}"))
        }
    }
}