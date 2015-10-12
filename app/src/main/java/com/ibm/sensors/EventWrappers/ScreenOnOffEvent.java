package com.ibm.sensors.EventWrappers;

import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.sensorWrappers.EventCreator;

/**
 * Created by nexus on 07/10/2015.
 */
public class ScreenOnOffEvent extends AbstractEventWrapper<Boolean>{
	private Boolean isScreenOn;
	public ScreenOnOffEvent(long timestamp, EventCreator sensor,Boolean isScreenOn) {
		super(timestamp, sensor);
		this.isScreenOn=isScreenOn;
	}

	@Override
	public int getEventType() {
		return EventCreatorFactory.TYPE_EVENT_SCREEN_ON_OFF;
	}

	@Override
	public Object getData() {
		return isScreenOn;
	}

	@Override
	public String toString() {
		return "ScreenOnOffEvent{" +
				"isScreenOn=" + isScreenOn +
				'}';
	}
}
