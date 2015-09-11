package com.ibm.sensors.EventWrappers;

import com.ibm.sensors.interfaces.Jsonable;
import com.ibm.sensors.sensorWrappers.SensorWrapper;

/**
 * Created by thinkPAD on 9/2/2015.
 */
public interface EventWrapper<T> extends Jsonable {
    public int getEventType();
    public SensorWrapper getSensor();
    public T getData();
    public long getTime();
}
