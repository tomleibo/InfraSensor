package com.ibm.sensors.com.ibm.core;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.google.gson.Gson;
import com.ibm.sensors.com.ibm.sensors.interfaces.EventWrapper;
import com.ibm.sensors.com.ibm.sensors.interfaces.GenericObserver;
import com.ibm.sensors.com.ibm.EventWrappers.MotionSensorEventWrapper;
import com.ibm.sensors.com.ibm.utils.MultiGenericObservable;

import java.util.Map;
import java.util.TreeMap;


/**
 * EventHandler: A singleton which is the mediator between native events, events and rules.
 This component will register on input source (including rules themselves), wrap these events in a uniform structure and send the wrapped event to any rule who is registered on it.
 In the same manner events can be unregistered.
 Rules can subscribe on events and unsubscribe.
 Implements Observer and Observalbe: It observes different types of events and send events to a subscribed rule.
 Zero subscribers on an event type would imply unregistering on that event.
 Clarification: register means that the eventhandler is requesting to receive events. Subscribing means that a rule is requesting to receive events from the eventHandler.


 */
public class EventHandler extends MultiGenericObservable<EventWrapper> implements SensorEventListener{
    private static final boolean REMOTE_PROCESSING = false;
    private static final int SENSOR_DELAY = SensorManager.SENSOR_DELAY_NORMAL;
    private static final int MOTION_SENSORS_TYPES[] = {Sensor.TYPE_GRAVITY,Sensor.TYPE_GYROSCOPE,Sensor.TYPE_ROTATION_VECTOR,Sensor.TYPE_ACCELEROMETER,Sensor.TYPE_LINEAR_ACCELERATION};

    private static EventHandler instance;

    private Gson gson;
    private SensorManager sm;
    private Map<Integer,Integer> subscribersCount;

    private EventHandler() {
        subscribersCount = new TreeMap<>();
        gson = new Gson();
        // optional GsonBuilder.settingMethods()....create()
    }

    public EventHandler(SensorManager sm) {
        this();
        this.sm=sm;
    }

    public static EventHandler build() throws InstantiationException {
        if (instance==null) {
            throw new InstantiationException();
        }
        return instance;
    }

    public static EventHandler build(SensorManager sm) {
        if (instance==null) {
            synchronized (EventHandler.class) {
                if (instance==null) {
                    instance = new EventHandler(sm);
                }
            }
        }
        return instance;
    }

    public String translateEventToJson(EventWrapper event) {
        String json = event.toJson(gson);
        return json;
    }


    public void handleEvent(EventWrapper event) throws InterruptedException {
        if (REMOTE_PROCESSING) {
            CommunicationHandler.getInstance().sendEvent(event);
        }
        else {
            notifyObservers(event.getEventType(),event);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        try {
            if (isMotionEvent(event.sensor.getType())) {
                MotionSensorEventWrapper wrap = new MotionSensorEventWrapper(event);
                handleEvent(wrap);
            }
            //TODO: add more types.
        }
        catch (InterruptedException e) {
            return;
        }
    }

    private boolean isMotionEvent(int type) {
        for (int t:MOTION_SENSORS_TYPES) {
            if (t==type) {
                return  true;
            }
        }
        return false;
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public boolean subscribe(Integer eventType, GenericObserver obs) {
        boolean result = super.subscribe(eventType,obs);
        if (result) {
            Integer count = subscribersCount.get(eventType);
            if (count == null) {
                subscribersCount.put(eventType,1);
            }
            else {
                subscribersCount.put(eventType,new Integer(count+1));
            }
        }
        return result;
    }

    @Override
    public boolean unsubscribe(Integer eventType, GenericObserver<EventWrapper> obs) {
        boolean result = super.unsubscribe(eventType, obs);
        if (result) {
            subscribersCount.put(eventType,new Integer(subscribersCount.get(eventType)-1));
        }
        return result;
    }

    /*
Sandbox methods
 */

    public String getActiveSensorsStatus(SensorManager mSensorManager) {
        StringBuilder sb= new StringBuilder("");
        for (int i=0; i< MOTION_SENSORS_TYPES.length;i++) {
            sb.append("sensor type number: "+i);
            Sensor s =mSensorManager.getDefaultSensor(MOTION_SENSORS_TYPES[i]);
            if (s!=null) {
                sb.append(getSensorSpec(s));
            }
            else {
                sb.append("disabled");
            }
        }
        return sb.toString();
    }

    private String getSensorSpec(Sensor s) {
        StringBuilder sb =new StringBuilder("\n");
        sb.append("vendor: " + s.getVendor() + "\n");
        sb.append("power: " + s.getPower() + "\n");
        return sb.toString();
    }

    /**
     * @param register false for unregister
     */
    public void registerMotionSensors(boolean register) {
        Sensor sensor;
        for (int type : MOTION_SENSORS_TYPES) {
            sensor = sm.getDefaultSensor(type);
            if (sensor!=null) {
                if (register) {
                    sm.registerListener(this, sensor, SENSOR_DELAY);
                }
                else {
                    sm.unregisterListener(this, sensor);
                }
            }
        }
    }
}
