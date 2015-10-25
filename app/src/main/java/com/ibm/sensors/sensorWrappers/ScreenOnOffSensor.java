package com.ibm.sensors.sensorWrappers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.ibm.sensors.EventWrappers.ScreenOnOffEvent;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.core.EventHandler;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.rules.SensorConfiguration;

/**
 * Created by nexus on 07/10/2015.
 */
public class ScreenOnOffSensor extends AbstractSensorWrapper{

	public class ScreenBRS extends BroadcastReceiver{
		private Boolean wasScreenOn;
		private EventHandler mEH;
		private EventCreator mSensor;
		public  ScreenBRS(EventHandler eh,EventCreator sensor){
			wasScreenOn=new Boolean(true);
			mEH=eh;
			this.mSensor=sensor;
		}


		@Override
		public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
					// do whatever you need to do here
					wasScreenOn = false;
				} else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
					// and do whatever you need to do here
					wasScreenOn = true;
				}
			mEH.handleEvent(new ScreenOnOffEvent(System.currentTimeMillis(),this.mSensor,wasScreenOn));
			}

	}
	private ScreenBRS mScreenHandler;
	private IntentFilter mFilter;
	public ScreenOnOffSensor(Env env) {
		super(env);
		mScreenHandler = new ScreenBRS(env.getEventHandler(),this);
		mFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
		mFilter.addAction(Intent.ACTION_SCREEN_OFF);
	}

	@Override
	public int getType() {
		return EventCreatorFactory.Sensors.TYPE_SENSOR_SCREEN_ON_OFF;
	}

	@Override
	public boolean register(SensorConfiguration conf) {
		env.getContext().registerReceiver(mScreenHandler, mFilter);
		return true;
	}

	@Override
	public boolean unregister() {
		env.getContext().unregisterReceiver(mScreenHandler);
		return true;
	}

	@Override
	public boolean isRegistered() {
		return false;
	}
}
