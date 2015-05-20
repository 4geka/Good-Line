package com.example.ergo_proxy.goodlineapptest;

/**
 * Created by Ergo-Proxy on 15.05.2015.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, MyService.class));

    }
}
