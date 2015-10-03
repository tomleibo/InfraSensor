package com.ibm.sensors.EventWrappers;

import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.sensorWrappers.EventCreator;


/**
 * Created by nexus on 04/09/2015.
 */
public class USBPlugTypeEventWrapper extends AbstractEventWrapper<Integer> {
    private int mPlugType;
    private EventCreator mSensorWrapper;
    private Long mBuildTime;
    public USBPlugTypeEventWrapper(int plugType, EventCreator sw){
        super(System.currentTimeMillis(),sw);
        this.mPlugType = plugType;
        this.mSensorWrapper=sw;
        this.mBuildTime = System.currentTimeMillis();
    }

    @Override
    public int getEventType() {
        return EventCreatorFactory.TYPE_USB_CONNECTION_TYPE;
    }

    @Override
    public Integer getData() {
        return this.mPlugType;
    }


}
