package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.ImageBean
import com.android.ql.lf.carapp.ui.activities.FragmentContainerActivity
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.utils.isEmpty
import com.android.ql.lf.carapp.utils.isPhone
import com.android.ql.lf.carapp.utils.toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import kotlinx.android.synthetic.main.fragment_mine_apply_master_info_submit_layout.*

/**
 * Created by lf on 18.2.12.
 * @author lf on 18.2.12
 */
class MineApplyMasterInfoSubmitFragment : BaseNetWorkingFragment() {

    private val storeImagesList by lazy {
        arrayListOf<ImageBean>()
    }

    private val idCardImageList by lazy {
        arrayListOf<ImageBean>()
    }

    private val licenceImageList by lazy {
        arrayListOf<ImageBean>()
    }

    private val storeImagesAdapter by lazy {
        ImageAdapter(R.layout.adapter_apply_master_info_image_item_layout, storeImagesList)
    }

    private val idCardImagesAdapter by lazy {
        ImageAdapter(R.layout.adapter_apply_master_info_image_item_layout, idCardImageList)
    }

    private val licenceImagesAdapter by lazy {
        ImageAdapter(R.layout.adapter_apply_master_info_image_item_layout, licenceImageList)
    }

    private var currentImageFlag = 0

    override fun getLayoutId() = R.layout.fragment_mine_apply_master_info_submit_layout

    override fun initView(view: View?) {
        (mContext as FragmentContainerActivity).setSwipeBackEnable(false)
        val storeHeaderView = View.inflate(mContext, R.layout.layout_apply_master_add_image_layout, null)
        val idCardHeaderView = View.inflate(mContext, R.layout.layout_apply_master_add_image_layout, null)
        val licenceHeaderView = View.inflate(mContext, R.layout.layout_apply_master_add_image_layout, null)

        mRvApplyMasterInfoStoreImages.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        mRvApplyMasterInfoStoreImages.adapter = storeImagesAdapter
        storeImagesAdapter.addHeaderView(storeHeaderView, -1, LinearLayoutManager.HORIZONTAL)
        storeImagesAdapter.headerLayout.setOnClickListener {
            if (storeImagesList.size >= 3) {
                toast("最多只能上传三张")
                return@setOnClickListener
            }
            currentImageFlag = 0
            openImageChoose(MimeType.ofImage(), 3 - storeImagesList.size)
        }

        mRvApplyMasterInfoIdCardImages.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        mRvApplyMasterInfoIdCardImages.adapter = idCardImagesAdapter
        idCardImagesAdapter.addHeaderView(idCardHeaderView, -1, LinearLayoutManager.HORIZONTAL)
        idCardImagesAdapter.headerLayout.setOnClickListener {
            if (idCardImageList.size >= 2) {
                toast("最多只能上传两张")
                return@setOnClickListener
            }
            currentImageFlag = 1
            openImageChoose(MimeType.ofImage(), 2 - idCardImageList.size)
        }

        mRvApplyMasterInfoLicenceImages.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        mRvApplyMasterInfoLicenceImages.adapter = licenceImagesAdapter
        licenceImagesAdapter.addHeaderView(licenceHeaderView, -1, LinearLayoutManager.HORIZONTAL)
        licenceImagesAdapter.headerLayout.setOnClickListener {
            if (licenceImageList.size >= 1) {
                toast("最多只能上传一张")
                return@setOnClickListener
            }
            currentImageFlag = 2
            openImageChoose(MimeType.ofImage(), 1 - licenceImageList.size)
        }


        mBtMasterInfoSubmit.setOnClickListener {
            if (storeImagesList.size <= 0) {
                toast("请上传至少一张门店照片")
                return@setOnClickListener
            }
            if (idCardImageList.size != 2) {
                toast("请上传正反面身份证照片")
                return@setOnClickListener
            }
            if (licenceImageList.isEmpty()) {
                toast("请上传营业执照")
                return@setOnClickListener
            }
            if (mEtMasterInfoStoreName.isEmpty()) {
                toast("请输入店铺名称")
                return@setOnClickListener
            }
            if (mEtMasterInfoPhone.isEmpty()) {
                toast("请输入店铺名称")
                return@setOnClickListener
            }
            if (mEtMasterInfoPhone.isPhone()) {
                toast("请输入合法的手机号")
                return@setOnClickListener
            }
            if (mEtMasterInfoAddress.isEmpty()) {
                toast("请输入店铺地址")
                return@setOnClickListener
            }
            if (!TextUtils.isDigitsOnly(mEtMasterInfoMasterNum.text.toString())) {
                toast("请输入合法的店铺师傅数量")
                return@setOnClickListener
            }
            if (mEtMasterInfoMasterNum.isEmpty()) {
                toast("请输入店铺师傅数量")
                return@setOnClickListener
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            when (currentImageFlag) {
                0 -> {
                    Matisse.obtainResult(data).forEach {
                        storeImagesList.add(ImageBean(null, it))
                    }
                    storeImagesAdapter.notifyDataSetChanged()
                }
                1 -> {
                    Matisse.obtainResult(data).forEach {
                        idCardImageList.add(ImageBean(null, it))
                    }
                    idCardImagesAdapter.notifyDataSetChanged()
                }
                2 -> {
                    Matisse.obtainResult(data).forEach {
                        licenceImageList.add(ImageBean(null, it))
                    }
                    licenceImagesAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    class ImageAdapter(resId: Int, list: ArrayList<ImageBean>) : BaseQuickAdapter<ImageBean, BaseViewHolder>(resId, list) {
        override fun convert(helper: BaseViewHolder?, item: ImageBean?) {
            val imageView = helper!!.getView<ImageView>(R.id.mIvApplyMasterImageInfoItem)
            imageView.setImageURI(item!!.uriPath)
        }
    }
}