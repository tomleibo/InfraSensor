package com.ibm.sensors.com.ibm.sensorWrappers;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.ibm.sensors.com.ibm.core.EventHandler;
import com.ibm.sensors.com.ibm.sensors.interfaces.SensorWrapper;


/**
 * Created by thinkPAD on 9/2/2015.
 */
public class
        Accelrometer implements SensorWrapper<SensorManager> {

    private Sensor sensor;
    private EventHandler handler;

    public Accelrometer(Sensor sensor,EventHandler handler) {
        this.sensor = sensor;
        this.handler = handler;
    }


    @Override
    public int getType() {
        return Sensor.TYPE_ACCELEROMETER;
    }



    private boolean registerOrUnregister(int delayMillis, SensorManager sm,boolean register) {
        sensor = sm.getDefaultSensor(getType());
        if (sensor!=null) {
            if (register) {
                sm.registerListener(handler, sensor, delayMillis);
            } else {
                sm.unregisterListener(handler, sensor);
            }
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean unregister(SensorManager param) {
        return registerOrUnregister(0,param,true);
    }

    @Override
    public boolean register(int delayMillis, SensorManager param) {
        return registerOrUnregister(delayMillis,param,true);
    }

}
