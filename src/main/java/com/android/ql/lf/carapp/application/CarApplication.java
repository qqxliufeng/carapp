package com.android.ql.lf.carapp.application;


import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.android.ql.lf.carapp.component.AppComponent;
import com.android.ql.lf.carapp.component.AppModule;
import com.android.ql.lf.carapp.component.DaggerAppComponent;
import com.android.ql.lf.carapp.utils.Constants;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.tencent.bugly.Bugly;

import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lf on 18.1.23.
 *
 * @author lf on 18.1.23
 */

public class CarApplication extends MultiDexApplication {

    private AppComponent appComponent;

    public static CarApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        Bugly.init(this, Constants.BUGLY_APP_ID, false);
        application = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        initHX();
    }

    private void initHX() {
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        if (processAppName == null || !processAppName.equalsIgnoreCase(getPackageName())) {
            return;
        }
        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(false);
        EMClient.getInstance().init(this, options);
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                Log.e("TAG", e.getMessage());
            }
        }
        return processName;
    }

    public static CarApplication getInstance() {
        return application;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
