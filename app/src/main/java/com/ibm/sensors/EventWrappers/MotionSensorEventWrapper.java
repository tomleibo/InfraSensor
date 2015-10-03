package com.ibm.sensors.EventWrappers;

import android.hardware.SensorEvent;

import com.google.gson.Gson;
import com.ibm.sensors.sensorWrappers.EventCreator;

public class MotionSensorEventWrapper extends AbstractEventWrapper<Float[]> {
    private int type;
    private float[] values;
    private int accuracy;

    public MotionSensorEventWrapper(EventCreator sensor,int type, float[] values, int timestamp, int accuracy) {
        super(System.currentTimeMillis(),sensor);
        this.type = type;
        this.values = values;
        this.accuracy = accuracy;
    }

    public MotionSensorEventWrapper(final SensorEvent event) {
        super(System.currentTimeMillis(), new EventCreator() {
            @Override
            public int getType() {
                return event.sensor.getType();
            }

            @Override
            public boolean register(int delayMillis, Object o) {
                return false;
            }

            @Override
            public boolean unregister(Object o) {
                return false;
            }

            @Override
            public boolean isRegistered() {
                return false;
            }
        });
        this.type = event.sensor.getType();
        this.values = event.values;
        this.accuracy = event.accuracy;
    }

    public int getEventType() {
        return type;
    }

    @Override
    public EventCreator getSensor() {
        return sensor;
    }

    @Override
    public Float[] getData() {
        return new Float[]{values[0],values[1],values[2]};
    }

    @Override
    public long getTime() {
        return timestamp;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float[] getValues() {
        return values;
    }

    public void setValues(float[] values) {
        this.values = values;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public String toJson(Gson gson) {
        return gson.toJson(this);
    }
}
