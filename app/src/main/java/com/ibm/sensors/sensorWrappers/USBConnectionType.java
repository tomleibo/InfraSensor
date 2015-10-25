package com.ibm.sensors.sensorWrappers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.ibm.sensors.EventWrappers.USBPlugTypeEventWrapper;
import com.ibm.sensors.core.EventHandler;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.rules.SensorConfiguration;

/**
 * Created by nexus on 03/09/2015.
 */
public class USBConnectionType extends BroadcastReceiver implements EventCreator {
    private final EventHandler mEvenetHandler;
    private BroadcastReceiver mReceiver;
    private boolean mRegistered;


    public USBConnectionType(EventHandler eventHandler){
        this.mRegistered=false;
        this.mEvenetHandler = eventHandler;
    }

    @Override
    public int getType() {
        return EventCreatorFactory.Sensors.TYPE_SENSOR_USB_CONNECTION_TYPE;
    }

    @Override
    public boolean register(SensorConfiguration conf) {
        this.mRegistered=true;
        return true;
    }

    @Override
    public boolean unregister() {
        this.mRegistered=false;
        return false;
    }

    @Override
    public boolean isRegistered() {
        return false;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (this.mRegistered) {
            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            this.mEvenetHandler.handleEvent(new USBPlugTypeEventWrapper(plugged,this));
        }
    }
}
