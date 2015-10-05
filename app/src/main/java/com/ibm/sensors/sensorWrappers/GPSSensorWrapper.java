package com.ibm.sensors.sensorWrappers;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.ibm.sensors.EventWrappers.GPSEventWrappers.GPSLocationChangedEventWrapper;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.core.EventHandler;

/**
 * Created by nexus on 02/10/2015.
 */
public class GPSSensorWrapper extends AbstractSensorWrapper<Integer> implements LocationListener {
	private final LocationManager mLocationManager;



	public GPSSensorWrapper(EventHandler handler,LocationManager locationManager) {
		super(handler);
		this.mLocationManager = locationManager;
	}

	@Override
	public int getType() {
		return EventCreatorFactory.TYPE_GPS;
	}

	@Override
	public boolean register(int delayMillis, Integer minDistance) {
		this.mLocationManager.requestLocationUpdates(
				LocationManager.GPS_PROVIDER, delayMillis, minDistance, this);
		return false;
	}

	@Override
	public boolean unregister(Integer non) {
		this.mLocationManager.removeUpdates(this);
		return false;
	}



	@Override
	public boolean isRegistered() {
		return false;
	}

	@Override
	public void onLocationChanged(Location location) {
		mHandler.handleEvent(new GPSLocationChangedEventWrapper(System.currentTimeMillis(),this,location));
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		mHandler.handleEvent(new GPSEventWrapper(System.currentTimeMillis(),this,new Integer(status),extras,null,provider,null));
	}

	@Override
	public void onProviderEnabled(String provider) {
		mHandler.handleEvent(new GPSEventWrapper(System.currentTimeMillis(),this,null,null,null,provider,new Boolean(true)));
	}

	@Override
	public void onProviderDisabled(String provider) {
		mHandler.handleEvent(new GPSEventWrapper(System.currentTimeMillis(),this,null,null,null,provider,new Boolean(false)));
	}
}
