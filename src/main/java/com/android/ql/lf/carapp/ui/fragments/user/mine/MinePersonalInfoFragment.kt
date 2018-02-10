package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.EditText
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.data.UserInfo
import com.android.ql.lf.carapp.present.UserPresent
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
import com.android.ql.lf.carapp.utils.Constants
import com.android.ql.lf.carapp.utils.GlideManager
import com.android.ql.lf.carapp.utils.ImageFactory
import com.soundcloud.android.crop.Crop
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import kotlinx.android.synthetic.main.fragment_mine_personal_info_layout.*
import java.io.File

/**
 * Created by lf on 18.2.3.
 * @author lf on 18.2.3
 */
class MinePersonalInfoFragment : BaseNetWorkingFragment() {

    private val userPresent by lazy {
        UserPresent()
    }

    override fun getLayoutId() = R.layout.fragment_mine_personal_info_layout

    override fun initView(view: View?) {
        GlideManager.loadFaceCircleImage(mContext, UserInfo.getInstance().memberPic, mTvPersonalInfoFace)
        mTvPersonalInfoNickName.text = UserInfo.getInstance().memberName
        mTvPersonalInfoPhone.text = UserInfo.getInstance().memberPhone.let { "${it.substring(0, 3)}****${it.substring(7, it.length)}" }
        mFaceContainer.setOnClickListener {
            openImageChoose(MimeType.ofImage(), 1)
        }
        mNickNameContainer.setOnClickListener {
            val builder = AlertDialog.Builder(mContext)
            builder.setTitle("修改昵称")
            builder.setNegativeButton("取消", null)
            builder.setPositiveButton("确定") { _, _ ->

            }
            val contentView = View.inflate(mContext, R.layout.layout_edit_personal_content_layout, null)
            val content = contentView.findViewById<EditText>(R.id.mEtEditPersonalInfo)
            content.setText(UserInfo.getInstance().memberName)
            builder.setView(contentView)
            builder.create().show()
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
            val desPath = File("${Constants.IMAGE_PATH}face/${System.currentTimeMillis()}_1.jpg")
            ImageFactory.compressAndGenImage(uri.path, desPath.absolutePath, 100, true)
        }
    }
}