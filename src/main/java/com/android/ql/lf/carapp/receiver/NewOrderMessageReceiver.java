package com.android.ql.lf.carapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.ql.lf.carapp.data.NewOrderMessageBean;
import com.android.ql.lf.carapp.ui.activities.SplashActivity;
import com.android.ql.lf.carapp.utils.Constants;
import com.android.ql.lf.carapp.utils.PreferenceUtils;
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
        if (intent != null && JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            RxBus.getDefault().post(new NewOrderMessageBean(intent.getStringExtra(JPushInterface.EXTRA_EXTRA)));
            if (!PreferenceUtils.getPrefBoolean(context, Constants.APP_IS_ALIVE, true)) {
                notify(context);
            }
        }
    }

    private void notify(Context context) {
        Intent forIntent = new Intent(context, SplashActivity.class);
        context.startActivity(forIntent);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//        Intent forIntent = new Intent(context, SplashActivity.class);
//        //将要跳转的界面
//        //点击后消失
//        builder.setAutoCancel(true);
//        //设置通知栏消息标题的头像
//        builder.setSmallIcon(R.mipmap.ic_launcher);
//        //设置通知铃声
//        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
//        builder.setTicker("新消息");
//        builder.setContentText("您有新的订单，请注意查收！");
//        builder.setContentTitle("新消息提醒");
//        PendingIntent intentPend = PendingIntent.getActivity(context, 0, forIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//        builder.setContentIntent(intentPend);
//        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
//        managerCompat.notify(0, builder.build());
    }
}
