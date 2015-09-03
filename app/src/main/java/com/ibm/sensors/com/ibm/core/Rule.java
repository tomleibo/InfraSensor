package com.ibm.sensors.com.ibm.core;

import com.ibm.sensors.com.ibm.sensors.interfaces.Modifier;
import com.ibm.sensors.com.ibm.utils.MultiGenericObservable;
import com.ibm.sensors.com.ibm.utils.Pair;
import com.ibm.sensors.com.ibm.EventWrappers.MotionSensorEventWrapper;

import java.util.Set;

/**
 * Rule – is a set of events that represents a new event or rule of action in the system.
 It can register or unregister receiving events through the eventHandler.
 For each event registered a specific modifier will process its data. Therefore, rule also contains a set of modifiers.
 To cover states an initial event will be created for each type of event needed by rule on registration for that event.
 (e.g.: is the phone connected to USB?)

 */
public class Rule extends MultiGenericObservable<MotionSensorEventWrapper> {
    public int id;
    protected Set<Pair<Integer,Modifier>> modifiers;
    protected EventHandler handler;

    public Rule(EventHandler handler) {
        this.handler = handler;
    }

}
