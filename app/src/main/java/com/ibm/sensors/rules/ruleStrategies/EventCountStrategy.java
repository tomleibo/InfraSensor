package com.ibm.sensors.rules.ruleStrategies;

import com.ibm.sensors.rules.Rule;
import com.ibm.sensors.utils.Pair;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by thinkPAD on 9/12/2015.
 */
public class EventCountStrategy implements RuleStrategy {
    protected Map<Integer,Integer> eventCountToDispatch;
    protected Map<Integer,Integer> eventCount;

    public EventCountStrategy(int... typesAndCounts) {
        this.eventCountToDispatch = new TreeMap<>();
        this.eventCount = new TreeMap<>();
        for (int i=0; i<typesAndCounts.length; i+=2) {
            eventCountToDispatch.put(typesAndCounts[i],typesAndCounts[i+1]);
        }
        reset();
    }

    public boolean isReady() {
        for (Map.Entry<Integer,Integer> entry : eventCount.entrySet()) {
            if (eventCountToDispatch.get(entry.getKey()).intValue() > entry.getValue().intValue()) {
                return false;
            }
        }
        reset();
        return true;
    }

    @Override
    public boolean shouldDispatchOnEventArrival(int type) {
        Integer count= eventCount.get(type);
        synchronized (this) {
            if (count == null) {
                eventCount.put(type, 1);
                count=1;
            }
            else {
                eventCount.put(type, ++count);
            }
            if (count.equals(eventCountToDispatch.get(type))) {
                if (isReady()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void reset() {
        for (Integer type : eventCount.keySet()) {
            eventCount.put(type,0);
        }
    }
}
