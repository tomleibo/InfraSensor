package com.ibm.sensors.EventWrappers.PhoneCall;

import com.ibm.sensors.EventWrappers.AbstractEventWrapper;
import com.ibm.sensors.sensorWrappers.EventCreator;

/**
 * Created by nexus on 25/10/2015.
 */
public abstract class CallStateEventWrapper extends AbstractEventWrapper<String> {
	protected String mPhoneNumber;

	public CallStateEventWrapper(long timestamp, EventCreator sensor,int phoneState , String phoneNumber) {
		super(timestamp, sensor);
		this.mPhoneNumber = phoneNumber;
	}

	@Override
	public String getData() {
		return this.mPhoneNumber;
	}
}
