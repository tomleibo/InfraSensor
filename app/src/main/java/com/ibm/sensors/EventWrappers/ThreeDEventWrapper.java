package com.ibm.sensors.EventWrappers;

import com.ibm.sensors.sensorWrappers.EventCreator;

/**
 * Created by nexus on 05/10/2015.
 */
public class ThreeDEventWrapper extends AbstractEventWrapper<Float[]>{
	public Float[]  mData;
	public ThreeDEventWrapper(long timestamp, EventCreator sensor, Float[] data) {
		super(timestamp, sensor);
		this.mData = data;
	}

	@Override
	public int getEventType() {
		return 0;
	}

	@Override
	public Object getData() {
		return this.mData;
	}
}
