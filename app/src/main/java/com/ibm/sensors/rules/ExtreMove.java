package com.ibm.sensors.rules;

import com.google.gson.Gson;
import com.ibm.sensors.core.EventHandler;
import com.ibm.sensors.core.SensorAndRuleFactory;
import com.ibm.sensors.EventWrappers.EventWrapper;
import com.ibm.sensors.modifiers.Modifier;
import com.ibm.sensors.sensorWrappers.SensorWrapper;
import com.ibm.sensors.modifiers.MaxAccelerometerSpeed;
import com.ibm.sensors.utils.MultiGenericObservable;
import com.ibm.sensors.utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.TreeMap;

public class ExtreMove extends Rule {

    private static final int EXTREME_SPEED = 50;

    public ExtreMove(EventHandler handler) {
        super(handler);
        eventCountToDispatch = new TreeMap<>();
        eventCountToDispatch.put(SensorAndRuleFactory.ACCELEROMETER, 500);
        modifiers = new ArrayList<>();
        modifiers.add(new Pair<Integer, Modifier>(0,new MaxAccelerometerSpeed()));
    }

    @Override
    public void update(MultiGenericObservable<EventWrapper> object, EventWrapper data) {
        super.update(object, data);
        for (Pair<Integer,Modifier> m : modifiers) {
            m.value.aggregate(data);
        }
    }

    @Override
    protected void dispatch() {
        for (Pair<Integer,Modifier> p : modifiers) {
            final int speed = (int) p.value.modify();
            if (speed > EXTREME_SPEED) {
                handler.handleEvent(new EventWrapper<Integer>() {
                    @Override
                    public int getEventType() {
                        return SensorAndRuleFactory.RULE_EXTREME_MOVE;
                    }
                    @Override
                    public SensorWrapper getSensor() {
                        return null;
                    }

                    @Override
                    public Integer getData() {
                        return speed;
                    }

                    @Override
                    public long getTime() {
                        return System.currentTimeMillis();
                    }
                    @Override
                    public String toJson(Gson gson) {
                        return "wooooooooow";
                    }
                });
            }
        }
    }

    @Override
    public Collection<Integer> getSensorTypes() {
        return Arrays.asList(new Integer[]{SensorAndRuleFactory.ACCELEROMETER});
    }
}
