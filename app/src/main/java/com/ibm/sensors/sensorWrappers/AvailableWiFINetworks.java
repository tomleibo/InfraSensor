package com.ibm.sensors.sensorWrappers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;


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
    private Boolean mIsBuisy;
    private BroadcastReceiver mBroadcastReciver;

    private Boolean getIsBusy(){
        synchronized (mIsBuisy){
            return mIsBuisy;
        }
    }
    private void setIsBusy(Boolean isbusy){
        synchronized (mIsBuisy){
            mIsBuisy = new Boolean(isbusy);
        }
    }

    public AvailableWiFINetworks(WifiManager wifiMan,EventHandler handler, Context context){
        super(handler);
        this.mWiFi = wifiMan;
        this.shouldStop = true;
        this.mDelay = 0;
        this.mResults =null;
        this.mContext = context;
        this.thread = new Thread(this);
        this.mIsBuisy= new Boolean(false);
        this.mBroadcastReciver = new BroadcastReceiver()
        {
            SensorWrapper sensor=null;

            public BroadcastReceiver init(SensorWrapper s) {
                this.sensor=s;
                return this;
            }

            @Override
            public void onReceive(Context c, Intent intent)
            {
                mResults = null;
                mResults = mWiFi.getScanResults();
                mHandler.handleEvent(new WiFiAvailableNetworksEvent(mResults,sensor));
                if (mDelay>0) {
                    mContext.unregisterReceiver(this);
                    try {
                        thread.sleep(mDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    setIsBusy(false);
                }

            }
        }.init(this);
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
        this.thread.start();
        setIsBusy(false);
        return true;
    }

    @Override
    public boolean unregister(Object o) {
        mWiFi.setWifiEnabled(false);
        shouldStop = true;

        try {
            this.mContext.unregisterReceiver(this.mBroadcastReciver);
        }catch (Exception e){}
        thread.interrupt();
        return true;
    }

    @Override
    public boolean isRegistered() {
        return false;
    }

    @Override
    public void run() {

        while (!shouldStop)
        if (!getIsBusy()) {
            setIsBusy(true);
            try {
                this.mContext.unregisterReceiver(this.mBroadcastReciver);
            }catch (Exception e){}
            this.mContext.registerReceiver(this.mBroadcastReciver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
            }

    }

}
