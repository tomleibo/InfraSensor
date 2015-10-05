package com.ibm.sensors.EventWrappers;

import android.location.Location;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.sensorWrappers.EventCreator;

/**
 * Created by nexus on 02/10/2015.
 */
public class GPSEventWrapper extends AbstractEventWrapper<JsonElement> {
	private Integer mLastAccuracy;
	private Location mLastLoscation;
	private String mLocationProvider;
	private Boolean mOnRegister;
	private Bundle mExtras;
	public GPSEventWrapper(long timestamp, EventCreator sensor, Integer accuracy , Bundle extras , Location location , String inputProvider, Boolean onRegister) {
		super(timestamp, sensor);
		this.mLastAccuracy=null;
		this.mLastLoscation=null;
		this.mExtras=null;
		this.mOnRegister=null;
		this.mLocationProvider=null;
		if (accuracy!=null){
			this.mLastAccuracy=accuracy;
			this.mExtras=extras;
		}
		if (location!=null){
			this.mLastLoscation=location;
		}
		if (inputProvider!=null){
			if (onRegister){
				this.mOnRegister=Boolean.valueOf(true);
			}
			else{
				this.mOnRegister=Boolean.valueOf(false);
			}
			this.mLocationProvider=inputProvider;
		}
	}



	@Override
	public int getEventType() {
		return EventCreatorFactory.TYPE_GPS;
	}

	@Override
	public JsonElement getData() {
		return (new Gson()).toJsonTree(this);
	}
}
