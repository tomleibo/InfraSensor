package com.ibm.sensors.EventWrappers.GPSEventWrappers;

import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.sensorWrappers.EventCreator;

/**
 * Created by nexus on 15/10/2015.
 */
public class GPSAccuracyChangeEventInputProvider extends GPSInputProviderAdd {

	public GPSAccuracyChangeEventInputProvider(long timestamp, EventCreator sensor, String inputProvider) {
		super(timestamp, sensor, inputProvider);
	}

	@Override
	public int getEventType() {
		return EventCreatorFactory.Events.TYPE_EVENT_GPS_ACCURACY_CHANGED_INPUT_PROVIDER;
	}
}
