package com.a

import android.text.TextUtils
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import kotlinx.android.synthetic.main.fragment_web_view_content_layout.*

/**
 * Created by lf on 2017/11/28 0028.
 * @author lf on 2017/11/28 0028
 */
class WebViewContentFragment : BaseNetWorkingFragment() {

    companion object {
        val PATH_FLAG = "path_flag"
    }

    override fun getLayoutId() = R.layout.fragment_web_view_content_layout

    override fun initView(view: View?) {
        val setting = mWvContent.settings
        setting.javaScriptEnabled = true
        setting.domStorageEnabled = true
        setting.defaultTextEncodingName = "utf-8"
        mWvContent.webViewClient = object : WebViewClient() {
        }
        mWvContent.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress == 100) {
                    mPbProgress.visibility = View.GONE
                } else {
                    mPbProgress.progress = newProgress
                }
            }
        }
        if (arguments != null) {
            val path = arguments.getString(PATH_FLAG)
            if (!TextUtils.isEmpty(path)) {
                if (path.startsWith("http://") || path.startsWith("https://")) {
                    mWvContent.loadUrl(path)
                } else {
                    mWvContent.loadData(path, "text/html; charset=UTF-8", null)
                }
            }
        }
    }
}