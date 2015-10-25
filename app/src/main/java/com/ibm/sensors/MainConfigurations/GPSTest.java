package com.ibm.sensors.MainConfigurations;

import android.content.Context;

import com.ibm.sensors.EventWrappers.EventWrapper;
import com.ibm.sensors.MainActivity;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.interfaces.MainConfInterface;
import com.ibm.sensors.utils.MultiGenericObservable;

/**
 * Created by nexus on 25/10/2015.
 */
public class GPSTest implements MainConfInterface {
	@Override
	public void Main(Env env, Context context) {

	}

	@Override
	public void onUpdate(MultiGenericObservable<EventWrapper> object, EventWrapper data) {

	}
}
