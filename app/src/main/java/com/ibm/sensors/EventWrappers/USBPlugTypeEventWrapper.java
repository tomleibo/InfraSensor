package com.ibm.sensors.EventWrappers;

import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.sensorWrappers.EventCreator;


/**
 * Created by nexus on 04/09/2015.
 */
public class USBPlugTypeEventWrapper extends AbstractEventWrapper<Integer> {
    private final int mPlugType;

    public USBPlugTypeEventWrapper(long timestamp, EventCreator sensor,Integer type) {
        super(timestamp, sensor);
        this.mPlugType=type;
    }

    @Override
    public int getEventType() {
        return EventCreatorFactory.Events.TYPE_SENSOR_USB_CONNECTION_TYPE;
    }

    @Override
    public Integer getData() {
        return this.mPlugType;
    }


}
