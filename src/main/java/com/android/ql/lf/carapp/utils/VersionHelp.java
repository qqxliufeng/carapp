package com.android.ql.lf.carapp.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by liufeng on 2018/3/11.
 */

public class VersionHelp {

    public static void checkUpgrade(){
    }

    public static void getUpgradeInfo(){
    }

    public static void downNewVersion(Context context,Uri uri,String fileName){
        DownLoadManagerHelper.downLoadApk(context,uri,fileName);
    }

    public static void install(Context context,String path){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            File file = (new File(path));
            Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName()+".fileProvider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }else {
            intent.setDataAndType(Uri.parse("file://" + path), "application/vnd.android.package-archive");
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
