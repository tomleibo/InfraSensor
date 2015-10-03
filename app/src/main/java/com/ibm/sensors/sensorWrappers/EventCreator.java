package com.ibm.sensors.sensorWrappers;

/**
 * Created by thinkPAD on 9/2/2015.
 */
public interface EventCreator<REGISTERATION_PARAM>{
    public int getType();
    public  boolean register (int delayMillis, REGISTERATION_PARAM param);
    public  boolean unregister (REGISTERATION_PARAM param);
    public boolean isRegistered();
}
