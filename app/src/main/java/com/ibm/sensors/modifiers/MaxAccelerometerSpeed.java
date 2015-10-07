package com.ibm.sensors.modifiers;

import com.ibm.sensors.EventWrappers.EventWrapper;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by thinkPAD on 9/11/2015.
 */
public class MaxAccelerometerSpeed implements Modifier<EventWrapper<Float[]>,Float>{

    private final SortedMap<Long,Float[]> events;

    public MaxAccelerometerSpeed() {
        this.events = new TreeMap<>();
    }

    @Override
    public Float modify() {
        float speed=0;
        Map.Entry<Long,Float[]> previousEvent;
        Iterator<Map.Entry<Long,Float[]>> it= events.entrySet().iterator();
        if (!it.hasNext()) {
            return Float.valueOf(0);
        }
        previousEvent = it.next();
        while(it.hasNext()){
            Map.Entry<Long,Float[]> event = it.next();
            long timeDif = event.getKey()-previousEvent.getKey();
            Float[] prevPosition = previousEvent.getValue();
            Float[] currPosition = event.getValue();
            float distance= currPosition[1] - prevPosition[1] +currPosition[1] - prevPosition[1] +currPosition[2] - prevPosition[2];
            speed = Math.max(speed, distance/timeDif);
        }
        events.clear();
        return speed;
    }

    @Override
    public void aggregate(EventWrapper<Float[]> input) {
        events.put(input.getTime(),input.getData());
    }

    @Override
    public int clear() {
        int s = events.size();
        events.clear();
        return s;
    }


}
