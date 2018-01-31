package com.android.ql.lf.carapp.ui.fragments.order

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.adapter.OrderImageUpLoadAdapter
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.ui.fragments.BaseRecyclerViewFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy
import kotlinx.android.synthetic.main.fragment_order_image_upload_layout.*

/**
 * Created by lf on 18.1.29.
 * @author lf on 18.1.29
 */
class OrderImageUpLoadFragment : BaseNetWorkingFragment() {

    private val mArrayList = arrayListOf<ImageBean>()
    private val mBaseAdapter : BaseQuickAdapter<ImageBean,BaseViewHolder> by lazy {
        OrderImageUpLoadAdapter(R.layout.adapter_order_image_up_load_item_layout, mArrayList)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun getLayoutId() = R.layout.fragment_order_image_upload_layout

    override fun initView(view: View?) {
        test.setOnClickListener {
            Matisse.from(this)
                    .choose(MimeType.allOf())
                    .imageEngine(GlideEngine())
                    .capture(true)
                    .maxSelectable(3)
                    .captureStrategy(CaptureStrategy(true, "com.android.ql.lf.carapp.fileProvider"))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .theme(R.style.my_matisse_style)
                    .forResult(0)
        }
        mArrayList.add(ImageBean(R.drawable.img_icon_add_photo,null))
        mRecyclerView.adapter = mBaseAdapter
        mRecyclerView.layoutManager = GridLayoutManager(mContext, 2)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.upload_image_menu, menu)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            val uris = Matisse.obtainResult(data)
            uris.forEach {
                mArrayList.add(0,ImageBean(null,it))
            }
            mBaseAdapter.notifyDataSetChanged()
        }
    }

    class ImageBean(var resId:Int?,var uriPath:Uri?)

}