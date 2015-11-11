package com.ibm.sensors.MainConfigurations;

import android.app.Activity;
import android.hardware.SensorManager;
import android.widget.TextView;

import com.ibm.sensors.EventWrappers.EventWrapper;
import com.ibm.sensors.MainConfigurations.MainConfInterface;
import com.ibm.sensors.R;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.realmDb.Location;
import com.ibm.sensors.realmDb.RealmHandler;
import com.ibm.sensors.realmDb.Wifi;
import com.ibm.sensors.rules.SensorConfiguration;
import com.ibm.sensors.sensorWrappers.LightSensor;
import com.ibm.sensors.utils.MultiGenericObservable;

import io.realm.RealmList;

/**
 * Created by nexus on 08/11/2015.
 */
public class LightSensorMain implements MainConfInterface {
	private Env env;
	public Activity ac;
	@Override
	public void Main(Env env, Activity ac) {
		this.env=env;
		TextView tv = (TextView) ac.findViewById(R.id.textView);
		if (!env.getEventHandler().subscribe(EventCreatorFactory.Events.TYPE_EVENT_LIGHT_AMOUNT, this, new SensorConfiguration().addInteger(EventCreatorFactory.Params.DELAY, SensorManager.SENSOR_DELAY_NORMAL))) {
			tv.setText("subscription failed");
		}
	}

	@Override
	public void update(MultiGenericObservable<EventWrapper> object, EventWrapper data) {
		TextView tv = (TextView) ac.findViewById(R.id.textView);
		tv.setText(data.getData().toString());
	}
}
