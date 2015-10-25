package com.ibm.sensors.rules;

import android.location.Location;
import android.location.LocationProvider;
import android.net.wifi.ScanResult;

import com.ibm.sensors.EventWrappers.EventWrapper;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.db.LocationTable;
import com.ibm.sensors.db.WifiTable;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.modifiers.abstracts.AbstractSingleChangingValueModifier;
import com.ibm.sensors.rules.ruleStrategies.EventCountStrategy;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by thinkPAD on 10/15/2015.
 */
public class TransmitLastLocationOnAccuracyChange extends Rule {
    private int lastAccuracy=-1;

    public TransmitLastLocationOnAccuracyChange(Env env) {
        super(env, new EventCountStrategy(
                EventCreatorFactory.Events.TYPE_EVENT_GPS_LOCATION,Integer.MAX_VALUE,
                EventCreatorFactory.Events.AVAILABLE_WIFI_NETWORKS,Integer.MAX_VALUE,
                EventCreatorFactory.Events.TYPE_EVENT_GPS_ACCURACY_CHANGED,1));
        modifiers=new TreeMap<>();
        instance = this;
        modifiers.put(EventCreatorFactory.Events.TYPE_EVENT_GPS_LOCATION,
                new AbstractSingleChangingValueModifier() {
                    @Override
                    public Object modify() {
                        return getValue();
                    }
                });
        modifiers.put(EventCreatorFactory.Events.TYPE_EVENT_GPS_ACCURACY_CHANGED,
                new AbstractSingleChangingValueModifier() {
                    @Override
                    public Object modify() {
                        return getValue();
                    }
                });
        modifiers.put(EventCreatorFactory.Events.AVAILABLE_WIFI_NETWORKS,
                new AbstractSingleChangingValueModifier<List<ScanResult>, List<WifiTable>>() {
                    @Override
                    public List<WifiTable> modify() {
                        List<WifiTable> result = new LinkedList<WifiTable>();
                        for (ScanResult scan : getValue()) {
                            result.add(new WifiTable(scan));
                        }
                        return result;
                    }
                });
    }
    protected TransmitLastLocationOnAccuracyChange instance;
    @Override
    public void dispatch() {
        int accuracyGrade = (int)modifiers.get(EventCreatorFactory.Events.TYPE_EVENT_GPS_ACCURACY_CHANGED).modify();
        if (accuracyGrade == LocationProvider.OUT_OF_SERVICE) {
            Location location = ((EventWrapper<Location>)modifiers.get(EventCreatorFactory.Events.TYPE_EVENT_GPS_LOCATION).modify()).getData();
            List<WifiTable> wifis = (List<WifiTable>)modifiers.get(EventCreatorFactory.Events.AVAILABLE_WIFI_NETWORKS).modify();
            float acc = location.getAccuracy();
            final LocationTable locationTable = new LocationTable("","",
                    (int)Math.round(location.getLongitude() - acc),
                    (int)Math.round(location.getLongitude()+acc),
                    (int)Math.round(location.getLatitude()-acc),
                    (int)Math.round(location.getLatitude()+acc),
                    (int)Math.round(location.getAltitude()-acc),
                    (int)Math.round(location.getAltitude()+acc));
            locationTable.setWifis(wifis);
            locationTable.insert(env.getDbHandler().getWritableDatabase());
        }
        strategy.reset();


    }

    @Override
    public Collection<Integer> getSensorTypes() {
        return Arrays.asList(EventCreatorFactory.Events.TYPE_EVENT_GPS_LOCATION,
                EventCreatorFactory.Events.TYPE_EVENT_GPS_ACCURACY_CHANGED,
                EventCreatorFactory.Events.AVAILABLE_WIFI_NETWORKS);
    }

    @Override
    public int getType() {
        return EventCreatorFactory.Events.WIFI_LOCATION_ON_GPS_LOST;
    }
}

/*
env.getEventHandler().handleEvent(new EventWrapper() {

                private long time;
                TransmitLastLocationOnAccuracyChange instance;

                @Override
                public int getEventType() {
                    return EventCreatorFactory.Events.WIFI_LOCATION_ON_GPS_LOST;
                }

                @Override
                public EventCreator getSensor() {
                    return TransmitLastLocationOnAccuracyChange.this.instance;
                }

                @Override
                public LocationTable getData() {
                    return locationTable;
                }

                @Override
                public long getTime() {
                    return time;
                }

                @Override
                public String toJson(Gson gson) {
                    return "$classname{" +
                            "time=" + time +
                            ", instance=" + instance +
                            '}';
                }

                public EventWrapper init(TransmitLastLocationOnAccuracyChange thisInstance) {
                    time=System.currentTimeMillis();
                    instance = thisInstance;
                    return this;
                }
            }.init(this));
 */