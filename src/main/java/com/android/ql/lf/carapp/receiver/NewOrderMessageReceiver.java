package com.android.ql.lf.carapp.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.android.ql.lf.carapp.R;
import com.android.ql.lf.carapp.data.NewOrderMessageBean;
import com.android.ql.lf.carapp.ui.activities.SplashActivity;
import com.android.ql.lf.carapp.utils.Constants;
import com.android.ql.lf.carapp.utils.PreferenceUtils;
import com.android.ql.lf.carapp.utils.RxBus;

import cn.jpush.android.api.JPushInterface;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by lf on 18.2.28.
 *
 * @author lf on 18.2.28
 */

public class NewOrderMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            RxBus.getDefault().post(new NewOrderMessageBean(intent.getStringExtra(JPushInterface.EXTRA_MESSAGE)));
            if (!PreferenceUtils.getPrefBoolean(context, Constants.APP_IS_ALIVE, true)) {
                notify(context);
            }
        }
    }

    private void notify(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        //将要跳转的界面
        Intent forIntent = new Intent(context, SplashActivity.class);
        //点击后消失
        builder.setAutoCancel(true);
        //设置通知栏消息标题的头像
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //设置通知铃声
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        builder.setTicker("新消息");
        builder.setContentText("您有新的订单，请注意查收！");
        builder.setContentTitle("新消息提醒");
        PendingIntent intentPend = PendingIntent.getActivity(context, 0, forIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(intentPend);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(0, builder.build());
    }
}
