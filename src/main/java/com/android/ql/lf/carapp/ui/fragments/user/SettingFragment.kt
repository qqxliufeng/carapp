package com.android.ql.lf.carapp.ui.fragments.user

import android.support.v7.app.AlertDialog
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.UserInfo
import com.android.ql.lf.carapp.present.UserPresent
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.ui.fragments.DetailContentFragment
import com.android.ql.lf.carapp.utils.CacheDataManager
import com.android.ql.lf.carapp.utils.Constants
import com.android.ql.lf.carapp.utils.RequestParamsHelper
import com.android.ql.lf.carapp.utils.toast
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.fragment_setting_layout.*
import org.jetbrains.anko.bundleOf

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
        val upgradeInfo = Beta.getUpgradeInfo()
        if (upgradeInfo != null && packageInfo.versionCode < upgradeInfo.versionCode) {
            mTvNewVersionNotify.visibility = View.VISIBLE
            mVersionName.text = "V${upgradeInfo.versionName}"
            mRlVersionUpContainer.setOnClickListener {
                Beta.checkUpgrade(true, false)
            }
        } else {
            mTvNewVersionNotify.visibility = View.GONE
        }
        val cacheSize = CacheDataManager.getTotalCacheSize(mContext)
        mCacheSize.text = "$cacheSize"
        mCacheSizeContainer.setOnClickListener {
            CacheDataManager.clearAllCache(mContext)
            mCacheSize.text = "暂无缓存"
        }
        mBtLogout.isEnabled = UserInfo.getInstance().isLogin
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
        mTvSettingAddressManager.setOnClickListener {
            toast(Constants.NO_FUNCTION_NOTIFY_MESSAGE)
        }
        mTvSettingAboutUs.setOnClickListener {
            FragmentContainerActivity.from(mContext)
                    .setClazz(DetailContentFragment::class.java)
                    .setTitle(mTvSettingAboutUs.text.toString())
                    .setExtraBundle(bundleOf(
                            Pair(DetailContentFragment.MODEL_NAME_FLAG,RequestParamsHelper.MEMBER_MODEL),
                            Pair(DetailContentFragment.ACT_NAME_FLAG,RequestParamsHelper.ACT_ABOUT),
                            Pair(DetailContentFragment.PARAM_FLAG, RequestParamsHelper.getAboutUs())))
                    .setNeedNetWorking(true)
                    .start()
        }
        mTvSettingHelp.setOnClickListener {
            FragmentContainerActivity.from(mContext)
                    .setClazz(DetailContentFragment::class.java)
                    .setTitle(mTvSettingHelp.text.toString())
                    .setExtraBundle(bundleOf(
                            Pair(DetailContentFragment.MODEL_NAME_FLAG,RequestParamsHelper.MEMBER_MODEL),
                            Pair(DetailContentFragment.ACT_NAME_FLAG,RequestParamsHelper.ACT_HELP),
                            Pair(DetailContentFragment.PARAM_FLAG, RequestParamsHelper.getHelp())))
                    .setNeedNetWorking(true)
                    .start()
        }
        mTvSettingProtocol.setOnClickListener {
            FragmentContainerActivity.from(mContext)
                    .setClazz(DetailContentFragment::class.java)
                    .setTitle(mTvSettingHelp.text.toString())
                    .setExtraBundle(bundleOf(
                            Pair(DetailContentFragment.MODEL_NAME_FLAG,RequestParamsHelper.MEMBER_MODEL),
                            Pair(DetailContentFragment.ACT_NAME_FLAG,RequestParamsHelper.ACT_PTGG),
                            Pair(DetailContentFragment.PARAM_FLAG, mapOf(Pair("pid","13")))))
                    .setNeedNetWorking(true)
                    .start()
        }
    }
}