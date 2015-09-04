package com.ibm.sensors.com.ibm.sensors.interfaces;

/**
 * Created by thinkPAD on 9/2/2015.
 */
public interface EventWrapper<T> extends Jsonable{
    public int getEventType();
    public SensorWrapper getSensor();
}
