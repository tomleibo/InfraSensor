package com.ibm.sensors.core;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.net.wifi.WifiManager;

import com.ibm.sensors.EventWrappers.MotionSensorEventWrapper;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.rules.ExtreMove;
import com.ibm.sensors.rules.Rule;
import com.ibm.sensors.rules.ruleStrategies.EventCountStrategy;
import com.ibm.sensors.sensorWrappers.AbstractHardwareSensor;
import com.ibm.sensors.sensorWrappers.AvailableWiFINetworks;
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
    public static final int MAGNETIC_FIELD_UNCALIBRATED = 14;
    public static final int GAME_ROTATION_VECTOR = 15;
    public static final int GYROSCOPE_UNCALIBRATED = 16;
    public static final int SIGNIFICANT_MOTION = 17;
    public static final int STEP_DETECTOR = 18;
    public static final int STEP_COUNTER= 19;
    public static final int GEOMAGNETIC_ROTATION_VECTOR = 20;
    public static final int HEART_RATE = 21;
    public static final int TYPE_USB_CONNECTION_TYPE = 22;
    public static final Integer LAST_HARDWARE_ID = 22;

    public static final int TYPE_AVAILABLE_WIFI_NETWORKS = 23;
    // arbitrary. decided by me.
    public static final int FILE_SIZE_CHECKER = 50;


    private static final int DELAY = 3;

    private static final int LAST_CUSTOM_RULE = 1000;
    public static final int RULE_EXTREME_MOVE = 1001;
    private final Env env;


    private Map<Integer,Integer> subscribersCount;
    private Map<Integer,SensorWrapper> sensors;

    public SensorAndRuleFactory(Env env) {
        this.env = env;
        subscribersCount = new TreeMap<>();
        sensors = new TreeMap<>();
    }




    public SensorWrapper buildAndRegisterCustomSensor(int type, Object o) {
        SensorWrapper sensor;
        switch(type) {
            case TYPE_AVAILABLE_WIFI_NETWORKS:
                sensor= new AvailableWiFINetworks((WifiManager) env.getContext().getSystemService(Context.WIFI_SERVICE),env.getEventHandler(),env.getContext(),3000);
                break;
            case FILE_SIZE_CHECKER:
                sensor = new FileSizeChecker(env.getEventHandler());
                break;
            default:
                sensor = new FileSizeChecker(env.getEventHandler());
        }
        sensor.register(DELAY,o);
        return sensor;
    }

    public SensorWrapper buildAndRegisterHardwareSensor(int type) {
        switch (type) {
            case FILE_SIZE_CHECKER:
                return new FileSizeChecker(env.getEventHandler());
            case ACCELEROMETER:
            case GYROSCOPE:
            case LINEAR_ACCELERATION:
            case ROTATION_VECTOR:
            case GRAVITY:
            case RULE_EXTREME_MOVE:
            default:
                try {
                    AbstractHardwareSensor result =  new AbstractHardwareSensor(type,env.getSensorManager(),env.getEventHandler()) {
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

    public Rule buildRule(int eventType) {
        switch (eventType) {
            case RULE_EXTREME_MOVE:
            default:
                Rule rule = new ExtreMove(env.getEventHandler(),new EventCountStrategy(SensorAndRuleFactory.ACCELEROMETER,50));
                for (Integer type: rule.getSensorTypes()) {
                    env.getEventHandler().subscribe(type,rule);
                }
                return rule;
        }
    }

    public void subscribe(int eventType) {
        Integer count = env.getEventHandler().observersCount(eventType);
        if (count == 1) {

            if (eventType < LAST_CUSTOM_RULE) {
                buildAndRegisterSensor(eventType,env.getSensorManager());
            }
            else {
                buildRule(eventType);
            }
        }
    }

    private SensorWrapper buildAndRegisterSensor(int eventType, Object o) {
        SensorWrapper sensor;
        if (eventType > LAST_HARDWARE_ID) {
            sensor = buildAndRegisterCustomSensor(eventType, null);
        }
        else  {
            sensor = buildAndRegisterHardwareSensor(eventType);
        }
        sensors.put(eventType, sensor);
        return sensor;
    }

    public void unsubscribe(EventHandler handler,Integer eventType) {
        if (handler.observersCount(eventType) == 0) {
            sensors.get(eventType).unregister(null);
        }
    }

}
