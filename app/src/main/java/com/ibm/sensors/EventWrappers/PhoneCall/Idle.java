package com.ibm.sensors.EventWrappers.PhoneCall;

import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.sensorWrappers.EventCreator;

/**
 * Created by nexus on 25/10/2015.
 */
public class Idle extends CallStateEventWrapper{
	public Idle(long timestamp, EventCreator sensor, int phoneState, String phoneNumber) {
		super(timestamp, sensor, phoneState, phoneNumber);
	}

	@Override
	public int getEventType() {
		return EventCreatorFactory.Events.PHONE_CALL_STATE_IDLE;
	}
}
