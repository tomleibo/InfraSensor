package com.ibm.sensors.sensorWrappers;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.core.EventHandler;
import com.ibm.sensors.rules.SensorConfiguration;

/**
 * Created by thinkPAD on 9/5/2015.
 */
public abstract class AbstractHardwareSensor implements EventCreator,SensorEventListener {

    private boolean isRegistered;
    private final Sensor sensor;
    private final SensorManager sm;
    protected final EventHandler handler;

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

    @Override
    public synchronized boolean unregister() {
        isRegistered = false;
        sm.unregisterListener(this, sensor);
        return true;
    }

    @Override
    public synchronized boolean register(SensorConfiguration conf) {
        isRegistered=true;
        int delayMillis = conf.getInt(EventCreatorFactory.Params.DELAY);
        return sm.registerListener(this, sensor, delayMillis);
    }

    @Override
    public boolean isRegistered() {
        return isRegistered;
    }

}
