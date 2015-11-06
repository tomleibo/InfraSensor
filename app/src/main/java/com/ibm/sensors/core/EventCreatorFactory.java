package com.ibm.sensors.core;

import com.ibm.sensors.env.Env;
import com.ibm.sensors.exceptions.EventTypeDoesNotExist;
import com.ibm.sensors.rules.RuleFastDTW;
import com.ibm.sensors.rules.RuleTimeSeriesCreator;
import com.ibm.sensors.rules.SensorConfiguration;
import com.ibm.sensors.sensorWrappers.AbstractHardwareSensor;
import com.ibm.sensors.sensorWrappers.EventCreator;
import com.ibm.sensors.sensorWrappers.GPSSensorWrapper;
import com.ibm.sensors.utils.DynamicEventCreatorIdMapping;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.TreeMap;

public class EventCreatorFactory {
    public class Sensors{
        //correlated with android's Sensor.TYPE_* constants.
        public static final int TYPE_SENSOR_ACCELEROMETER =1;
        public static final int TYPE_SENSOR_MAGNETIC_FIELD = 2;
        public static final int TYPE_SENSOR_ORIENTATION = 3;
        public static final int TYPE_SENSOR_GYROSCOPE = 4;
        public static final int TYPE_SENSOR_LIGHT =5;
        public static final int TYPE_SENSOR_PRESSURE = 6;
        public static final int TYPE_SENSOR_TEMPRATURE = 7;
        public static final int TYPE_SENSOR_PROXIMITY = 8;
        public static final int TYPE_SENSOR_GRAVITY = 9;
        public static final int TYPE_SENSOR_LINEAR_ACCELERATION = 10;
        public static final int TYPE_SENSOR_ROTATION_VECTOR = 11;
        public static final int TYPE_SENSOR_RELATIVE_HUMIDITY = 12;
        public static final int TYPE_SENSOR_AMBIENT_TEMPATURE = 13;
        public static final int TYPE_SENSOR_MAGNETIC_FIELD_UNCALIBRATED = 14;
        public static final int TYPE_SENSOR_GAME_ROTATION_VECTOR = 15;
        public static final int TYPE_SENSOR_GYROSCOPE_UNCALIBRATED = 16;
        public static final int TYPE_SENSOR_SIGNIFICANT_MOTION = 17;
        public static final int TYPE_SENSOR_STEP_DETECTOR = 18;
        public static final int TYPE_SENSOR_STEP_COUNTER = 19;
        public static final int TYPE_SENSOR_GEOMAGNETIC_ROTATION_VECTOR = 20;
        public static final int TYPE_SENSOR_HEART_RATE = 21;
        public static final int TYPE_SENSOR_USB_CONNECTION_TYPE = 22;
        public static final int TYPE_SENSOR_AVAILABLE_WIFI_NETWORKS = 23;

        // 30-39 GPS
        public static final int TYPE_SENSOR_GPS = 30;
        public static final int TYPE_SENSOR_SCREEN_ON_OFF = 42;
        public static final int TYPE_SENSOR_LIGHT_SENSOR = 41;
        public static final int TYPE_SAVE_WIFI_AND_LOCATION = 77;
        public static final int TYPE_COMPARE_WIFI_AND_LOCATION = 78;

        public static final int TYPE_SENSOR_MICROPHONE = 63;
    }

    public class Events{
        public static final int TYPE_EVENT_GPS_INPUT_PROVIDER_REMOVE = 33;
        public static final int TYPE_EVENT_GPS_ACCURACY_CHANGED = 34;
        public static final int TYPE_EVENT_GPS_ACCURACY_CHANGED_EXTRAS = 35;
        public static final int TYPE_EVENT_GPS_ACCURACY_CHANGED_INPUT_PROVIDER = 36;
        public static final int WIFI_LOCATION_ON_GPS_LOST = 37;
        public static final int TYPE_EVENT_LIGHT_AMOUNT = 40;
        public static final int TYPE_EVENT_SCREEN_ON_OFF = 43;
        public static final int TYPE_EVENT_LINEAR_VELOCITY_CHANGE = 24;
        public static final int TYPE_EVENT_GPS_LOCATION = 31;
        public static final int TYPE_EVENT_GPS_INPUT_PROVIDER_ADD = 32;
        public static final int AVAILABLE_WIFI_NETWORKS = 50;
        public static final int WIFI_DISTANCE = 386;
        public static final int AUDION_RECORDING_EVENT = 51;
        public static final int PHONE_CALL_STATE_OFFHOOK = 60;
        public static final int PHONE_CALL_STATE_RINGING = 61;
        public static final int PHONE_CALL_STATE_IDLE = 62;
    }

