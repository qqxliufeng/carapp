package com.android.ql.lf.carapp.ui.fragments.user.mine

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.View
import com.android.ql.lf.carapp.R
import com.android.ql.lf.carapp.ui.fragments.BaseNetWorkingFragment
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

    override fun getLayoutId() = R.layout.fragment_mine_personal_info_layout

    override fun initView(view: View?) {
        mTvPersonalInfoFace.setOnClickListener {
            openImageChoose(MimeType.ofImage(), 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            val uris = Matisse.obtainResult(data)
            uris.forEach {
                val desUri = Uri.fromFile(File(Environment.getExternalStorageDirectory(), "croop"))
                Crop.of(it, desUri).start(mContext, this@MinePersonalInfoFragment)
            }
        } else if (requestCode == Crop.REQUEST_CROP) {
            val uri = Crop.getOutput(data)
            val desPath = File(Environment.getExternalStorageDirectory(), "croop1.jpg")
            ImageFactory.compressAndGenImage(uri.path, desPath.absolutePath, 100, false)
        }
    }
}