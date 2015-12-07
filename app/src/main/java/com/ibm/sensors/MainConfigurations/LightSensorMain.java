package com.ibm.sensors.MainConfigurations;

import android.app.Activity;
import android.hardware.SensorManager;
import android.widget.TextView;

import com.ibm.sensors.EventWrappers.EventWrapper;
import com.ibm.sensors.R;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.rules.SensorConfiguration;
import com.ibm.sensors.utils.MultiGenericObservable;

/**
 * Created by nexus on 08/11/2015.
 */
public class LightSensorMain extends AbstractMainActivityConf {


	@Override
	protected void _Main(Env env, Activity ac) {
		TextView tv = (TextView) ac.findViewById(R.id.textView);
		if (!env.getEventHandler().subscribe(EventCreatorFactory.Events.TYPE_EVENT_LIGHT_AMOUNT, this, new SensorConfiguration().addObject(EventCreatorFactory.Params.DELAY, SensorManager.SENSOR_DELAY_NORMAL))) {
			tv.setText("subscription failed");
		}
	}


	@Override
	public void update(MultiGenericObservable<EventWrapper> object, EventWrapper data) {
		TextView tv = (TextView) mAc.findViewById(R.id.textView);
		tv.setText(data.getData().toString());
	}
}
