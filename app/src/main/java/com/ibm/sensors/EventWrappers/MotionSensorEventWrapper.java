package com.ibm.sensors.EventWrappers;

import android.hardware.SensorEvent;

import com.google.gson.Gson;
import com.ibm.sensors.sensorWrappers.EventCreator;

public class MotionSensorEventWrapper extends AbstractEventWrapper<Float[]> {
    private int type;
    private Float[] values;
    private int accuracy;
    private long timestamp;

    public MotionSensorEventWrapper(EventCreator sensor,int type, Float[] values, long timestamp, int accuracy) {
        super(System.currentTimeMillis(),sensor);
        construct(type,accuracy,timestamp);
        this.values=values;
    }

    private void construct(int type,int accuracy,long timestamp){
        this.type = type;
        this.accuracy = accuracy;
        this.timestamp=timestamp;
    }

    public MotionSensorEventWrapper(EventCreator sensor,int type, float[] values, long timestamp, int accuracy) {
        super(System.currentTimeMillis(),sensor);
        construct(type, accuracy,timestamp);
        this.values=new Float[values.length];
        for (int i=0;i<values.length;i++){
            this.values[i]=new Float(values[i]);
        }
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
        for (int i=0; i< Math.min(this.values.length,event.values.length);i++){
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
}
