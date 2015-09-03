package com.ibm.sensors.com.ibm.sensors.interfaces;

import com.ibm.sensors.com.ibm.core.EventHandler;

/**
 * Created by thinkPAD on 9/2/2015.
 */
public interface SensorWrapper <REGISTERATION_PARAM>{
    public int getType();
    public abstract boolean register (int delayMillis, REGISTERATION_PARAM param);
    public abstract boolean unregister (REGISTERATION_PARAM param);
}
