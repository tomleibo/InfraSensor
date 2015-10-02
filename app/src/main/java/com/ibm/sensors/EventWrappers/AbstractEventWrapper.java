package com.ibm.sensors.EventWrappers;

import com.google.gson.Gson;
import com.ibm.sensors.sensorWrappers.SensorWrapper;

/**
 * Created by thinkPAD on 10/2/2015.
 */
public abstract class AbstractEventWrapper<T> implements EventWrapper{
    long timestamp;
    SensorWrapper sensor;

    public AbstractEventWrapper(long timestamp,SensorWrapper sensor) {
        this.timestamp=timestamp;
        this.sensor = sensor;
    }

    @Override
    public SensorWrapper getSensor() {
        return sensor;
    }

    @Override
    public long getTime() {
        return timestamp;
    }

    @Override
    public String toJson(Gson gson) {
        return gson.toJson(this);
    }
}
