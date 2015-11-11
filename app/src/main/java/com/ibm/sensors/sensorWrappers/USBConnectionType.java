package com.ibm.sensors.sensorWrappers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.ibm.sensors.EventWrappers.BatteryPercentEvent;
import com.ibm.sensors.EventWrappers.USBPlugTypeEventWrapper;
import com.ibm.sensors.core.EventHandler;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.rules.SensorConfiguration;

/**
 * Created by nexus on 03/09/2015.
 */
public class USBConnectionType extends AbstractSensorWrapper {

    private BroadcastReceiver mReceiver;
    private boolean mRegistered;
    protected Integer mConnectionType;
    protected USBConnectionType instance;
    private final BroadcastReceiver mBatInfoReceiver;
    private IntentFilter ifilter;
    public USBConnectionType(final Env env) {
        super(env);
        instance=this;
        mConnectionType=new Integer(-1);
        this.mBatInfoReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context ctxt, Intent intent) {
                mConnectionType = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                env.getEventHandler().handleEvent(new USBPlugTypeEventWrapper(System.currentTimeMillis(),instance,mConnectionType));
            }
        };
    }


    @Override
    public int getType() {
        return EventCreatorFactory.Sensors.TYPE_SENSOR_USB_CONNECTION_TYPE;
    }

    @Override
    public boolean register(SensorConfiguration conf) {
        ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        env.getContext().registerReceiver(mBatInfoReceiver , ifilter);
        this.mRegistered=true;
        return true;
    }

    @Override
    public boolean unregister() {
        ifilter=null;
        this.mRegistered=false;
        return false;
    }

    @Override
    public boolean isRegistered() {
        return false;
    }

}
