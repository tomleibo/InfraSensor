package com.ibm.sensors.EventWrappers;

import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.sensorWrappers.EventCreator;

import java.io.File;

/**
 * Created by thinkPAD on 9/3/2015.
 */
public class FileSizeChangedEvent extends AbstractEventWrapper<Long> {
    final File file;
    final long length;
    EventCreator sensor;

    public FileSizeChangedEvent(EventCreator sensor,File file, long length) {
        super(System.currentTimeMillis(),sensor);
        this.file=file;
        this.length=length;
    }

    @Override
    public int getEventType() {
        return EventCreatorFactory.Sensors.TYPE_SENSOR_FILE_SIZE_CHECKER;
    }

    @Override
    public Long getData() {
        return length;
    }

}
