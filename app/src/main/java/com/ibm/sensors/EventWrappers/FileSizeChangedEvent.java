package com.ibm.sensors.EventWrappers;

import com.ibm.sensors.core.SensorAndRuleFactory;
import com.ibm.sensors.sensorWrappers.SensorWrapper;

import java.io.File;

/**
 * Created by thinkPAD on 9/3/2015.
 */
public class FileSizeChangedEvent extends AbstractEventWrapper<Long> {
    File file;
    long length;
    SensorWrapper sensor;

    public FileSizeChangedEvent(SensorWrapper sensor,File file, long length) {
        super(System.currentTimeMillis(),sensor);
        this.file=file;
        this.length=length;
    }

    @Override
    public int getEventType() {
        return SensorAndRuleFactory.FILE_SIZE_CHECKER;
    }

    @Override
    public Long getData() {
        return length;
    }

}
