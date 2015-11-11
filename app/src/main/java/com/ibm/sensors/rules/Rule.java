package com.ibm.sensors.rules;

import com.ibm.sensors.EventWrappers.EventWrapper;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.interfaces.GenericObserver;
import com.ibm.sensors.modifiers.abstracts.Modifier;
import com.ibm.sensors.rules.ruleStrategies.RuleStrategy;
import com.ibm.sensors.sensorWrappers.EventCreator;
import com.ibm.sensors.utils.MultiGenericObservable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * IRRELEVANT COMMENT - NEED TO BE UPDATED.
 *
 * Rule: is a set of events that represents a new event or rule of action in the system.
 It can register or unregister receiving events through the eventHandler.
 For each event registered a specific modifier will process its data. Therefore, rule also contains a set of modifiers.
 To cover states an initial event will be created for each type of event needed by rule on registration for that event.
 (e.g.: is the phone connected to USB?)
 */
public abstract class Rule implements GenericObserver<EventWrapper>,EventCreator{
    protected Map<Integer,Modifier> modifiers;
    protected final Env env;
    protected final RuleStrategy strategy;
    protected boolean isRegistered=false;

    public Rule(Env env,RuleStrategy strategy) {
        this.modifiers = new TreeMap<>();
        this.strategy=strategy;
        this.env = env;
    }

    /**
     * should activate all modifiers and determine the result. possible results may be:
     * 1. turn on or off another rule.
     * 2. notify observers.
     */
    public abstract void dispatch();

    public abstract Collection<Integer> getSensorTypes();

    @Override
    public void update(MultiGenericObservable<EventWrapper> object, EventWrapper data) {
        Integer type = data.getEventType();
        List<Float> tmp = new ArrayList<Float>();
        tmp.add(((Float[]) data.getData())[0]);
        tmp.add(((Float[]) data.getData())[1]);
        tmp.add(((Float[]) data.getData())[2]);
        (modifiers.get(type)).aggregate(tmp);
        if (strategy.shouldDispatchOnEventArrival(type)){
            dispatch();
        }
    }

    @Override
    public boolean register(SensorConfiguration configuration) {
        boolean ans = true;
        for (Integer type: getSensorTypes()) {
            ans &= env.getEventHandler().subscribe(type, this,null);
        }
        if (ans) {
            isRegistered=true;
            return true;
        }
        for (Integer type: getSensorTypes()) {
            env.getEventHandler().unsubscribe(type, this);
        }
        isRegistered=false;
        return false;
    }

    @Override
    public boolean unregister() {
        boolean ans = true;
        for (Integer type: getSensorTypes()) {
            ans &= env.getEventHandler().unsubscribe(type, this);
        }
        isRegistered=false;
        return ans;
    }

    @Override
    public boolean isRegistered() {
        return isRegistered;
    }
}
