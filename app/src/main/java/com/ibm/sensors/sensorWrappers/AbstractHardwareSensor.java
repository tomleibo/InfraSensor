package com.ibm.sensors.sensorWrappers;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.ibm.sensors.EventWrappers.MotionSensorEventWrapper;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.core.EventHandler;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.rules.SensorConfiguration;

/**
 * Created by thinkPAD on 9/5/2015.
 */
public class AbstractHardwareSensor implements EventCreator,SensorEventListener {

    private boolean isRegistered;
    private Sensor sensor;
    private final SensorManager sm;
    protected final EventHandler handler;

    public AbstractHardwareSensor(Env env) throws InstantiationException {
        this.isRegistered=false;
        this.sm=env.getSensorManager();
        this.handler = env.getEventHandler();

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
        int delayMillis = (Integer)conf.getObject(EventCreatorFactory.Params.DELAY);
        int type = (Integer)conf.getObject(EventCreatorFactory.Params.SENSOR_TYPE);
        this.sensor = sm.getDefaultSensor(type);
        if (sensor==null) {
            try {
                throw new InstantiationException("hardware sensor is null");
            } catch (InstantiationException e) {
                e.printStackTrace();
                return false;
            }
        }
        return sm.registerListener(this, sensor, delayMillis);
    }

    @Override
    public boolean isRegistered() {
        return isRegistered;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        handler.handleEvent(new MotionSensorEventWrapper(event));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
