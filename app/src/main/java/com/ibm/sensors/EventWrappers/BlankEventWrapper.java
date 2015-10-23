package com.ibm.sensors.EventWrappers;

import com.ibm.sensors.sensorWrappers.EventCreator;

/**
 * Created by nexus on 20/10/2015.
 */
public class BlankEventWrapper<IN> extends AbstractEventWrapper<IN>{
	private int mEventType;
	private IN mData;
	public BlankEventWrapper(long timestamp, EventCreator sensor, int eventType, IN data) {
		super(timestamp, sensor);
		this.mEventType=eventType;
		this.mData=data;
	}

	@Override
	public int getEventType() {
		return mEventType;
	}

	@Override
	public IN getData() {
		return this.mData;
	}
}
