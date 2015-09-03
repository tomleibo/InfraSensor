package com.ibm.sensors.com.ibm.core;

import android.hardware.Sensor;

import com.ibm.sensors.com.ibm.sensors.interfaces.GenericObserver;
import com.ibm.sensors.com.ibm.sensors.interfaces.Modifier;
import com.ibm.sensors.com.ibm.utils.MultiGenericObservable;

/**
 * Created by thinkPAD on 8/30/2015.
 */
public class Example extends Rule implements GenericObserver<Float[]>{
    public Example(EventHandler handler) {
        super(handler);
    }

    private void f () {
        handler.subscribe(Sensor.TYPE_GYROSCOPE,this);
    }

    public static void main(String args[]) {

    }

    @Override
    public void update(MultiGenericObservable<Float[]> object, Float[] data) {

    }
}
