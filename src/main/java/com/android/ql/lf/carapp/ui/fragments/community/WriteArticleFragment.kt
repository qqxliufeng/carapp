package com.android.ql.lf.carapp.ui.fragments.community

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.util.TypedValue
import android.view.View
import android.widget.CheckedTextView
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.ImageBean
import com.android.ql.lf.carapp.present.CommunityPresent
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.adapter.OrderImageUpLoadAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.utils.ImageUploadHelper
import com.android.ql.lf.carapp.utils.isEmpty
import com.android.ql.lf.carapp.utils.toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.google.android.flexbox.FlexboxLayout
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import kotlinx.android.synthetic.main.fragment_write_article_layout.*
import java.util.*

/**
 * Created by lf on 18.2.6.
 * @author lf on 18.2.6
 */
class WriteArticleFragment : BaseNetWorkingFragment() {

    private val images = arrayListOf<ImageBean>()
    private var currentTag: String? = null
    private val mBaseAdapter: BaseQuickAdapter<ImageBean, BaseViewHolder> by lazy {
        OrderImageUpLoadAdapter(R.layout.adapter_write_article_image_up_load_item_layout, images)
    }
    private val communityPresent by lazy {
        CommunityPresent()
    }

    override fun getLayoutId() = R.layout.fragment_write_article_layout

    override fun initView(view: View?) {
        (mContext as FragmentContainerActivity).setSwipeBackEnable(false)
        images.add(ImageBean(R.drawable.img_icon_add_photo, null))
        val linearLayoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        mRvWriteArticleImages.layoutManager = linearLayoutManager
        mRvWriteArticleImages.adapter = mBaseAdapter
        mRvWriteArticleImages.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                if (mBaseAdapter.itemCount - 1 >= 3) {
                    toast("最多只能选择三张图片")
                    return
                }
                val item = mBaseAdapter.getItem(position)
                if (item != null) {
                    if (item.resId != null) {
                        openImageChoose(MimeType.ofImage(), 3 - (mBaseAdapter.itemCount - 1))
                    }
                }
            }
        })
        (0..10).forEach {
            val textView = CheckedTextView(mContext)
            val layoutParams = FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT)
            val margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, resources.displayMetrics).toInt()
            layoutParams.setMargins(margin, margin, margin, margin)
            textView.setBackgroundResource(R.drawable.selector_ctv_bg2)
            textView.setTextColor(resources.getColorStateList(R.color.select_ctv_1))
            textView.layoutParams = layoutParams
            textView.setOnClickListener { view ->
                currentTag = textView.text.toString()
                (0 until mFlWriteArticleTag.childCount)
                        .map { mFlWriteArticleTag.getChildAt(it) as CheckedTextView }
                        .filter { it != view }
                        .forEach { it.isChecked = false }
                (view as CheckedTextView).isChecked = !view.isChecked
            }
            textView.text = if (it % 2 == 0) "item item is $it" else "item is $it"
            mFlWriteArticleTag.addView(textView)
        }
        mCbWriteArticleProtocol.isChecked = true
        mBtWriteArticleSubmit.setOnClickListener {
            if (mEtWriteArticleTitle.isEmpty()) {
                toast("请输入帖子标题")
                return@setOnClickListener
            }
            if (mEtWriteArticleContent.isEmpty()) {
                toast("请输入帖子内容")
                return@setOnClickListener
            }
            if (currentTag == null) {
                toast("至少选择一个标签")
                return@setOnClickListener
            }
            if (!mCbWriteArticleProtocol.isChecked) {
                toast("请先确认同意${mTvWriteArticleProtocol.text}")
                return@setOnClickListener
            }
            if (images.size == 1) {

            } else {
                communityPresent.onUploadArticle(
                        images.filter { it.uriPath != null } as ArrayList<ImageBean>,
                        150,
                        object : ImageUploadHelper.OnImageUploadListener {
                            override fun onActionStart() {
                            }

                            override fun onActionEnd(paths: ArrayList<String>?) {
                            }

                            override fun onActionFailed() {
                            }
                        })
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            val uris = Matisse.obtainPathResult(data)
            uris.forEach {
                images.add(0, ImageBean(null, it))
            }
            mBaseAdapter.notifyDataSetChanged()
        }
    }
}