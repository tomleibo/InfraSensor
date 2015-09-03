package com.ibm.sensors.com.ibm.EventWrappers;

import android.hardware.SensorEvent;

import com.google.gson.Gson;
import com.ibm.sensors.com.ibm.sensors.interfaces.EventWrapper;
import com.ibm.sensors.com.ibm.sensors.interfaces.Jsonable;

/**
 * Created by thinkPAD on 8/18/2015.
 */
public class MotionSensorEventWrapper implements EventWrapper {
    private int type;
    private float[] values;
    private long timestamp;
    private int accuracy;

    public MotionSensorEventWrapper(int type, float[] values, int timestamp, int accuracy) {
        this.type = type;
        this.values = values;
        this.timestamp = timestamp;
        this.accuracy = accuracy;
    }

    public MotionSensorEventWrapper(SensorEvent event) {
        this.type = event.sensor.getType();
        this.values = event.values;
        this.timestamp = event.timestamp;
        this.accuracy = event.accuracy;
    }

    public int getType() {
        return type;
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
