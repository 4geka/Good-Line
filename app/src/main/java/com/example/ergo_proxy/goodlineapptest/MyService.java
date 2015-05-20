package com.example.ergo_proxy.goodlineapptest;

/**
 * Created by Ergo-Proxy on 15.05.2015.
 */
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.concurrent.TimeUnit;

public class MyService extends IntentService {
    NotificationManager nm;

    public MyService() {
        super("myname");
    }

    public void onCreate() {
        super.onCreate();
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        sendNotif();
    }

    void sendNotif() {

        Notification notif = new Notification(R.mipmap.ic_launcher, "Ой, что то интересное",
                System.currentTimeMillis());

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("title", "Почему надо сходить на GLO");
        intent.putExtra("url", "http://live.goodline.info/blog/Good_Line/2814.html");
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.main_items);

        notif.setLatestEventInfo(this, "Notification's title", "Notification's text", pIntent);

        notif.flags |= Notification.FLAG_AUTO_CANCEL;
        notif.flags |= Notification.DEFAULT_VIBRATE;
        contentView.setImageViewResource(R.id.title_image, R.mipmap.ic_launcher);
        contentView.setTextViewText(R.id.titleView, "Почему надо сходить на GLO");
        notif.contentIntent = contentIntent;
        notif.contentView = contentView;

        nm.notify(1, notif);

    }

    public void onDestroy() {
        super.onDestroy();
    }
}
