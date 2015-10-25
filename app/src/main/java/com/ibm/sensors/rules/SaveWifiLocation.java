package com.ibm.sensors.rules;

import android.location.Location;
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

/**
 * Created by thinkPAD on 10/20/2015.
 */
public class SaveWifiLocation extends Rule {
    public SaveWifiLocation(Env env) {
        super(env, new EventCountStrategy(EventCreatorFactory.Events.AVAILABLE_WIFI_NETWORKS,1,
                EventCreatorFactory.Events.TYPE_EVENT_GPS_LOCATION,1));
        modifiers.put(EventCreatorFactory.Events.AVAILABLE_WIFI_NETWORKS,
                new AbstractSingleChangingValueModifier<List<ScanResult>, List<WifiTable>>() {
                    @Override
                    public List<WifiTable> modify() {
                        List<WifiTable> result = new LinkedList<>();
                        for (ScanResult scan : getValue()) {
                            result.add(new WifiTable(scan));
                        }
                        return result;
                    }
                });
        modifiers.put(EventCreatorFactory.Events.TYPE_EVENT_GPS_LOCATION, new AbstractSingleChangingValueModifier() {
            @Override
            public Object modify() {
                return getValue();
            }
        });
    }

    @Override
    public void dispatch() {
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
        strategy.reset();
    }

    @Override
    public Collection<Integer> getSensorTypes() {
        return Arrays.asList(EventCreatorFactory.Events.AVAILABLE_WIFI_NETWORKS);
    }

    @Override
    public int getType() {
        return EventCreatorFactory.Sensors.TYPE_SAVE_WIFI_AND_LOCATION;
    }
}
