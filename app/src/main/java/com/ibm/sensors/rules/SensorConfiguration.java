package com.ibm.sensors.rules;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thinkPAD on 10/25/2015.
 */
public class SensorConfiguration {
    public static String DELAY = "DELAY";
    public static String REPEAT = "REPEAT";
    public static String DURATION = "DURATION";
    Map<String,Object> objectMap=null;
    public SensorConfiguration() {
        objectMap=new HashMap<>();
    }

    public SensorConfiguration addObject(String key,Object o) {
        objectMap.put(key,o);
        return this;
    }

    public Object getObject(String key) { return objectMap.get(key);}
}
