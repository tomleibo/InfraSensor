package com.ibm.sensors.EventWrappers;

import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.sensorWrappers.EventCreator;

/**
 * Created by nexus on 09/11/2015.
 */
public class BatteryPercentEvent extends AbstractEventWrapper<Integer>{
	private Integer mPercent;

	public BatteryPercentEvent(long timestamp, EventCreator sensor,Integer percent) {
		super(timestamp, sensor);
		this.mPercent=percent;
	}

	@Override
	public int getEventType() {
		return EventCreatorFactory.Events.TYPE_EVENT_BATTERY_PERCENT;
	}

	@Override
	public Integer getData() {
		return this.mPercent;
	}
}
