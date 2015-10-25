package com.ibm.sensors.sensorWrappers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.ibm.sensors.env.Env;
import com.ibm.sensors.rules.SensorConfiguration;

/**
 * Created by nexus on 27/09/2015.
 */
public class BatteryPercent extends AbstractSensorWrapper{
    private Context mContext;
    private int mLevel;

    protected void update(){

    }

    private final BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            mLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        }
    };
    public BatteryPercent(Env env){
        super(env);
        this.mLevel=-1;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public boolean register(SensorConfiguration conf) {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        mContext.registerReceiver(mBatInfoReceiver , ifilter);
        return true;
    }

    @Override
    public boolean unregister() {
        mContext.unregisterReceiver(mBatInfoReceiver);
        return false;
    }

    @Override
    public boolean isRegistered() {
        return false;
    }
}
