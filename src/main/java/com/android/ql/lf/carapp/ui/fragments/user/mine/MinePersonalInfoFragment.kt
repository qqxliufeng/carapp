package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.EditText
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.ImageBean
import com.android.ql.lf.carapp.data.UserInfo
import com.android.ql.lf.carapp.present.UserPresent
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.utils.*
import com.soundcloud.android.crop.Crop
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import kotlinx.android.synthetic.main.fragment_mine_personal_info_layout.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject
import java.io.File
import java.util.ArrayList

/**
 * Created by lf on 18.2.3.
 * @author lf on 18.2.3
 */
class MinePersonalInfoFragment : BaseNetWorkingFragment() {

    private val userPresent by lazy {
        UserPresent()
    }

    //0  用户名   1 手机号    2 头像
    private var currentToken = 0

    override fun getLayoutId() = R.layout.fragment_mine_personal_info_layout

    override fun initView(view: View?) {
        GlideManager.loadFaceCircleImage(mContext, UserInfo.getInstance().memberPic, mTvPersonalInfoFace)
        mTvPersonalInfoNickName.text = UserInfo.getInstance().memberName
        mTvPersonalInfoPhone.text = UserInfo.getInstance().memberPhone.let { "${it.substring(0, 3)}****${it.substring(7, it.length)}" }
        mFaceContainer.setOnClickListener {
            currentToken = 2
            openImageChoose(MimeType.ofImage(), 1)
        }
        mNickNameContainer.setOnClickListener {
            currentToken = 0
            showEditInfoDialog("修改昵称", UserInfo.getInstance().memberName)
        }
        mIdCardContainer.setOnClickListener {
            currentToken = 1
            showEditInfoDialog("修改身份证号", UserInfo.getInstance().memberIdCard ?: "")
        }
    }

    private fun showEditInfoDialog(title: String, oldInfo: String) {
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle(title)
        val contentView = View.inflate(mContext, R.layout.layout_edit_personal_content_layout, null)
        val content = contentView.findViewById<EditText>(R.id.mEtEditPersonalInfo)
        content.setText(oldInfo)
        content.setSelection(oldInfo.length)
        builder.setNegativeButton("取消", null)
        builder.setPositiveButton("确定") { _, _ ->
            if (oldInfo == content.text.toString().trim()) {
                return@setPositiveButton
            }
            mPresent.getDataByPost(0x0, RequestParamsHelper.MEMBER_MODEL, RequestParamsHelper.ACT_EDIT_PERSONAL, RequestParamsHelper.getEditPersonalParam(content.getTextString()))
        }
        builder.setView(contentView)
        builder.create().show()
    }

    override fun onRequestStart(requestID: Int) {
        super.onRequestStart(requestID)
        if (requestID == 0x0) {
            getFastProgressDialog("正在修改……")
        }
    }

    override fun <T : Any?> onRequestSuccess(requestID: Int, result: T) {
        super.onRequestSuccess(requestID, result)
        val check = checkResultCode(result)
        if (check != null) {
            if (SUCCESS_CODE == check.code) {
                when (currentToken) {
                    0 -> {
                        val nameResult = (check.obj as JSONObject).optJSONObject("result").optString("member_name")
                        mTvPersonalInfoNickName.text = nameResult
                        userPresent.modifyInfoForName(nameResult)
                    }
                    1 -> {

                    }
                    2 -> {
                        val picResult = (check.obj as JSONObject).optJSONObject("result").optString("member_pic")
                        GlideManager.loadFaceCircleImage(mContext, picResult, mTvPersonalInfoFace)
                        userPresent.modifyInfoForPic(picResult)
                    }
                }

            } else {
                toast((check.obj as JSONObject).optString("msg"))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            val uris = Matisse.obtainResult(data)
            uris[0].let {
                val dir = File("${Constants.IMAGE_PATH}face/")
                if (!dir.exists()) {
                    dir.mkdirs()
                }
                val desUri = Uri.fromFile(File(dir, "${System.currentTimeMillis()}.jpg"))
                Crop.of(it, desUri).start(mContext, this@MinePersonalInfoFragment)
            }
        } else if (requestCode == Crop.REQUEST_CROP) {
            val uri = Crop.getOutput(data)
            ImageUploadHelper(object : ImageUploadHelper.OnImageUploadListener {
                override fun onActionFailed() {
                    toast("头像上传失败，请稍后重试！")
                }

                override fun onActionStart() {
                    progressDialog = ProgressDialog.show(mContext, null, "正在上传头像……")
                }

                override fun onActionEnd(paths: ArrayList<String>) {
                    val builder = ImageUploadHelper.createMultipartBody()
                    paths.forEachIndexed { index, s ->
                        val file = File(s)
                        builder.addFormDataPart("$index", file.name, RequestBody.create(MultipartBody.FORM, file))
                    }
                    mPresent.uploadFile(0x1, RequestParamsHelper.MEMBER_MODEL, RequestParamsHelper.ACT_EDIT_PERSONAL, builder.build().parts())
                }
            }).upload(arrayListOf(ImageBean(null, uri.path)), 100)
        }
    }
}