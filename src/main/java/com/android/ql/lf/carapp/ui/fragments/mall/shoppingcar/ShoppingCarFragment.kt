package com.android.ql.lf.carapp.ui.fragments.mall.shoppingcar

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.RefreshData
import com.android.ql.lf.carapp.data.ShoppingCarItemBean
import com.android.ql.lf.carapp.ui.adapter.ShoppingCarItemAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import com.android.ql.lf.carapp.utils.RxBus
import com.android.ql.lf.carapp.utils.toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.fragment_shopping_car_layout.*
import org.json.JSONObject
import java.text.DecimalFormat

/**
 * Created by lf on 2017/11/8 0008.
 * @author lf on 2017/11/8 0008
 */
class ShoppingCarFragment : BaseRecyclerViewFragment<ShoppingCarItemBean>() {


    companion object {
        val REFRESH_SHOPPING_CAR_FLAG = "refresh shopping car"
    }

    private lateinit var currentItem: ShoppingCarItemBean

    private var currentEditMode = 1// 1 减  0  加

    private val selectedList = ArrayList<ShoppingCarItemBean>()

    override fun getLayoutId(): Int = R.layout.fragment_shopping_car_layout

    override fun createAdapter(): BaseQuickAdapter<ShoppingCarItemBean, BaseViewHolder> =
            ShoppingCarItemAdapter(R.layout.adapter_shopping_car_item_layout, mArrayList)

    @SuppressLint("RestrictedApi")
    override fun initView(view: View?) {
        super.initView(view)
        subscription = RxBus.getDefault().toObservable(RefreshData::class.java).subscribe {
            if (RefreshData.isRefresh && RefreshData.any == REFRESH_SHOPPING_CAR_FLAG) {
                onPostRefresh()
            }
        }
        mShoppingCarContainer.visibility = View.VISIBLE
        mCalculate.isEnabled = false
        setRefreshEnable(false)
        mLlShoppingCarAllSelectContainer.setOnClickListener {
            mCivShoppingCarAllSelect.isChecked = !mCivShoppingCarAllSelect.isChecked
            var money = 0.00F
            mArrayList.forEach { it.isSelector = mCivShoppingCarAllSelect.isChecked }
            money = calculatePrice(money)
            mTvShoppingCarAllSelectMoney.text = "￥${DecimalFormat("0.00").format(money)}"
            mCalculate.isEnabled = money != 0.00f
            mBaseAdapter.notifyDataSetChanged()
        }
        mCalculate.setOnClickListener {
            selectedList.clear()
            selectedList.addAll(mArrayList.filter { it.isSelector } as ArrayList<ShoppingCarItemBean>)
//            val bundle = Bundle()
//            bundle.putParcelableArrayList(SubmitNewOrderFragment.Companion.GOODS_ID_FLAG, selectedList)
//            FragmentContainerActivity.startFragmentContainerActivity(mContext, "确认订单", true, false, bundle, SubmitNewOrderFragment::class.java)
        }
    }

