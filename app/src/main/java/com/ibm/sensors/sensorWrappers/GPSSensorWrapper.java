package com.ibm.sensors.sensorWrappers;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.ibm.sensors.EventWrappers.GPSEventWrappers.GPSAccuracyChangeEventExtrasWrapper;
import com.ibm.sensors.EventWrappers.GPSEventWrappers.GPSAccuracyChangeEventInputProvider;
import com.ibm.sensors.EventWrappers.GPSEventWrappers.GPSAccuracyChangeEventWrapper;
import com.ibm.sensors.EventWrappers.GPSEventWrappers.GPSInputProviderAdd;
import com.ibm.sensors.EventWrappers.GPSEventWrappers.GPSInputProviderRemove;
import com.ibm.sensors.EventWrappers.GPSEventWrappers.GPSLocationChangedEventWrapper;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.rules.SensorConfiguration;

/**
 * Created by nexus on 02/10/2015.
 */
public class GPSSensorWrapper extends AbstractSensorWrapper implements LocationListener {
	private final LocationManager mLocationManager;



	public GPSSensorWrapper(Env env) {
		super(env);
		LocationManager locationManager = (LocationManager)env.getContext().getSystemService(Context.LOCATION_SERVICE);
		this.mLocationManager = locationManager;
	}

	@Override
	public int getType() {
		return EventCreatorFactory.Sensors.TYPE_SENSOR_GPS;
	}

	@Override
	public boolean register(SensorConfiguration conf) {
		int delayMillis = conf.getInt(EventCreatorFactory.Params.DELAY);
		int minDistance= conf.getInt(EventCreatorFactory.Params.MIN_DISTANCE);
		this.mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, delayMillis, minDistance, this);
		return false;
	}

	@Override
	public boolean unregister() {
		this.mLocationManager.removeUpdates(this);
		return false;
	}

	@Override
	public boolean isRegistered() {
		return false;
	}

	@Override
	public void onLocationChanged(Location location) {
		env.getEventHandler().handleEvent(new GPSLocationChangedEventWrapper(System.currentTimeMillis(), this, location));
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		env.getEventHandler().handleEvent(new GPSAccuracyChangeEventWrapper(System.currentTimeMillis(), this, new Integer(status)));
		env.getEventHandler().handleEvent(new GPSAccuracyChangeEventExtrasWrapper(System.currentTimeMillis(), this, extras));
		env.getEventHandler().handleEvent(new GPSAccuracyChangeEventInputProvider(System.currentTimeMillis(), this, provider));
	}

	@Override
	public void onProviderEnabled(String provider) {
		env.getEventHandler().handleEvent(new GPSInputProviderAdd(System.currentTimeMillis(), this, provider));
	}

	@Override
	public void onProviderDisabled(String provider) {
		env.getEventHandler().handleEvent(new GPSInputProviderRemove(System.currentTimeMillis(), this, provider));
	}
}
