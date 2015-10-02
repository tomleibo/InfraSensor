package com.ibm.sensors.sensorWrappers;


import com.ibm.sensors.core.EventHandler;

/**
 * Created by nexus on 02/10/2015.
 */
public abstract class AbstractSensorWrapper<REGISTERATION_PARAM> implements SensorWrapper<REGISTERATION_PARAM>{
    protected EventHandler mHandler;
    public AbstractSensorWrapper(EventHandler handler){
        this.mHandler=handler;
    }
}