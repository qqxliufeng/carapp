package com.android.ql.lf.carapp.data;

import android.text.TextUtils;

/**
 * Created by lf on 18.2.10.
 *
 * @author lf on 18.2.10
 */

public class UserInfo {

    public static final String LOGOUT_FLAG = "user_logout_flag";

    public static String loginToken = "NONE";

    public static void resetLoginSuccessDoActionToken() {
        loginToken = "NONE";
    }

    private UserInfo() {
    }

    private static UserInfo instance;

    public static UserInfo getInstance() {
        if (instance == null) {
            synchronized (UserInfo.class) {
                if (instance == null) {
                    instance = new UserInfo();
                }
            }
        }
        return instance;
    }

    private String memberId;
    private String memberPhone;
    private String memberName = "";
    private String memberPic;
    private String memberMyInviteCode;
    private String memberRank;
    private String memberOpenid;
    private String memberAddress;
    private String memberInviteCode;
    private String memberIsEnsureMoney;
    private String memberIsMaster;
    private String memberGrade;
    private String memberOrderNum;
    private String memberIdCard = "";
    private String memberAuthentication; // 0 已经提交过师傅资料，正在审核


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberPic() {
        return memberPic;
    }

    public void setMemberPic(String memberPic) {
        this.memberPic = memberPic;
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(memberId);
    }

    public boolean isMaster() {
        return "1".equals(memberIsMaster) && !"0".equals(memberAuthentication);
    }

    public boolean isPayEnsureMoney() {
        return "1".equals(memberIsEnsureMoney);
    }

    public String getMemberMyInviteCode() {
        return memberMyInviteCode;
    }

    public void setMemberMyInviteCode(String memberMyInviteCode) {
        this.memberMyInviteCode = memberMyInviteCode;
    }

    public String getMemberRank() {
        return memberRank;
    }

    public void setMemberRank(String memberRank) {
        this.memberRank = memberRank;
    }

    public String getMemberOpenid() {
        return memberOpenid;
    }

    public void setMemberOpenid(String memberOpenid) {
        this.memberOpenid = memberOpenid;
    }

    public String getMemberAddress() {
        return memberAddress;
    }

    public void setMemberAddress(String memberAddress) {
        this.memberAddress = memberAddress;
    }

    public String getMemberInviteCode() {
        return memberInviteCode;
    }

    public void setMemberInviteCode(String memberInviteCode) {
        this.memberInviteCode = memberInviteCode;
    }

    public void setMemberIsEnsureMoney(String memberIsEnsureMoney) {
        this.memberIsEnsureMoney = memberIsEnsureMoney;
    }

    public void setMemberIsMaster(String memberIsMaster) {
        this.memberIsMaster = memberIsMaster;
    }

    public String getMemberGrade() {
        return memberGrade;
    }

    public void setMemberGrade(String memberGrade) {
        this.memberGrade = memberGrade;
    }

    public String getMemberOrderNum() {
        return memberOrderNum;
    }

    public void setMemberOrderNum(String memberOrderNum) {
        this.memberOrderNum = memberOrderNum;
    }

    public String getMemberIdCard() {
        return memberIdCard;
    }

    public void setMemberIdCard(String memberIdCard) {
        this.memberIdCard = memberIdCard;
    }

    public boolean isCheckingMaster() {
        return "0".equals(memberAuthentication);
    }

    public void setMemberAuthentication(String memberAuthentication) {
        this.memberAuthentication = memberAuthentication;
    }

    public void loginOut() {
        memberId = null;
    }

    public void exitApp() {
        if (instance != null) {
            instance = null;
        }
    }

}
