package com.ibm.sensors.sensorWrappers;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.ibm.sensors.EventWrappers.LightSensorEvent;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.core.EventHandler;

/**
 * Created by nexus on 07/10/2015.
 */
public class LightSensor extends AbstractSensorWrapper<Float> implements SensorEventListener {

	private Sensor mLightSensor;
	public LightSensor(EventHandler handler) {
		super(handler);
		mLightSensor = handler.getSensorManager().getDefaultSensor(Sensor.TYPE_LIGHT);
	}

	@Override
	public int getType() {
		return EventCreatorFactory.TYPE_LIGHT_SENSOR;
	}

	@Override
	public boolean register(int delayMillis, Float integer) {
		mHandler.getSensorManager().registerListener(
				this,
				mLightSensor,
				delayMillis);
		return true;
	}

	@Override
	public boolean unregister(Float integer) {
		mHandler.getSensorManager().unregisterListener(this);
		return true;
	}

	@Override
	public boolean isRegistered() {
		return true;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		mHandler.handleEvent(new LightSensorEvent(System.currentTimeMillis(),this,new Float(event.values[0])));
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}
}
