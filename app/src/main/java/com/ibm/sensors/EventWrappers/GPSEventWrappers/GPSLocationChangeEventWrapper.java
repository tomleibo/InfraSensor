package com.ibm.sensors.EventWrappers.GPSEventWrappers;

import android.location.Location;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import com.ibm.sensors.EventWrappers.AbstractEventWrapper;
import com.ibm.sensors.core.SensorAndRuleFactory;
import com.ibm.sensors.sensorWrappers.SensorWrapper;

/**
 * Created by nexus on 02/10/2015.
 */
public class GPSLocationChangeEventWrapper extends AbstractEventWrapper<Location> {
	private Location mLoscation;
	private String mLocationProvider;
	public GPSLocationChangeEventWrapper(long timestamp, SensorWrapper sensor, Location location) {
		super(timestamp, sensor);
		this.mLoscation=location;
	}



	@Override
	public int getEventType() {
		return SensorAndRuleFactory.TYPE_GPS_LOCATION;
	}

	@Override
	public Location getData() {
		return this.mLoscation;
	}
}
