package com.ibm.sensors.EventWrappers.GPSEventWrappers;

import android.location.Location;

import com.ibm.sensors.EventWrappers.AbstractEventWrapper;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.sensorWrappers.EventCreator;

/**
 * Created by nexus on 02/10/2015.
 */
public class GPSLocationChangedEventWrapper extends AbstractEventWrapper<Location> {
	private Location mLoscation;
	private String mLocationProvider;
	public GPSLocationChangedEventWrapper(long timestamp, EventCreator sensor, Location location) {
		super(timestamp, sensor);
		this.mLoscation=location;
	}



	@Override
	public int getEventType() {
		return EventCreatorFactory.Events.TYPE_EVENT_GPS_LOCATION;
	}

	@Override
	public Location getData() {
		return this.mLoscation;
	}
}
