package com.ibm.sensors.rules;

import com.ibm.sensors.core.EventHandler;
import com.ibm.sensors.EventWrappers.EventWrapper;
import com.ibm.sensors.interfaces.GenericObserver;
import com.ibm.sensors.modifiers.Modifier;
import com.ibm.sensors.rules.ruleStrategies.RuleStrategy;
import com.ibm.sensors.utils.MultiGenericObservable;
import com.ibm.sensors.utils.Pair;

import java.util.Collection;
import java.util.List;

/**
 * IRRELEVANT COMMENT - NEED TO BE UPDATED.
 *
 * Rule: is a set of events that represents a new event or rule of action in the system.
 It can register or unregister receiving events through the eventHandler.
 For each event registered a specific modifier will process its data. Therefore, rule also contains a set of modifiers.
 To cover states an initial event will be created for each type of event needed by rule on registration for that event.
 (e.g.: is the phone connected to USB?)
 */
public abstract class Rule extends MultiGenericObservable<EventWrapper> implements GenericObserver<EventWrapper>{
    protected List<Pair<Integer,Modifier>> modifiers;
    protected EventHandler handler;
    protected RuleStrategy strategy;


    public Rule(EventHandler handler,RuleStrategy strategy) {
        this.strategy=strategy;
        this.handler = handler;
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
        for (Pair <Integer,Modifier> p : modifiers) {
            if (p.key == type) {
                p.value.aggregate(data);
            }
        }
        if (strategy.shouldDispatchOnEventArrival(type)){
            dispatch();
        }
    }

}
