package com.ibm.sensors.EventWrappers.GPSEventWrappers;

import com.ibm.sensors.EventWrappers.AbstractEventWrapper;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.sensorWrappers.EventCreator;

/**
 * Created by nexus on 03/10/2015.
 */
public class GPSAccuracyChangeEventWrapper  extends AbstractEventWrapper<Integer> {
	private Integer mStatus;
	public GPSAccuracyChangeEventWrapper(long timestamp, EventCreator sensor, Integer status) {
		super(timestamp, sensor);
		this.mStatus = new Integer(status);
	}



	@Override
	public int getEventType() {
		return EventCreatorFactory.Events.TYPE_EVENT_GPS_ACCURACY_CHANGED;
	}

	@Override
	public Integer getData() {
		return this.mStatus;
	}
}
