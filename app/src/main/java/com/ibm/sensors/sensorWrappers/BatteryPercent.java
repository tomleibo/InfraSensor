package com.ibm.sensors.sensorWrappers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.ibm.sensors.EventWrappers.BatteryPercentEvent;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.rules.SensorConfiguration;

/**
 * Created by nexus on 27/09/2015.
 */
public class BatteryPercent extends AbstractSensorWrapper{
    BatteryPercent instance;
    private int mLevel;

    private final BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            mLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            env.getEventHandler().handleEvent(new BatteryPercentEvent(System.currentTimeMillis(),instance,mLevel));
        }
    };

    public BatteryPercent(Env env){
        super(env);
        instance = this;
        this.mLevel=-1;
    }

    @Override
    public int getType() {
        return EventCreatorFactory.Sensors.TYPE_BATTERY_PERCENT;
    }

    @Override
    public boolean register(SensorConfiguration conf) {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        env.getContext().registerReceiver(mBatInfoReceiver , ifilter);
        return true;
    }

    @Override
    public boolean unregister() {
        env.getContext().unregisterReceiver(mBatInfoReceiver);
        return false;
    }

    @Override
    public boolean isRegistered() {
        return true;
    }
}
