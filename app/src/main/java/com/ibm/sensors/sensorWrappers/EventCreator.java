package com.ibm.sensors.sensorWrappers;

import com.ibm.sensors.rules.SensorConfiguration;

/**
 * Created by thinkPAD on 9/2/2015.
 */
public interface EventCreator{
    int getType();
    boolean register (SensorConfiguration configuration) throws  Exception;
    boolean unregister ();
    boolean isRegistered();
}
