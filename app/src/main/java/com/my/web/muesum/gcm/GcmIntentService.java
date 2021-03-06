package com.my.web.muesum.gcm;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

import com.my.web.muesum.LoadActivity;
import com.my.web.muesum.R;
import com.my.web.muesum.myApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chomark on 2016. 1. 12..
 */


public class GcmIntentService  extends GCMBaseIntentService {


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private static void generateNotification(Context context,String title, String message){
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, LoadActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0,
                notificationIntent, 0);

        Notification.Builder mBuilder = new Notification.Builder(context);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setTicker(context.getString(R.string.app_name));
        mBuilder.setWhen(System.currentTimeMillis());
        mBuilder.setNumber(10);
        mBuilder.setContentTitle(title);

        mBuilder.setContentText(message);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        mBuilder.setContentIntent(intent);
        mBuilder.setAutoCancel(true);

        //mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        notificationManager.notify(0, mBuilder.build());
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String msg = intent.getStringExtra("msg");
        String title = intent.getStringExtra("title");
        String ordno = intent.getStringExtra("ordno");
        Long type = intent.getLongExtra("type", 0);

        generateNotification(context, title, msg);
    }

    @Override
    protected void onError(Context context, String s) {

    }

    @Override
    protected void onRegistered(Context context, String reg_id) {
        ((myApplication) this.getApplication()).setRegGCMId(reg_id);
        Log.e("들어간값1", ((myApplication) this.getApplication()).getRegGCMId());
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onUnregistered(Context context, String s) {

    }
}
