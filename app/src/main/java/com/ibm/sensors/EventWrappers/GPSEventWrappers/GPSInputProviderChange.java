package com.ibm.sensors.EventWrappers.GPSEventWrappers;

import com.ibm.sensors.EventWrappers.AbstractEventWrapper;
import com.ibm.sensors.sensorWrappers.EventCreator;

/**
 * Created by nexus on 03/10/2015.
 */
public class GPSInputProviderChange extends AbstractEventWrapper{
	//TODO
	public GPSInputProviderChange(long timestamp, EventCreator sensor) {
		super(timestamp, sensor);
	}

	@Override
	public int getEventType() {
		return 0;
	}

	@Override
	public Object getData() {
		return null;
	}
}