    public class Rules{
        public static final int TYPE_RULE_EXTREME_MOVE = 1001;
        public static final int TYPE_RULE_LAST_GOOD_GPS_POINT = 1002;
        public static final int TYPE_RULE_COMPARE_DTW_SERIES = 1003;
        public static final int RuleTimeSeriesCreator = 1004;
        public static final int RuleFastDTW=1005;
    }

    public class Params {
        public static final String DELAY = "delay";
        public static final String MIN_DISTANCE = "minDistance";
    }

    private static final int FIRST_DYNAMIC_ID = 10000;
    private static final boolean CHANGE_CONFIGURATION_ON_RE_REGISTER = true;
    private static final int DEFAULT_DELAY = 50;

    private final Env env;
    private final Map<Integer,EventCreator> eventCreatorMap;
    private final DynamicEventCreatorIdMapping mapping;
    private Map<Integer,Class> eventTypeNumberToClass;

    public EventCreatorFactory(Env env) {
        this.env = env;
        mapping = new DynamicEventCreatorIdMapping(FIRST_DYNAMIC_ID);
        eventCreatorMap = new TreeMap<>();
        eventTypeNumberToClass = new TreeMap<>();

        //for all hardware motion sensors event type is as same as the sensor type.
        eventTypeNumberToClass.put(Sensors.TYPE_SENSOR_ACCELEROMETER, AbstractHardwareSensor.class);
        eventTypeNumberToClass.put(Sensors.TYPE_SENSOR_MAGNETIC_FIELD, AbstractHardwareSensor.class);
        eventTypeNumberToClass.put(Sensors.TYPE_SENSOR_ORIENTATION, AbstractHardwareSensor.class);
        eventTypeNumberToClass.put(Sensors.TYPE_SENSOR_GYROSCOPE, AbstractHardwareSensor.class);
        eventTypeNumberToClass.put(Sensors.TYPE_SENSOR_GYROSCOPE_UNCALIBRATED, AbstractHardwareSensor.class);
        eventTypeNumberToClass.put(Sensors.TYPE_SENSOR_GRAVITY, AbstractHardwareSensor.class);
        eventTypeNumberToClass.put(Sensors.TYPE_SENSOR_GAME_ROTATION_VECTOR, AbstractHardwareSensor.class);
        eventTypeNumberToClass.put(Sensors.TYPE_SENSOR_LINEAR_ACCELERATION, AbstractHardwareSensor.class);
        eventTypeNumberToClass.put(Sensors.TYPE_SENSOR_ROTATION_VECTOR, AbstractHardwareSensor.class);
        eventTypeNumberToClass.put(Sensors.TYPE_SENSOR_GEOMAGNETIC_ROTATION_VECTOR, AbstractHardwareSensor.class);
        eventTypeNumberToClass.put(Sensors.TYPE_SENSOR_MAGNETIC_FIELD_UNCALIBRATED, AbstractHardwareSensor.class);

        //GPS
        eventTypeNumberToClass.put(Events.TYPE_EVENT_GPS_LOCATION, GPSSensorWrapper.class);
        eventTypeNumberToClass.put(Events.TYPE_EVENT_GPS_ACCURACY_CHANGED, GPSSensorWrapper.class);
        eventTypeNumberToClass.put(Events.TYPE_EVENT_GPS_INPUT_PROVIDER_ADD, GPSSensorWrapper.class);
        eventTypeNumberToClass.put(Events.TYPE_EVENT_GPS_ACCURACY_CHANGED_EXTRAS, GPSSensorWrapper.class);
        eventTypeNumberToClass.put(Events.TYPE_EVENT_GPS_ACCURACY_CHANGED_INPUT_PROVIDER, GPSSensorWrapper.class);
        eventTypeNumberToClass.put(Events.TYPE_EVENT_GPS_INPUT_PROVIDER_REMOVE, GPSSensorWrapper.class);


        //Rules
        eventTypeNumberToClass.put(Rules.RuleFastDTW, RuleFastDTW.class);
        eventTypeNumberToClass.put(Rules.RuleTimeSeriesCreator, RuleTimeSeriesCreator.class);




        /*
        TODO
        public static final int TYPE_SENSOR_SCREEN_ON_OFF = 42;
        public static final int TYPE_SENSOR_LIGHT_SENSOR = 41;
        public static final int TYPE_SAVE_WIFI_AND_LOCATION = 77;
        public static final int TYPE_COMPARE_WIFI_AND_LOCATION = 78;

        public static final int TYPE_RULE_LAST_GOOD_GPS_POINT = 1002;
        public static final int TYPE_RULE_COMPARE_DTW_SERIES = 1003;

        public static final int TYPE_SENSOR_LIGHT =5;
        public static final int TYPE_SENSOR_PRESSURE = 6;
        public static final int TYPE_SENSOR_TEMPRATURE = 7;
        public static final int TYPE_SENSOR_PROXIMITY = 8;
        public static final int TYPE_SENSOR_RELATIVE_HUMIDITY = 12;
        public static final int TYPE_SENSOR_AMBIENT_TEMPATURE = 13;
        public static final int TYPE_SENSOR_SIGNIFICANT_MOTION = 17;
        public static final int TYPE_SENSOR_STEP_DETECTOR = 18;
        public static final int TYPE_SENSOR_STEP_COUNTER = 19;
        public static final int TYPE_SENSOR_HEART_RATE = 21;

         */

    }

