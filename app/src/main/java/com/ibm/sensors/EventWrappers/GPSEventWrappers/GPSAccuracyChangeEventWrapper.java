package com.ibm.sensors.EventWrappers.GPSEventWrappers;

import android.location.Location;

import com.ibm.sensors.EventWrappers.AbstractEventWrapper;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.sensorWrappers.EventCreator;

/**
 * Created by nexus on 03/10/2015.
 */
public class GPSAccuracyChangeEventWrapper  extends AbstractEventWrapper<Location> {
	private Location mLoscation;
	private String mLocationProvider;
	public GPSAccuracyChangeEventWrapper(long timestamp, EventCreator sensor, Location location) {
		super(timestamp, sensor);
		this.mLoscation=location;
	}



	@Override
	public int getEventType() {
		return EventCreatorFactory.TYPE_GPS;
	}

	@Override
	public Location getData() {
		return this.mLoscation;
	}
}
