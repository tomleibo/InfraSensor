package com.ibm.sensors.rules;

import com.google.gson.Gson;
import com.ibm.sensors.EventWrappers.EventWrapper;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.modifiers.MaxAccelerometerSpeed;
import com.ibm.sensors.modifiers.abstracts.Modifier;
import com.ibm.sensors.rules.ruleStrategies.RuleStrategy;
import com.ibm.sensors.sensorWrappers.EventCreator;
import com.ibm.sensors.utils.MultiGenericObservable;
import com.ibm.sensors.utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ExtreMove extends Rule {

    private static final int EXTREME_SPEED = 0;

    public ExtreMove(Env env,RuleStrategy strategy) {
        super(env,strategy);
        modifiers = new ArrayList<>();
        modifiers.add(new Pair<Integer, Modifier>(EventCreatorFactory.Sensors.TYPE_SENSOR_ACCELEROMETER,new MaxAccelerometerSpeed()));
    }

    @Override
    public void update(MultiGenericObservable<EventWrapper> object, EventWrapper data) {
        super.update(object, data);
    }

    @Override
    public void dispatch() {
        for (Pair<Integer,Modifier> p : modifiers) {
            final float speed = (float) p.value.modify();
            if (speed > EXTREME_SPEED) {
                env.getEventHandler().handleEvent(new EventWrapper<Float>() {
                    @Override
                    public int getEventType() {
                        return EventCreatorFactory.Rules.TYPE_RULE_EXTREME_MOVE;
                    }

                    @Override
                    public EventCreator getSensor() {
                        return null;
                    }

                    @Override
                    public Float getData() {
                        return Float.valueOf(speed);
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
        return Arrays.asList(EventCreatorFactory.Sensors.TYPE_SENSOR_ACCELEROMETER);
    }

    @Override
    public int getType() {
        return EventCreatorFactory.Rules.TYPE_RULE_EXTREME_MOVE;
    }

}
