package com.android.ql.lf.carapp.present;

import com.android.ql.lf.carapp.action.IViewServiceOrderAction;
import com.android.ql.lf.carapp.action.ViewServiceOrderAction;
import com.android.ql.lf.carapp.data.UserInfo;

/**
 * Created by lf on 18.2.8.
 *
 * @author lf on 18.2.8
 */

public class ServiceOrderPresent {

    private IViewServiceOrderAction serviceOrderAction;

    public ServiceOrderPresent() {
        serviceOrderAction = new ViewServiceOrderAction();
    }

    public void doAuthMaster() {
        UserInfo.getInstance().setMemberIsMaster("1");
        serviceOrderAction.doAuthMaster();
    }

    public void doAuthEnsureMoney() {
        UserInfo.getInstance().setMemberIsEnsureMoney("1");
        serviceOrderAction.doAuthEnsureMoney();
    }

}
