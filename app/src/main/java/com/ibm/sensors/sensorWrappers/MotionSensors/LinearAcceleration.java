package com.ibm.sensors.sensorWrappers.MotionSensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

import com.ibm.sensors.EventWrappers.MotionSensorEventWrapper;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.sensorWrappers.AbstractHardwareSensor;

/**
 * Created by nexus on 31/10/2015.
 */
public class LinearAcceleration extends AbstractHardwareSensor {
	public LinearAcceleration(SensorManager sm, Env env) throws InstantiationException {
		super(EventCreatorFactory.Sensors.TYPE_SENSOR_ACCELEROMETER,env);
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
