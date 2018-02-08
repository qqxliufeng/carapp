package com.android.ql.lf.carapp.present;

import com.android.ql.lf.carapp.action.IViewUserAction;
import com.android.ql.lf.carapp.action.ViewUserAction;

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

    public void onLogin(String result) {
        userAction.onLogin(result);
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
