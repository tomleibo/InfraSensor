package com.ibm.sensors.EventWrappers;

import com.google.gson.Gson;
import com.ibm.sensors.core.SensorAndRuleFactory;
import com.ibm.sensors.sensorWrappers.SensorWrapper;


/**
 * Created by nexus on 04/09/2015.
 */
public class USBPlugTypeEventWrapper implements EventWrapper<Integer> {
    private int mPlugType;
    private SensorWrapper mSensorWrapper;
    private Long mBuildTime;
    public USBPlugTypeEventWrapper(int plugType, SensorWrapper sw){
        this.mPlugType = plugType;
        this.mSensorWrapper=sw;
        this.mBuildTime = System.currentTimeMillis();
    }


    @Override
    public String toJson(Gson gson) {
        return gson.toJson(this);
    }

    @Override
    public int getEventType() {
        return SensorAndRuleFactory.TYPE_USB_CONNECTION_TYPE;
    }

    @Override
    public SensorWrapper getSensor() {
        return this.mSensorWrapper;
    }

    @Override
    public Integer getData() {
        return this.mPlugType;
    }

    @Override
    public long getTime() {
        return this.mBuildTime;
    }
}
