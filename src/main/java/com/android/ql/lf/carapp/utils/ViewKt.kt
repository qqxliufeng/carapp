package com.android.ql.lf.carapp.utils

import android.support.design.widget.Snackbar
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import com.android.ql.lf.carapp.R
import java.util.regex.Pattern

/**
 * Created by lf on 18.2.10.
 * @author lf on 18.2.10
 */

val PHONE_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}\$"


/**
 * 显示SnackBar
 */
fun View.showSnackBar(message: String) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    snackBar.view.setBackgroundResource(R.color.colorPrimary)
    snackBar.show()
}

/**
 * 校验输入框是否为空
 */
fun EditText.isEmpty(): Boolean {
    return TextUtils.isEmpty(this.text.toString())
}

fun EditText.isPhone(): Boolean {
    return Pattern.compile(PHONE_REG).matcher(this.text).matches()
}
