package com.ibm.sensors.MainConfigurations;

import android.content.Context;

import com.ibm.sensors.EventWrappers.EventWrapper;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.interfaces.GenericObserver;
import com.ibm.sensors.utils.MultiGenericObservable;

/**
 * Created by nexus on 25/10/2015.
 */
public interface MainConfInterface extends GenericObserver<EventWrapper> {
	public void Main(Env env);
}
