package com.ibm.sensors.EventWrappers.GPSEventWrappers;

import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.sensorWrappers.EventCreator;

/**
 * Created by nexus on 05/10/2015.
 */
public class GPSInputProviderRemove extends GPSInputProviderAdd{
	public GPSInputProviderRemove(long timestamp, EventCreator sensor, String inputProvider) {
		super(timestamp, sensor, inputProvider);
	}

	@Override
	public int getEventType() {
		return EventCreatorFactory.TYPE_EVENT_GPS_INPUT_PROVIDER_REMOVE;
	}
}
