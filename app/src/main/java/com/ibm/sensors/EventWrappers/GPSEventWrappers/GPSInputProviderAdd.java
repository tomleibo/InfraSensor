package com.ibm.sensors.EventWrappers.GPSEventWrappers;

import com.ibm.sensors.EventWrappers.AbstractEventWrapper;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.sensorWrappers.EventCreator;

/**
 * Created by nexus on 03/10/2015.
 */
public class GPSInputProviderAdd extends AbstractEventWrapper{
	protected String mInputProvider;
	public GPSInputProviderAdd(long timestamp, EventCreator sensor, String inputProvider) {
		super(timestamp, sensor);
		this.mInputProvider=inputProvider;
	}

	@Override
	public int getEventType() {
		return EventCreatorFactory.TYPE_EVENT_GPS_INPUT_PROVIDER_ADD;
	}

	@Override
	public Object getData() {
		return mInputProvider;
	}
}
