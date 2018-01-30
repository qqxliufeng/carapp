package com.android.ql.lf.carapp.ui.fragments.bottom

import android.view.View
import com.android.ql.lf.carapp.ui.fragments.BaseFragment

/**
 * Created by lf on 18.1.24.
 * @author lf on 18.1.24
 */
class MainMallFragment : BaseFragment(){

    companion object {
        fun newInstance():MainMallFragment{
            return MainMallFragment()
        }
    }

    override fun getLayoutId() = android.R.layout.simple_list_item_1

    override fun initView(view: View?) {
    }
}