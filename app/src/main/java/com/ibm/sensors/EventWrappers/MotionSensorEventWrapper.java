package com.ibm.sensors.EventWrappers;

import android.hardware.SensorEvent;

import com.google.gson.Gson;
import com.ibm.sensors.rules.SensorConfiguration;
import com.ibm.sensors.sensorWrappers.EventCreator;

import java.util.Arrays;

public class MotionSensorEventWrapper extends AbstractEventWrapper<Float[]> {
    private int type;
    private Float[] values;
    private int accuracy;
    private long timestamp;

    public MotionSensorEventWrapper(EventCreator sensor,int type, Float[] values, long timestamp, int accuracy) {
        super(System.currentTimeMillis(),sensor);
        this.type = type;
        this.accuracy = accuracy;
        this.timestamp= timestamp;
        this.values=values;
    }

    public MotionSensorEventWrapper(final SensorEvent event) {
        super(System.currentTimeMillis(), new EventCreator() {
            @Override
            public int getType() {
                return event.sensor.getType();
            }

            @Override
            public boolean register(SensorConfiguration s) {
                return false;
            }

            @Override
            public boolean unregister() {
                return false;
            }

            @Override
            public boolean isRegistered() {
                return false;
            }
        });
        this.type = event.sensor.getType();
        values=new Float[3];
        for (int i=0; i< event.values.length;i++){
            this.values[i] = event.values[i];
        }

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

    public Float[] getValues() {
        return values;
    }

    public void setValues(float[] values) {
        for (int i=0; i< Math.min(this.values.length,values.length);i++){
            this.values[i] = values[i];
        }
    }

    public void setValues(Float[] values) {
        this.values = values;
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

    @Override
    public String toString() {
        return "MotionSensorEventWrapper{" +
                "type=" + type +
                ", values=" + Arrays.toString(values) +
                ", accuracy=" + accuracy +
                ", timestamp=" + timestamp +
                '}';
    }
}
