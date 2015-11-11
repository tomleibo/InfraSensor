package com.ibm.sensors.MainConfigurations;

import android.app.Activity;
import android.content.Context;

import com.ibm.sensors.EventWrappers.EventWrapper;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.utils.MultiGenericObservable;

/**
 * Created by nexus on 25/10/2015.
 */
public class GPSTest extends AbstractMainActivityConf {
	@Override
	protected void _Main(Env env, Activity ac) {

	}


	@Override
	public void update(MultiGenericObservable<EventWrapper> object, EventWrapper data) {

	}
}
