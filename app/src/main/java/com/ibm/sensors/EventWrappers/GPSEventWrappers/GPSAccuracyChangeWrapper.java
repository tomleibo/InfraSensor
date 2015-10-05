package com.ibm.sensors.EventWrappers.GPSEventWrappers;

import android.os.Bundle;

/**
 * Created by nexus on 05/10/2015.
 */
public class GPSAccuracyChangeWrapper {
	public GPSAccuracyChangeWrapper(String inputProvider, Integer status, Bundle extras){
		this.mInputProvider = inputProvider;
		this.mStatus = status;
		this.mExtras = extras;
	}
	public String getmInputProvider() {
		return mInputProvider;
	}

	private String mInputProvider;

	public Integer getmStatus() {
		return mStatus;
	}

	public Bundle getmExtras() {
		return mExtras;
	}

	private Integer mStatus;
	private Bundle mExtras;
}
