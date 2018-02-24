package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import org.json.JSONObject

/**
 * Created by lf on 18.2.3.
 * @author lf on 18.2.3
 */
class MineWalletFragment : BaseRecyclerViewFragment<MineWalletFragment.HistoryWalletBean>() {


    private var mTvReleaseMoney: TextView? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun createAdapter(): BaseQuickAdapter<HistoryWalletBean, BaseViewHolder> =
            MineWalletListAdapter(R.layout.adapter_mine_wallet_history_item_layout, mArrayList)

    override fun initView(view: View?) {
        super.initView(view)
        val topView = View.inflate(mContext, R.layout.layout_top_mine_wallet_layout, null)
        mTvReleaseMoney = topView.findViewById(R.id.mTvTopMineWalletMoney)
        topView.findViewById<TextView>(R.id.mTvMineWalletEnsureMoney).setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "保证金交纳", MineEnsureMoneyFragment::class.java)
        }
        topView.findViewById<TextView>(R.id.mTvMineWalletMoneyAccount).setOnClickListener {

        }
        mBaseAdapter.addHeaderView(topView)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.wallet_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.mMenuWallet) {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "提现", MineCashFragment::class.java)
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onRefresh() {
        super.onRefresh()
        mPresent.getDataByPost(0x0, RequestParamsHelper.MEMBER_MODEL, RequestParamsHelper.ACT_INTEGRAL, RequestParamsHelper.getIntegralParam(currentPage))
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mPresent.getDataByPost(0x0, RequestParamsHelper.MEMBER_MODEL, RequestParamsHelper.ACT_INTEGRAL, RequestParamsHelper.getIntegralParam(currentPage))
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        val check = checkResultCode(result)
        if (check != null && check.code == SUCCESS_CODE) {
            mTvReleaseMoney!!.text = (check.obj as JSONObject).optString("arr") ?: "暂无"
            processList(result as String,HistoryWalletBean::class.java)
        }
    }


    class MineWalletListAdapter(resId: Int, list: ArrayList<HistoryWalletBean>) : BaseQuickAdapter<HistoryWalletBean, BaseViewHolder>(resId, list) {
        override fun convert(helper: BaseViewHolder?, item: HistoryWalletBean?) {
            helper!!.setText(R.id.mTvWalletHistoryItemTitle, item!!.integral_title)
            helper.setText(R.id.mTvWalletHistoryItemTime, item.integral_time)
            helper.setText(R.id.mTvWalletHistoryItemCount, item.integral_price)
        }
    }

    class HistoryWalletBean {
        var integral_id: String? = null
        var integral_title: String? = null
        var integral_price: String? = null
        var integral_sym: String? = null
        var integral_time: String? = null
        var integral_uid: String? = null
    }

}