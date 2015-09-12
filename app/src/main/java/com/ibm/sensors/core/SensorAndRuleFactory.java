package com.ibm.sensors.core;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

import com.ibm.sensors.EventWrappers.MotionSensorEventWrapper;
import com.ibm.sensors.rules.ExtreMove;
import com.ibm.sensors.rules.Rule;
import com.ibm.sensors.sensorWrappers.AbstractHardwareSensor;
import com.ibm.sensors.sensorWrappers.FileSizeChecker;
import com.ibm.sensors.sensorWrappers.SensorWrapper;

import java.util.Map;
import java.util.TreeMap;


/**TODO: add custom incremental event type id.*/

/**
 * This class is responsible of creation of sensors to rule types.
 * In addition it contains all Sensor type numbers and rule type numbers.
 */
public class SensorAndRuleFactory {
    //correlated with android's Sensor.TYPE_* constants.
    public static final int ACCELEROMETER=1;
    public static final int MAGNETIC_FIELD = 2;
    public static final int ORIENTATION = 3;
    public static final int GYROSCOPE = 4;
    public static final int LIGHT =5;
    public static final int PRESSURE = 6;
    public static final int TEMPRATURE = 7;
    public static final int PROXIMITY = 8;
    public static final int GRAVITY = 9;
    public static final int LINEAR_ACCELERATION = 10;
    public static final int ROTATION_VECTOR = 11;
    public static final int RELATIVE_HUMIDITY = 12;
    public static final int AMBIENT_TEMPATURE = 13;
    public static final int GAME_ROTATION_VECTOR = 15;
    public static final int MAGNETIC_FIELD_UNCALIBRATED = 14;
    public static final int GYROSCOPE_UNCALIBRATED = 16;
    public static final int SIGNIFICANT_MOTION = 17;
    public static final int STEP_DETECTOR = 18;
    public static final int STEP_COUNTER= 19;
    public static final int GEOMAGNETIC_ROTATION_VECTOR = 20;
    public static final int HEART_RATE = 21;

    public static final Integer LAST_HARDWARE_ID = 21;

    // arbitrary. decided by me.
    public static final int FILE_SIZE_CHECKER = 23;


    private static final int DELAY = 3;

    private static final int LAST_CUSTOM_RULE = 1000;
    public static final int RULE_EXTREME_MOVE = 1001;


    private static Map<Integer,Integer> subscribersCount;
    private static Map<Integer,SensorWrapper> sensors;

    //static constructor
    {
        subscribersCount = new TreeMap<>();
        sensors = new TreeMap<>();
    }


    public static SensorWrapper buildAndRegisterCustomSensor(EventHandler handler, int type, Object o) {
        SensorWrapper sensor;
        switch(type) {
            case FILE_SIZE_CHECKER:
                sensor = new FileSizeChecker(handler);
                break;
            default:
                sensor = new FileSizeChecker(handler);
        }
        sensor.register(DELAY,o);
        return sensor;
    }

    public static SensorWrapper buildAndRegisterHardwareSensor(EventHandler handler, int type, SensorManager sm) {
        switch (type) {
            case FILE_SIZE_CHECKER:
                return new FileSizeChecker(handler);
            case ACCELEROMETER:
            case GYROSCOPE:
            case LINEAR_ACCELERATION:
            case ROTATION_VECTOR:
            case GRAVITY:
            case RULE_EXTREME_MOVE:
            default:
                try {
                    AbstractHardwareSensor result =  new AbstractHardwareSensor(type,sm,handler) {
                        @Override
                        public void onSensorChanged(SensorEvent event) {
                            MotionSensorEventWrapper wrap = new MotionSensorEventWrapper(event);
                            handler.handleEvent(wrap);
                        }

                        @Override
                        public void onAccuracyChanged(Sensor sensor, int accuracy) {

                        }
                    };
                    result.register(DELAY,null);
                    return result;
                } catch (InstantiationException e) {
                    e.printStackTrace();
                    return null;
                }


        }
    }

    public static Rule buildRule(EventHandler handler, int eventType) {
        switch (eventType) {
            case RULE_EXTREME_MOVE:
            default:
                Rule rule = new ExtreMove(handler);
                for (Integer type: rule.getSensorTypes()) {
                    handler.subscribe(type,rule);
                }
                return rule;
        }
    }

    public static void subscribe(EventHandler handler,int eventType,SensorManager sm) {
        Integer count = handler.observersCount(eventType);
        if (count == 1) {

            if (eventType < LAST_CUSTOM_RULE) {
                buildAndRegisterSensor(handler,eventType,sm);
            }
            else {
                buildRule(handler,eventType);
            }
        }
    }

    private static SensorWrapper buildAndRegisterSensor(EventHandler handler, int eventType, Object o) {
        SensorWrapper sensor;
        if (eventType > LAST_HARDWARE_ID) {
            sensor = buildAndRegisterCustomSensor(handler, eventType, null);
        }
        else  {
            sensor = SensorAndRuleFactory.buildAndRegisterHardwareSensor(handler, eventType, (SensorManager)o);
        }
        sensors.put(eventType, sensor);
        return sensor;
    }

    public static void unsubscribe(EventHandler handler,Integer eventType) {
        if (handler.observersCount(eventType) == 0) {
            sensors.get(eventType).unregister(null);
        }
    }

}
