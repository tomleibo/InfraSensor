package com.ibm.sensors.sensorWrappers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;


import com.ibm.sensors.EventWrappers.WiFiAvailableNetworksEvent;
import com.ibm.sensors.core.EventHandler;
import com.ibm.sensors.core.SensorAndRuleFactory;

import java.util.List;

/**
 * Created by nexus on 04/09/2015.
 */
public class AvailableWiFINetworks extends AbstractSensorWrapper implements Runnable {
    private WifiManager mWiFi;
    private Context mContext;

    private boolean shouldStop;
    private Thread thread;
    private int mDelay;
    private List<ScanResult> mResults;


    public AvailableWiFINetworks(WifiManager wifiMan,EventHandler handler, Context context){
        super(handler);
        this.mWiFi = wifiMan;
        this.shouldStop = true;
        this.mDelay = 0;
        this.mResults =null;
        this.mContext = context;
    }
    public AvailableWiFINetworks(WifiManager wifiMan,EventHandler handler, Context context, int delay){
        this(wifiMan,handler,context);
        this.mDelay = delay;

    }
    @Override
    public int getType() {
        return SensorAndRuleFactory.TYPE_AVAILABLE_WIFI_NETWORKS;
    }

    @Override
    public boolean register(int delayMillis, Object o) {
        mWiFi.setWifiEnabled(true);
        this.shouldStop = false;
        this.mDelay = delayMillis;
        return true;
    }

    @Override
    public boolean unregister(Object o) {
        mWiFi.setWifiEnabled(false);
        shouldStop = true;
        thread.interrupt();
        return true;
    }

    @Override
    public boolean isRegistered() {
        return false;
    }

    @Override
    public void run() {
        while(!shouldStop) {
            try {
                this.mContext.registerReceiver(new BroadcastReceiver()
                {
                    @Override
                    public void onReceive(Context c, Intent intent)
                    {
                        mResults = null;
                        mResults = mWiFi.getScanResults();
                        mHandler.handleEvent(new WiFiAvailableNetworksEvent(mResults));
                    }
                }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
                Thread.sleep(mDelay);
            }
            catch (InterruptedException e) {
                //ignore. unless it is implictly stopped.
            }
        }
    }
}
