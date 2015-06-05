package com.example.ergo_proxy.goodlineapptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class HomeActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable()
        {
            public void run()
            {
                Intent i = new Intent(HomeActivity.this, MainTwoActivity.class);
                startActivity(i);
                finish();
            }
        }, 4 * 1000);
    }
}
