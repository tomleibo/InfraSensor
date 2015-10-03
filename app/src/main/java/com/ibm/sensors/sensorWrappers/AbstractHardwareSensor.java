package com.ibm.sensors.sensorWrappers;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.ibm.sensors.core.EventHandler;

/**
 * Created by thinkPAD on 9/5/2015.
 */
public abstract class AbstractHardwareSensor implements EventCreator,SensorEventListener {
    private boolean isRegistered;
    private Sensor sensor;
    private SensorManager sm;
    protected EventHandler handler;

    public AbstractHardwareSensor(int type,SensorManager sm, EventHandler handler) throws InstantiationException {
        this.isRegistered=false;
        this.sm=sm;
        this.sensor = sm.getDefaultSensor(type);
        this.handler = handler;
        if (sensor==null) {
            throw new InstantiationException("hardware sensor is null");
        }
    }

    @Override
    public int getType() {
        return sensor.getType();
    }

    private boolean registerOrUnregister(int delayMillis, boolean register) {
        if (sensor!=null) {
            if (register) {
                sm.registerListener(this, sensor, delayMillis);
            } else {
                sm.unregisterListener(this, sensor);
            }
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public synchronized boolean unregister(Object o) {
        isRegistered = false;
        return registerOrUnregister(0,true);
    }

    @Override
    public synchronized boolean register(int delayMillis, Object o) {
        isRegistered=true;
        return registerOrUnregister(delayMillis, true);
    }

    @Override
    public boolean isRegistered() {
        return isRegistered;
    }

}
