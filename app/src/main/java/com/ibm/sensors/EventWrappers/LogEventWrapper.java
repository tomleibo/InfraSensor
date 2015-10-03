package com.ibm.sensors.EventWrappers;

import com.ibm.sensors.sensorWrappers.EventCreator;

/**
 * Created by nexus on 02/10/2015.
 */
public class LogEventWrapper extends AbstractEventWrapper<String[]> {
	public LogEventWrapper(long timestamp, EventCreator sensor) {
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
