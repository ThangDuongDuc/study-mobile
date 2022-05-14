package com.ddt.broadcast_receivers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.ddt.broadcast_receivers.customs.InternetConnectionReciever;

public class MainActivity extends AppCompatActivity {
    InternetConnectionReciever internetConnectionReciever = new InternetConnectionReciever();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(internetConnectionReciever, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(internetConnectionReciever);
    }
}