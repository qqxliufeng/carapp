package com.android.ql.lf.carapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.ql.lf.carapp.data.NewOrderMessageBean;
import com.android.ql.lf.carapp.utils.RxBus;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lf on 18.2.28.
 *
 * @author lf on 18.2.28
 */

public class NewOrderMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                RxBus.getDefault().post(new NewOrderMessageBean(intent.getStringExtra(JPushInterface.EXTRA_MESSAGE)));
            }
        }
    }
}
