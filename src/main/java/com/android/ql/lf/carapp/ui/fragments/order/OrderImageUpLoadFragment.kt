package com.android.ql.lf.carapp.ui.fragments.order

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.adapter.OrderImageUpLoadAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.utils.Constants
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy
import kotlinx.android.synthetic.main.fragment_order_image_upload_layout.*
import org.jetbrains.anko.support.v4.toast

/**
 * Created by lf on 18.1.29.
 * @author lf on 18.1.29
 */
class OrderImageUpLoadFragment : BaseNetWorkingFragment() {

    private val mArrayList = arrayListOf<ImageBean>()
    private val mBaseAdapter: BaseQuickAdapter<ImageBean, BaseViewHolder> by lazy {
        OrderImageUpLoadAdapter(R.layout.adapter_order_image_up_load_item_layout, mArrayList)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun getLayoutId() = R.layout.fragment_order_image_upload_layout

    override fun initView(view: View?) {
        mArrayList.add(ImageBean(R.drawable.img_icon_add_photo, null))
        mRecyclerView.adapter = mBaseAdapter
        mRecyclerView.layoutManager = GridLayoutManager(mContext, 2)
        mRecyclerView.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                if (mBaseAdapter.itemCount - 1 >= 3) {
                    toast("最多只能选择三张图片")
                    return
                }
                val item = mBaseAdapter.getItem(position)
                if (item != null) {
                    if (item.resId != null) {
                        Matisse.from(this@OrderImageUpLoadFragment)
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
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.upload_image_menu, menu)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            val uris = Matisse.obtainResult(data)
            uris.forEach {
                mArrayList.add(0, ImageBean(null, it))
            }
            mBaseAdapter.notifyDataSetChanged()
        }
    }

    class ImageBean(var resId: Int?, var uriPath: Uri?)

}