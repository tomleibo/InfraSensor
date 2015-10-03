package com.ibm.sensors.EventWrappers;

import android.location.Location;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import com.ibm.sensors.core.SensorAndRuleFactory;
import com.ibm.sensors.sensorWrappers.SensorWrapper;

/**
 * Created by nexus on 02/10/2015.
 */
public class GPSEventWrapper extends AbstractEventWrapper<JsonElement> {
	private Integer mLastAccuracy;
	private Location mLastLoscation;
	private String mLocationProvider;
	private Boolean mOnRegister;
	private Bundle mExtras;
	public GPSEventWrapper(long timestamp, SensorWrapper sensor, Integer accuracy , Bundle extras , Location location , String inputProvider, Boolean onRegister) {
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
				this.mOnRegister=new Boolean(true);
			}
			else{
				this.mOnRegister=new Boolean(false);
			}
			this.mLocationProvider=inputProvider;
		}
	}



	@Override
	public int getEventType() {
		return SensorAndRuleFactory.TYPE_GPS;
	}

	@Override
	public JsonElement getData() {
		return (new Gson()).toJsonTree(this);
	}
}
