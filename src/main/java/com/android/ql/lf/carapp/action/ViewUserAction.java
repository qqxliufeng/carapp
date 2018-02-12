package com.android.ql.lf.carapp.action;

import com.android.ql.lf.carapp.data.UserInfo;
import com.android.ql.lf.carapp.utils.RxBus;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/**
 * Created by lf on 18.2.8.
 *
 * @author lf on 18.2.8
 */

public class ViewUserAction implements IViewUserAction {

    @Override
    public boolean onLogin(@NotNull JSONObject result) {
        try {
            UserInfo.getInstance().setMemberId(result.optString("member_id"));
            UserInfo.getInstance().setMemberName(result.optString("member_name"));
            UserInfo.getInstance().setMemberPhone(result.optString("member_phone"));
            UserInfo.getInstance().setMemberPic(result.optString("member_pic"));
            UserInfo.getInstance().setMemberAddress(result.optString("member_address"));
            UserInfo.getInstance().setMemberGrade(result.optString("member_grade"));
            UserInfo.getInstance().setMemberInviteCode(result.optString("member_invite_code"));
            UserInfo.getInstance().setMemberOrderNum(result.optString("member_order_num"));
            UserInfo.getInstance().setMemberMyInviteCode(result.optString("member_mycode"));
            UserInfo.getInstance().setMemberIsEnsureMoney(result.optString("member_is_ensure_money"));
            UserInfo.getInstance().setMemberIsMaster(result.optString("member_ismaster"));
            UserInfo.getInstance().setMemberOpenid(result.optString("member_openid"));
            UserInfo.getInstance().setMemberRank(result.optString("member_rank"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onRegister(@NotNull JSONObject result) {
    }

    @Override
    public void onForgetPassword(@NotNull JSONObject result) {
    }

    @Override
    public void onResetPassword(@NotNull JSONObject result) {
    }
}
