package com.android.ql.lf.carapp.present;

import com.android.ql.lf.carapp.action.IViewUserAction;
import com.android.ql.lf.carapp.action.ViewUserAction;
import com.android.ql.lf.carapp.data.UserInfo;
import com.android.ql.lf.carapp.utils.RxBus;

import org.json.JSONObject;

/**
 * Created by lf on 18.2.8.
 *
 * @author lf on 18.2.8
 */

public class UserPresent {

    private IViewUserAction userAction;

    public UserPresent() {
        userAction = new ViewUserAction();
    }

    public void onLogin(JSONObject result) {
        if (userAction.onLogin(result)) {
            sendLoginSuccessMessage();
        }
    }

    public void sendLoginSuccessMessage() {
        RxBus.getDefault().post(UserInfo.getInstance());
    }

    public void getCode(String phone) {
    }

    public void register(String name, String password) {
    }

    public void forgetPassword(String phone, String code) {
    }

    public void resetPassword(String oldPassword, String newPassword) {
    }

    public void qqLogin() {
    }

    public void weixinLogin() {
    }

}
