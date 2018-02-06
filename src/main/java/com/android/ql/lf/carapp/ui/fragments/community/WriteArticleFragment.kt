package com.android.ql.lf.carapp.ui.fragments.community

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.widget.LinearLayoutManager
import android.util.TypedValue
import android.view.View
import android.widget.CheckedTextView
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.ImageBean
import com.android.ql.lf.carapp.ui.adapter.OrderImageUpLoadAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.utils.Constants
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.google.android.flexbox.FlexboxLayout
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy
import kotlinx.android.synthetic.main.fragment_write_article_layout.*
import org.jetbrains.anko.support.v4.toast

/**
 * Created by lf on 18.2.6.
 * @author lf on 18.2.6
 */
class WriteArticleFragment : BaseNetWorkingFragment() {

    private val images = arrayListOf<ImageBean>()
    private val mBaseAdapter: BaseQuickAdapter<ImageBean, BaseViewHolder> by lazy {
        OrderImageUpLoadAdapter(R.layout.adapter_write_article_image_up_load_item_layout, images)
    }

    override fun getLayoutId() = R.layout.fragment_write_article_layout

    override fun initView(view: View?) {
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
                        Matisse.from(this@WriteArticleFragment)
                                .choose(MimeType.ofAll())
                                .imageEngine(GlideEngine())
                                .capture(true)
                                .maxSelectable(3 - (mBaseAdapter.itemCount - 1))
                                .captureStrategy(CaptureStrategy(true, Constants.FILE_PROVIDE_PATH))
                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                                .thumbnailScale(0.85f)
                                .theme(R.style.my_matisse_style)
                                .forResult(0)
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
                (0 until mFlWriteArticleTag.childCount)
                        .map { mFlWriteArticleTag.getChildAt(it) as CheckedTextView }
                        .filter { it != view }
                        .forEach { it.isChecked = false }
                (view as CheckedTextView).isChecked = !view.isChecked
            }
            textView.text = if (it % 2 == 0) "item item is $it" else "item is $it"
            mFlWriteArticleTag.addView(textView)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            val uris = Matisse.obtainResult(data)
            uris.forEach {
                images.add(0, ImageBean(null, it))
            }
            mBaseAdapter.notifyDataSetChanged()
        }
    }
}