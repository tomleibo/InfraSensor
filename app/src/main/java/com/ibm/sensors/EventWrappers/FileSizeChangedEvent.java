package com.ibm.sensors.EventWrappers;

import com.google.gson.Gson;
import com.ibm.sensors.sensorWrappers.SensorWrapper;
import com.ibm.sensors.core.SensorAndRuleFactory;

import java.io.File;

/**
 * Created by thinkPAD on 9/3/2015.
 */
public class FileSizeChangedEvent implements EventWrapper<Long> {
    File file;
    long length;
    SensorWrapper sensor;

    public FileSizeChangedEvent(SensorWrapper sensor,File file, long length) {
        this.file=file;
        this.length=length;
        this.sensor = sensor;
    }

    @Override
    public int getEventType() {
        return SensorAndRuleFactory.FILE_SIZE_CHECKER;
    }

    @Override
    public SensorWrapper getSensor() {
        return sensor;
    }

    @Override
    public Long getData() {
        return length;
    }

    @Override
    public long getTime() {
        return System.currentTimeMillis();
    }


    @Override
    public String toJson(Gson gson) {
        return gson.toJson(this);
    }
}
