package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.support.design.widget.BottomSheetDialog
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carapp.ui.views.SelectPayTypeView
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.json.JSONObject

/**
 * Created by liufeng on 2018/2/4.
 */
class MineEnsureMoneyFragment : BaseRecyclerViewFragment<MineEnsureMoneyFragment.EnsureMoneyProduct>() {

    private lateinit var mTvEnsureMoneyIntroduce: TextView

    private var bottomPayDialog: BottomSheetDialog? = null

    override fun createAdapter(): BaseQuickAdapter<EnsureMoneyProduct, BaseViewHolder> =
            EnsureMoneyProductAdapter(R.layout.adapter_ensure_money_item_layout, mArrayList)

    override fun initView(view: View?) {
        super.initView(view)
        val footView = View.inflate(mContext, R.layout.fragment_mine_ensure_money_layout, null)
        mTvEnsureMoneyIntroduce = footView.findViewById(R.id.mTvEnsureMoneyIntroduce)
        mBaseAdapter.addFooterView(footView)
//        mLlMineEnsureMoneyContainer.minimumHeight = screenSize.height - statusBarHeight - actionBarHeight
//        mBtMineEnsureMoneyBack.setOnClickListener {
//            val dialog = Dialog(mContext)
//            dialog.window.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(mContext, android.R.color.transparent)))
//            val contentView = View.inflate(mContext,R.layout.dialog_ensure_money_back_layout,null)
//            val iv_close = contentView.findViewById<ImageView>(R.id.mIvEnsureMoneyBackClose)
//            iv_close.setColorFilter(Color.parseColor("#555555"))
//            iv_close.setOnClickListener{
//                dialog.dismiss()
//            }
//            dialog.setContentView(contentView)
//            dialog.show()
//        }
    }

    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0, RequestParamsHelper.MEMBER_MODEL, RequestParamsHelper.ACT_M_P, RequestParamsHelper.getEnsureMoneyProductParam())
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        processList(result as String, EnsureMoneyProduct::class.java)
        setLoadEnable(false)
        val check = checkResultCode(result)
        if (check != null && check.code == SUCCESS_CODE) {
            val arrJson = (check.obj as JSONObject).optJSONObject("arr")
            mTvEnsureMoneyIntroduce.text = Html.fromHtml(arrJson.optString("ptgg_content"))
        }
    }

    override fun onMyItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        super.onMyItemChildClick(adapter, view, position)
        val item = mArrayList[position]
        if (view!!.id == R.id.mBtEnsureMoneyProductAction) {
            when (item.m_p_type) {
                "1" -> {
                    if (item.member_ismaster_ensure_money == "0") {
                        //缴纳师傅保证金
                        if (bottomPayDialog == null) {
                            bottomPayDialog = BottomSheetDialog(mContext)
                            val contentView = SelectPayTypeView(mContext)
                            contentView.setShowConfirmView(View.VISIBLE)
                            contentView.setOnConfirmClickListener {
                                bottomPayDialog!!.dismiss()
                            }
                            bottomPayDialog!!.setContentView(contentView)
                        }
                        bottomPayDialog!!.show()
                    } else {
                        //退师傅保证金
                    }
                }
                "2" -> {
                    if (item.member_ismerchant_ensure_money == "0") {
                        //缴纳店铺保证金
                        if (bottomPayDialog == null) {
                            bottomPayDialog = BottomSheetDialog(mContext)
                            val contentView = SelectPayTypeView(mContext)
                            contentView.setShowConfirmView(View.VISIBLE)
                            contentView.setOnConfirmClickListener {
                                bottomPayDialog!!.dismiss()
                            }
                            bottomPayDialog!!.setContentView(contentView)
                        }
                        bottomPayDialog!!.show()
                    } else {
                        //退店铺保证金
                    }
                }
            }
        }
    }

    class EnsureMoneyProductAdapter(layout: Int, list: ArrayList<EnsureMoneyProduct>) : BaseQuickAdapter<EnsureMoneyProduct, BaseViewHolder>(layout, list) {
        override fun convert(helper: BaseViewHolder?, item: EnsureMoneyProduct?) {
            helper!!.setText(R.id.mTvEnsureMoneyProductTitle, item!!.m_p_name)
            helper.setText(R.id.mTvEnsureMoneyProductCount, "￥${item.m_p_price}")
            val bt_action = helper.getView<Button>(R.id.mBtEnsureMoneyProductAction)
            helper.addOnClickListener(R.id.mBtEnsureMoneyProductAction)
            when (item.m_p_type) {
                "1" -> {
                    if (item.member_ismaster_ensure_money == "0") {
                        bt_action.text = "去缴纳"
                    } else {
                        bt_action.text = "去退款"
                    }
                }
                "2" -> {
                    if (item.member_ismerchant_ensure_money == "0") {
                        bt_action.text = "去缴纳"
                    } else {
                        bt_action.text = "去退款"
                    }
                }
            }
        }
    }

    class EnsureMoneyProduct {
        var m_p_id: String? = null
        var m_p_name: String? = null
        var m_p_content: String? = null
        var m_p_yprice: String? = null
        var m_p_price: String? = null
        var m_p_num: String? = null
        var m_p_type: String? = null
        var member_ismaster_ensure_money: String? = null
        var member_ismerchant_ensure_money: String? = null
    }

}