    private fun calculatePrice(money: Float): Float {
        var money1 = money
        mArrayList.forEach {
            if (it.isSelector) {
                money1 += (it.shopcart_price.toFloat() * it.shopcart_num.toInt())
            }
        }
        return money1
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        val itemDecoration: DividerItemDecoration = super.getItemDecoration() as DividerItemDecoration
        itemDecoration.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.recycler_view_height_divider))
        return itemDecoration
    }

    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0, RequestParamsHelper.Companion.MEMBER_MODEL,
                RequestParamsHelper.ACT_SHOPCART, RequestParamsHelper.getShopcartParam(currentPage))
    }

    override fun onLoadMore() {
        super.onLoadMore()
        setLoadEnable(false)
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        if (requestID == 0x1) {
            getFastProgressDialog("正在删除……")
        } else if (requestID == 0x2) {
            mPbShoppingCarItem.visibility = View.VISIBLE
        }
    }

    override fun onRequestEnd(requestID: Int) {
        super.onRequestEnd(requestID)
        if (requestID == 0x2) {
            mPbShoppingCarItem.visibility = View.GONE
        }
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        val baseNetResult = checkResultCode(result)
        if (baseNetResult.code == SUCCESS_CODE) {
            if (requestID == 0x0) {
                if (baseNetResult != null && (baseNetResult.obj as JSONObject).optJSONArray("result").length() != 0) {
                    processList(result as String, ShoppingCarItemBean::class.java)
                } else {
                    emptyShoppingCar()
                }
            } else if (requestID == 0x1) {
                if (baseNetResult != null) {
                    toast("删除成功")
                    mArrayList.remove(currentItem)
                    mBaseAdapter.notifyDataSetChanged()
                }
            } else if (requestID == 0x2) {
                if (baseNetResult != null) {
                    if (currentEditMode == 0) {
                        currentItem.shopcart_num = (currentItem.shopcart_num.toInt() + 1).toString()
                    } else {
                        currentItem.shopcart_num = (currentItem.shopcart_num.toInt() - 1).toString()
                    }
                    var money = 0.00f
                    money = calculatePrice(money)
                    mTvShoppingCarAllSelectMoney.text = "￥${DecimalFormat("0.00").format(money)}"
                    mBaseAdapter.notifyItemChanged(mArrayList.indexOf(currentItem))
                }
            }
        }
    }

    override fun onRequestFail(requestID: Int, e: Throwable) {
        emptyShoppingCar()
    }

    private fun emptyShoppingCar() {
        mShoppingCarContainer.visibility = View.GONE
    }

//    @SuppressLint("RestrictedApi")
//    override fun onMyItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
//        super.onMyItemChildClick(adapter, view, position)
//        currentItem = mArrayList[position]
//        when (view?.id) {
//            R.id.mTvShoppingCarItemEditMode -> {
//                currentItem.isEditorMode = !currentItem.isEditorMode
//                mBaseAdapter.notifyItemChanged(position)
//            }
//            R.id.mIvShoppingCarItemSelector -> {
//                currentItem.isSelector = !currentItem.isSelector
//                mBaseAdapter.notifyItemChanged(position)
//
//                var money = 0.00f
//                mCivShoppingCarAllSelect.isChecked = mArrayList.filter { it.isSelector }.size == mArrayList.size
//                money = calculatePrice(money)
//                mCalculate.isEnabled = money != 0.00f
//                mTvShoppingCarAllSelectMoney.text = "￥${DecimalFormat("0.00").format(money)}"
//            }
//            R.id.mTvShoppingCarItemEditDel -> {
//                val builder = AlertDialog.Builder(context)
//                builder.setMessage("是否要删除当前商品？")
//                builder.setNegativeButton("取消", null)
//                builder.setPositiveButton("删除") { _, _ ->
//                    mPresent.getDataByPost(0x1,
//                            RequestParamsHelper.Companion.MEMBER_MODEL,
//                            RequestParamsHelper.Companion.ACT_DEL_SHOPCART,
//                            RequestParamsHelper.Companion.getDelShopcartParam(currentItem.shopcart_id))
//                }
//                builder.create().show()
//            }
//            R.id.mTvShoppingCarItemDelCount -> {
//                if (currentItem.shopcart_num.toInt() <= 1) {
//                    return
//                }
//                currentEditMode = 1
//                mPresent.getDataByPost(0x2,
//                        RequestParamsHelper.Companion.MEMBER_MODEL,
//                        RequestParamsHelper.Companion.ACT_UPDATE_SHOPCART,
//                        RequestParamsHelper.Companion.getUpdateShopcart(currentItem.shopcart_id, (currentItem.shopcart_num.toInt() - 1).toString()))
//            }
//            R.id.mTvShoppingCarItemAddCount -> {
//                currentEditMode = 0
//                mPresent.getDataByPost(0x2,
//                        RequestParamsHelper.Companion.MEMBER_MODEL,
//                        RequestParamsHelper.Companion.ACT_UPDATE_SHOPCART,
//                        RequestParamsHelper.Companion.getUpdateShopcart(currentItem.shopcart_id, (currentItem.shopcart_num.toInt() + 1).toString()))
//            }
//        }
//    }
}