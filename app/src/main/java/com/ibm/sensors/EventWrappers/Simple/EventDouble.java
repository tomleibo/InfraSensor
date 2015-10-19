package com.ibm.sensors.EventWrappers.Simple;

import com.ibm.sensors.EventWrappers.AbstractEventWrapper;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.sensorWrappers.EventCreator;

/**
 * Created by nexus on 18/10/2015.
 */
public class EventDouble extends AbstractEventWrapper<Double> {
	Double mData;
	public EventDouble(long timestamp, EventCreator sensor,Double data) {
		super(timestamp, sensor);
		this.mData=data;
	}

	@Override
	public int getEventType() {
		return 0;
	}

	@Override
	public Double getData() {
		return mData;
	}
}
