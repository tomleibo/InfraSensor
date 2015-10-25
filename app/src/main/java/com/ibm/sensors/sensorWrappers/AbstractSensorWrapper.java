package com.ibm.sensors.sensorWrappers;


import com.ibm.sensors.env.Env;

/**
 * Created by nexus on 02/10/2015.
 */
public abstract class AbstractSensorWrapper implements EventCreator {
    protected final Env env;
    public AbstractSensorWrapper(Env env){
        this.env=env;
    }
}
