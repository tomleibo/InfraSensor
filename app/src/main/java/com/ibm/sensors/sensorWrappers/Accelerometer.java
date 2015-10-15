package com.ibm.sensors.sensorWrappers;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

import com.ibm.sensors.EventWrappers.MotionSensorEventWrapper;
import com.ibm.sensors.core.EventHandler;
import com.ibm.sensors.core.EventCreatorFactory;


/**
 * Created by thinkPAD on 9/2/2015.
 */
public class Accelerometer extends AbstractHardwareSensor {

    public Accelerometer(SensorManager sm,  EventHandler handler) throws InstantiationException {
        super(EventCreatorFactory.Sensors.TYPE_SENSOR_ACCELEROMETER,sm,handler);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        MotionSensorEventWrapper wrap = new MotionSensorEventWrapper(event);
        handler.handleEvent(wrap);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
