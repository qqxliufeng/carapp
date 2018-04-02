package com.android.ql.lf.carapp.ui.activities

import com.android.ql.lf.carapp.R
import com.hyphenate.easeui.EaseConstant
import com.hyphenate.easeui.ui.EaseChatFragment
import kotlinx.android.synthetic.main.activity_chat_layout.*
import org.jetbrains.anko.bundleOf

/**
 * Created by lf on 18.4.2.
 * @author lf on 18.4.2
 */
class ChatActivity : BaseActivity() {

    companion object {
        val CHAT_TITLE_FLAG = "chat_title"
    }

    override fun getLayoutId() = R.layout.activity_chat_layout

    override fun initView() {
        val bundle = bundleOf(Pair(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE), Pair(EaseConstant.EXTRA_USER_ID, intent.getStringExtra(EaseConstant.EXTRA_USER_ID)))
        val easeChatFragment = EaseChatFragment()
        easeChatFragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.mFlChat, easeChatFragment).commit()
    }
}