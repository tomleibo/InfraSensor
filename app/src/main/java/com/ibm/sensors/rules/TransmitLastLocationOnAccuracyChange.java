package com.ibm.sensors.rules;

import android.location.LocationProvider;

import com.ibm.sensors.EventWrappers.GPSEventWrappers.GPSLocationChangedEventWrapper;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.modifiers.abstracts.AbstractSingleChangingValueModifier;
import com.ibm.sensors.modifiers.abstracts.Modifier;
import com.ibm.sensors.rules.ruleStrategies.EventCountStrategy;
import com.ibm.sensors.utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by thinkPAD on 10/15/2015.
 */
public class TransmitLastLocationOnAccuracyChange extends Rule {
    private int lastAccuracy=-1;

    public TransmitLastLocationOnAccuracyChange(Env env) {
        super(env, new EventCountStrategy(
                EventCreatorFactory.Events.TYPE_EVENT_GPS_LOCATION,Integer.MAX_VALUE,
                EventCreatorFactory.Events.TYPE_EVENT_GPS_ACCURACY_CHANGED,1));
        modifiers=new ArrayList<>();
        modifiers.add(new Pair<>(EventCreatorFactory.Events.TYPE_EVENT_GPS_LOCATION,
                new AbstractSingleChangingValueModifier() {
                    @Override
                    public Object modify() {
                        return getValue();
                    }
                }));
        modifiers.add(new Pair<>(EventCreatorFactory.Events.TYPE_EVENT_GPS_ACCURACY_CHANGED,
                new AbstractSingleChangingValueModifier() {
                    @Override
                    public Object modify() {
                        return getValue();
                    }
                }));
    }

    @Override
    public void dispatch() {
        for (Pair<Integer, Modifier> p:modifiers) {
            if (p.key.equals(EventCreatorFactory.Events.TYPE_EVENT_GPS_ACCURACY_CHANGED)) {
                int accuracyGrade = (Integer)p.value.modify();
                if (accuracyGrade == LocationProvider.OUT_OF_SERVICE) {
                    for (Pair<Integer, Modifier> p2:modifiers) {
                        if (p2.key.equals(EventCreatorFactory.Events.TYPE_EVENT_GPS_LOCATION)) {
                            env.getEventHandler().handleEvent((GPSLocationChangedEventWrapper)p2.value.modify());
                        }
                    }
                }
            }
        }

    }

    @Override
    public Collection<Integer> getSensorTypes() {
        return Arrays.asList(EventCreatorFactory.Events.TYPE_EVENT_GPS_LOCATION,
                EventCreatorFactory.Events.TYPE_EVENT_GPS_ACCURACY_CHANGED);
    }

    @Override
    public int getType() {
        return EventCreatorFactory.Events.TYPE_EVENT_GPS_TRANSMIT_LAST_LOCATION_ON_ACCURACY_CHANGE;
    }
}
