package com.ibm.sensors.EventWrappers;

import com.ibm.sensors.sensorWrappers.AbstractSensorWrapper;
import com.ibm.sensors.sensorWrappers.SensorWrapper;

import java.util.TreeMap;

/**
 * Created by nexus on 02/10/2015.
 */
public class LogEventWrapper extends AbstractEventWrapper<String[]> {
	public LogEventWrapper(long timestamp, SensorWrapper sensor) {
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
