package com.ibm.sensors.EventWrappers;

import com.ibm.sensors.core.SensorAndRuleFactory;
import com.ibm.sensors.sensorWrappers.SensorWrapper;


/**
 * Created by nexus on 04/09/2015.
 */
public class USBPlugTypeEventWrapper extends AbstractEventWrapper<Integer> {
    private int mPlugType;
    private SensorWrapper mSensorWrapper;
    private Long mBuildTime;
    public USBPlugTypeEventWrapper(int plugType, SensorWrapper sw){
        super(System.currentTimeMillis(),sw);
        this.mPlugType = plugType;
        this.mSensorWrapper=sw;
        this.mBuildTime = System.currentTimeMillis();
    }

    @Override
    public int getEventType() {
        return SensorAndRuleFactory.TYPE_USB_CONNECTION_TYPE;
    }

    @Override
    public Integer getData() {
        return this.mPlugType;
    }


}
