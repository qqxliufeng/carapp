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
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by lf on 18.2.3.
 * @author lf on 18.2.3
 */
class MineWalletFragment : BaseRecyclerViewFragment<String>() {

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun createAdapter(): BaseQuickAdapter<String, BaseViewHolder> =
            MineWalletListAdapter(android.R.layout.simple_list_item_1, mArrayList)

    override fun initView(view: View?) {
        super.initView(view)
        val topView = View.inflate(mContext, R.layout.layout_top_mine_wallet_layout, null)
        topView.findViewById<TextView>(R.id.mTvMineWalletEnsureMoney).setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext,"保证金交纳",MineEnsureMoneyFragment::class.java)
        }
        mBaseAdapter.addHeaderView(topView)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.wallet_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.mMenuWallet){
            FragmentContainerActivity.startFragmentContainerActivity(mContext,"提现",MineCashFragment::class.java)
        }
        return super.onOptionsItemSelected(item)
    }


    class MineWalletListAdapter(resId: Int, list: ArrayList<String>) : BaseQuickAdapter<String, BaseViewHolder>(resId, list) {
        override fun convert(helper: BaseViewHolder?, item: String?) {
        }
    }
}