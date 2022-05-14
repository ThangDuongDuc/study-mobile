package com.ddt.broadcast_receivers.customs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class InternetConnectionReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if (networkInfo != null) {
            if (networkInfo.isConnected()) {
//                WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
//                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//                String ssid = wifiInfo.getSSID();
                WifiInfo wifiInfo = intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
                String ssid = wifiInfo.getSSID();
                Toast.makeText(context, ssid + " is connected.", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(context, " Wifi is disconnected.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
