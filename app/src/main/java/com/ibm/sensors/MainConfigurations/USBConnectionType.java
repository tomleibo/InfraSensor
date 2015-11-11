package com.ibm.sensors.MainConfigurations;

import android.app.Activity;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.TextView;

import com.ibm.sensors.EventWrappers.EventWrapper;
import com.ibm.sensors.R;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.rules.SensorConfiguration;
import com.ibm.sensors.utils.MultiGenericObservable;

/**
 * Created by nexus on 10/11/2015.
 */
public class USBConnectionType extends AbstractMainActivityConf{
	@Override
	public void update(MultiGenericObservable<EventWrapper> object, EventWrapper data) {
		TextView tv = (TextView) this.mAc.findViewById(R.id.textView);
		tv.setText(data.getData().toString());
	}

	@Override
	protected void _Main(Env env, Activity ac) {
		if (!this.mEnv.getEventHandler().subscribe(EventCreatorFactory.Events.TYPE_SENSOR_USB_CONNECTION_TYPE, this, new SensorConfiguration().addInteger(EventCreatorFactory.Params.DELAY, SensorManager.SENSOR_DELAY_NORMAL))) {
			//tv.setText("subscription failed");
		}
	}
}