    private EventCreator buildAndRegisterEventCreator(Class<? extends EventCreator> clz, SensorConfiguration conf) {
        try {
            Constructor<? extends EventCreator> ctor = clz.getConstructor(Env.class);
            EventCreator ec = ctor.newInstance(env);
            ec.register(conf);
            return ec;
        }
        catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void subscribe(int eventType,SensorConfiguration conf) {
        Class<? extends EventCreator> clz = eventTypeNumberToClass(eventType);
        if (clz==null) {
            throw new EventTypeDoesNotExist();
        }
        Integer count = env.getEventHandler().observersCount(eventType);

        // set initial event types.
        // map event types to sensors.

        if (conf==null) {
            conf = sensorClassToDefaultConfiguration(clz);
        }
        if (count == 1) {
            EventCreator ec = buildAndRegisterEventCreator(clz, conf);
            eventCreatorMap.put(eventType, ec);
        }
        else if (CHANGE_CONFIGURATION_ON_RE_REGISTER) {
            EventCreator ec = eventCreatorMap.get(eventType);
            ec.unregister();
            ec.register(conf);
        }
    }

    public void unsubscribe(EventHandler handler,Integer eventType) {
        if (handler.observersCount(eventType) == 0) {
            EventCreator ec =eventCreatorMap.get(eventType);
            if (ec!=null) {
                ec.unregister();
            }
            eventCreatorMap.remove(eventType);
        }
    }

    private Class<? extends EventCreator> eventTypeNumberToClass(int eventType) {
        Class<? extends EventCreator> clz =  eventTypeNumberToClass.get(eventType);
        if (clz==null) {
            clz = eventTypeNumberToClass.get(mapping.getCoreType(eventType));
        }
        return clz;
    }


    private SensorConfiguration sensorClassToDefaultConfiguration(Class<? extends EventCreator> clz) {
        //TODO add more cases
        SensorConfiguration conf = new SensorConfiguration();
        conf.addInteger(Params.DELAY,DEFAULT_DELAY);
        return conf;
    }

    public int createNewEventCreatorId(String name,int type) {
        return mapping.addMapping(name,type);
    }

    public int getIdForName(String name) {
        return mapping.getDynamicIpFromName(name);
    }

    public int getCoreTypeFromDynamicType(int type) {
        return mapping.getCoreType(type);
    }

}

