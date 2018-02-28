package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.UserInfo
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.json.JSONObject

/**
 * Created by liufeng on 2018/2/4.
 */
class MineEnsureMoneyFragment : BaseRecyclerViewFragment<MineEnsureMoneyFragment.EnsureMoneyProduct>() {

    private lateinit var mTvProductIntruduce: TextView

    override fun createAdapter(): BaseQuickAdapter<EnsureMoneyProduct, BaseViewHolder> =
            EnsureMoneyProductAdapter(R.layout.adapter_ensure_money_item_layout, mArrayList)

    override fun initView(view: View?) {
        super.initView(view)
        val footView = View.inflate(mContext, R.layout.fragment_mine_ensure_money_layout, null)
        mTvProductIntruduce = footView.findViewById<TextView>(R.id.mTvEnsureMoneyIntrduce)
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
            mTvProductIntruduce.text = Html.fromHtml(arrJson.optString("ptgg_content"))
        }
    }

    class EnsureMoneyProductAdapter(layout: Int, list: ArrayList<EnsureMoneyProduct>) : BaseQuickAdapter<EnsureMoneyProduct, BaseViewHolder>(layout, list) {
        override fun convert(helper: BaseViewHolder?, item: EnsureMoneyProduct?) {
            helper!!.setText(R.id.mTvEnsureMoneyProductTitle, item!!.m_p_name)
            helper.setText(R.id.mTvEnsureMoneyProductCount, "￥${item.m_p_price}")
            val bt_action = helper.getView<Button>(R.id.mBtEnsureMoneyProductAction)
            if (item.member_ismaster_ensure_money == "0"){

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