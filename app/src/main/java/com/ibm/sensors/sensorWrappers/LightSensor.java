package com.ibm.sensors.sensorWrappers;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.ibm.sensors.EventWrappers.LightSensorEvent;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.rules.SensorConfiguration;

/**
 * Created by nexus on 07/10/2015.
 */
public class LightSensor extends AbstractSensorWrapper implements SensorEventListener {

	private Sensor mLightSensor;
	protected Float mLevel;
	public LightSensor(Env env) {
		super(env);
		mLightSensor = env.getSensorManager().getDefaultSensor(Sensor.TYPE_LIGHT);
		mLevel = new Float(0);
	}

	@Override
	public int getType() {
		return EventCreatorFactory.Sensors.TYPE_SENSOR_LIGHT_SENSOR;
	}

	@Override
	public boolean register(SensorConfiguration conf) {
		env.getSensorManager().registerListener(
				this,
				mLightSensor,
				(Integer)conf.getObject(EventCreatorFactory.Params.DELAY));
		return true;
	}

	@Override
	public boolean unregister() {
		env.getSensorManager().unregisterListener(this);
		return true;
	}

	@Override
	public boolean isRegistered() {
		return true;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		this.mLevel=new Float(event.values[0]);
		env.getEventHandler().handleEvent(new LightSensorEvent(System.currentTimeMillis(),this,mLevel));
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}
}
