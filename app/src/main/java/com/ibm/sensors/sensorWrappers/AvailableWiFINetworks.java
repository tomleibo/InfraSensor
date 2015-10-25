package com.ibm.sensors.sensorWrappers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import com.ibm.sensors.EventWrappers.WiFiAvailableNetworksEvent;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.rules.SensorConfiguration;

import java.util.List;

/**
 * Created by nexus on 04/09/2015.
 */
public class AvailableWiFINetworks extends AbstractSensorWrapper implements Runnable {
    private final WifiManager mWiFi;
    private final Context mContext;

    private boolean shouldStop;
    private final Thread thread;
    private int mDelay;
    private List<ScanResult> mResults;
    private Boolean mIsBuisy;
    private final BroadcastReceiver mBroadcastReciver;

    private Boolean getIsBusy(){
        synchronized (mIsBuisy){
            return mIsBuisy;
        }
    }
    private void setIsBusy(Boolean isbusy){
        synchronized (mIsBuisy){
            mIsBuisy = isbusy;
        }
    }

    public AvailableWiFINetworks(final Env env){
        super(env);
        this.mWiFi = (WifiManager)env.getContext().getSystemService(Context.WIFI_SERVICE);
        this.shouldStop = true;
        this.mDelay = 0;
        this.mResults =null;
        this.mContext = env.getContext();
        this.thread = new Thread(this);
        this.mIsBuisy= Boolean.valueOf(false);
        this.mBroadcastReciver = new BroadcastReceiver()
        {
            EventCreator sensor=null;

            public BroadcastReceiver init(EventCreator s) {
                this.sensor=s;
                return this;
            }

            @Override
            public void onReceive(Context c, Intent intent)
            {
                mResults = null;
                mResults = mWiFi.getScanResults();
                env.getEventHandler().handleEvent(new WiFiAvailableNetworksEvent(mResults,sensor));
                if (mDelay>0) {
                    mContext.unregisterReceiver(this);
                    try {
                        Thread.sleep(mDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    setIsBusy(false);
                }

            }
        }.init(this);
    }

    @Override
    public int getType() {
        return EventCreatorFactory.Sensors.TYPE_SENSOR_AVAILABLE_WIFI_NETWORKS;
    }

    @Override
    public boolean register(SensorConfiguration conf) {
        mWiFi.setWifiEnabled(true);
        this.shouldStop = false;
        this.mDelay = conf.getInt(EventCreatorFactory.Params.DELAY);
        this.thread.start();
        setIsBusy(false);
        return true;
    }

    public boolean unregister() {
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
