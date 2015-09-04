package com.ibm.sensors.com.ibm.EventWrappers;

import com.google.gson.Gson;
import com.ibm.sensors.com.ibm.sensors.interfaces.EventWrapper;
import com.ibm.sensors.com.ibm.sensors.interfaces.SensorWrapper;
import com.ibm.sensors.com.ibm.utils.SensorTypes;

import java.io.File;

/**
 * Created by thinkPAD on 9/3/2015.
 */
public class FileSizeChangedEvent implements EventWrapper {
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
        return SensorTypes.FILE_SIZE_CHECKER;
    }

    @Override
    public SensorWrapper getSensor() {
        return sensor;
    }

    @Override
    public String toJson(Gson gson) {
        return gson.toJson(this);
    }
}
