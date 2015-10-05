package com.ibm.sensors.sensorWrappers;

/**
 * Created by thinkPAD on 9/2/2015.
 */
public interface EventCreator<REGISTERATION_PARAM>{
    int getType();
    boolean register (int delayMillis, REGISTERATION_PARAM param);
    boolean unregister (REGISTERATION_PARAM param);
    boolean isRegistered();
}
