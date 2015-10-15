package com.ibm.sensors.EventWrappers.GPSEventWrappers;

import android.os.Bundle;

import com.ibm.sensors.EventWrappers.AbstractEventWrapper;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.sensorWrappers.EventCreator;

/**
 * Created by nexus on 15/10/2015.
 */
public class GPSAccuracyChangeEventExtrasWrapper extends AbstractEventWrapper<Bundle>{
	private Bundle mExtras;
	public GPSAccuracyChangeEventExtrasWrapper(long timestamp, EventCreator sensor, Bundle extras) {
		super(timestamp, sensor);
		this.mExtras=extras;
	}

	@Override
	public int getEventType() {
		return EventCreatorFactory.TYPE_EVENT_GPS_ACCURACY_CHANGED_EXTRAS;
	}

	@Override
	public Object getData() {
		return this.mExtras;
	}
}
