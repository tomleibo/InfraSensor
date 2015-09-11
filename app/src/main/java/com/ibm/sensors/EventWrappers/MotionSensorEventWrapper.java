package com.ibm.sensors.EventWrappers;

import android.hardware.SensorEvent;

import com.google.gson.Gson;
import com.ibm.sensors.sensorWrappers.SensorWrapper;

/**
 * Created by thinkPAD on 8/18/2015.
 */
public class MotionSensorEventWrapper implements EventWrapper<Float[]> {
    private int type;
    private float[] values;
    private long timestamp;
    private int accuracy;
    private SensorWrapper sensor;

    public MotionSensorEventWrapper(SensorWrapper sensor,int type, float[] values, int timestamp, int accuracy) {
        this.type = type;
        this.values = values;
        this.timestamp = timestamp;
        this.accuracy = accuracy;
        this.sensor =sensor;
    }

    public MotionSensorEventWrapper(SensorEvent event) {
        this.type = event.sensor.getType();
        this.values = event.values;
        this.timestamp = event.timestamp;
        this.accuracy = event.accuracy;
    }

    public int getEventType() {
        return type;
    }

    @Override
    public SensorWrapper getSensor() {
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
