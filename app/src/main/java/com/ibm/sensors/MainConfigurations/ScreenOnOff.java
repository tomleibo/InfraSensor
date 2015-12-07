package com.ibm.sensors.MainConfigurations;

import android.app.Activity;
import android.hardware.SensorManager;
import android.util.Log;

import com.ibm.sensors.EventWrappers.EventWrapper;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.rules.SensorConfiguration;
import com.ibm.sensors.utils.MultiGenericObservable;

/**
 * Created by nexus on 10/11/2015.
 */
public class ScreenOnOff extends AbstractMainActivityConf{
	@Override
	public void update(MultiGenericObservable<EventWrapper> object, EventWrapper data) {
		Log.wtf("res",data.getData().toString());
	}

	@Override
	protected void _Main(Env env, Activity ac) {
		if (!this.mEnv.getEventHandler().subscribe(EventCreatorFactory.Events.TYPE_EVENT_SCREEN_ON_OFF, this, new SensorConfiguration().addObject(EventCreatorFactory.Params.DELAY, SensorManager.SENSOR_DELAY_NORMAL))) {
			//tv.setText("subscription failed");
		}
	}
}
