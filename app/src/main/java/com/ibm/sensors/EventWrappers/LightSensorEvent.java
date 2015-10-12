package com.ibm.sensors.EventWrappers;

import com.google.gson.Gson;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.sensorWrappers.EventCreator;
import com.ibm.sensors.sensorWrappers.LightSensor;

/**
 * Created by nexus on 07/10/2015.
 */
public class LightSensorEvent extends AbstractEventWrapper<Float>{
	private Float mLight;

	public LightSensorEvent(long timestamp, EventCreator sensor, Float lightAmmount) {
		super(timestamp, sensor);
		this.mLight=lightAmmount;
	}

	@Override
	public int getEventType() {
		return EventCreatorFactory.TYPE_EVENT_LIGHT_AMOUNT;
	}

	@Override
	public Float getData() {
		return this.mLight;
	}

	@Override
	public String toString() {
		return "LightSensorEvent{" +
				"mLight=" + mLight +
				'}';
	}
}
