package com.android.ql.lf.carapp.ui.fragments.user

import android.support.v7.app.AlertDialog
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.present.UserPresent
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.utils.CacheDataManager
import kotlinx.android.synthetic.main.fragment_setting_layout.*

/**
 * Created by liufeng on 2018/2/3.
 */
class SettingFragment : BaseNetWorkingFragment() {

    private val userPresent by lazy {
        UserPresent()
    }

    override fun getLayoutId() = R.layout.fragment_setting_layout

    override fun initView(view: View?) {
        val packageInfo = mContext.packageManager.getPackageInfo(mContext.packageName, 0)
        val versionName = packageInfo.versionName
        mVersionName.text = "V$versionName"
        val cacheSize = CacheDataManager.getTotalCacheSize(mContext)
        mCacheSize.text = "$cacheSize"
        mTvPersonalInfoResetPassword.setOnClickListener {
            FragmentContainerActivity.startFragmentContainerActivity(mContext, "修改密码", ResetPasswordFragment::class.java)
        }
        mCacheSizeContainer.setOnClickListener {
            CacheDataManager.clearAllCache(mContext)
            mCacheSize.text = "暂无缓存"
        }
        mBtLogout.setOnClickListener {
            val builder = AlertDialog.Builder(mContext)
            builder.setNegativeButton("否", null)
            builder.setPositiveButton("是") { _, _ ->
                userPresent.onLogout()
                finish()
            }
            builder.setMessage("是否要退出登录？")
            builder.create().show()
        }
    }
}