package com.ibm.sensors.rules;

import com.ibm.sensors.core.EventHandler;
import com.ibm.sensors.EventWrappers.EventWrapper;
import com.ibm.sensors.interfaces.GenericObserver;
import com.ibm.sensors.modifiers.Modifier;
import com.ibm.sensors.utils.MultiGenericObservable;
import com.ibm.sensors.utils.Pair;

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
public abstract class Rule extends MultiGenericObservable<EventWrapper> implements GenericObserver<EventWrapper>{
    protected List<Pair<Integer,Modifier>> modifiers;
    protected Map<Integer,Integer> eventCountToDispatch;
    protected Map<Integer,Integer> eventCount;
    protected EventHandler handler;

    public Rule(EventHandler handler) {
        eventCount = new TreeMap<>();
        this.handler = handler;
    }

    /**
     * should activate all modifiers and determine the result. possible results may be:
     * 1. turn on or off another rule.
     * 2. notify observers.
     */
    protected abstract void dispatch();

    public abstract Collection<Integer> getSensorTypes();

    @Override
    public void update(MultiGenericObservable<EventWrapper> object, EventWrapper data) {
        Integer type = eventCount.get(data.getSensor().getType());
        Integer count= eventCount.get(type);
        synchronized (this) {
            if (count == null) {
                eventCount.put(type, 1);
                count=1;
            }
            else {
                eventCount.put(type, ++count);
            }
            if (count == eventCountToDispatch.get(type)) {
                if (isReady()) {
                    dispatch();
                }
            }
        }
        for (Pair <Integer,Modifier> p : modifiers) {
            if (p.key == type) {
                p.value.aggregate(data);
            }
        }
    }

    public boolean isReady() {
        for (Map.Entry<Integer,Integer> entry : eventCount.entrySet()) {
            if (eventCountToDispatch.get(entry.getKey()) != entry.getValue()) {
                return false;
            }
        }
        return true;
    }
}
