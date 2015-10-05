package com.ibm.sensors.EventWrappers.GPSEventWrappers;

import android.location.Location;
import android.os.Bundle;

import com.ibm.sensors.EventWrappers.AbstractEventWrapper;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.sensorWrappers.EventCreator;

/**
 * Created by nexus on 03/10/2015.
 */
public class GPSAccuracyChangeEventWrapper  extends AbstractEventWrapper<GPSAccuracyChangeWrapper> {
	private GPSAccuracyChangeWrapper mData;
	public GPSAccuracyChangeEventWrapper(long timestamp, EventCreator sensor, String provider, Integer status, Bundle extras) {
		super(timestamp, sensor);
		this.mData= new GPSAccuracyChangeWrapper(provider,status,extras);
	}



	@Override
	public int getEventType() {
		return EventCreatorFactory.TYPE_GPS;
	}

	@Override
	public GPSAccuracyChangeWrapper getData() {
		return this.mData;
	}
}
