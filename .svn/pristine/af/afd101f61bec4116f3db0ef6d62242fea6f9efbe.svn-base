package com.android.ql.lf.carapp.application;


import android.support.multidex.MultiDexApplication;

import com.android.ql.lf.carapp.component.AppComponent;
import com.android.ql.lf.carapp.component.AppModule;
import com.android.ql.lf.carapp.component.DaggerAppComponent;

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
        application = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }


    public static CarApplication getInstance() {
        return application;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }



}
