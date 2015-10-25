package com.ibm.sensors.rules;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thinkPAD on 10/25/2015.
 */
public class SensorConfiguration {
    Map<String,String> stringMap=null;
    Map<String,Integer> integerMap=null;

    public SensorConfiguration() {
        stringMap=new HashMap<>();
        integerMap=new HashMap<>();
    }

    public SensorConfiguration addString(String key,String value) {
        stringMap.put(key,value);
        return this;
    }

    public SensorConfiguration addInteger(String key,Integer value) {
        integerMap.put(key,value);
        return this;
    }

    public String getString(String key) {
        return stringMap.get(key);
    }

    public Integer getInt(String key) {
        return integerMap.get(key);
    }

}
