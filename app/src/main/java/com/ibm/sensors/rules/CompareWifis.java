package com.ibm.sensors.rules;

import android.net.wifi.ScanResult;

import com.google.gson.Gson;
import com.ibm.sensors.EventWrappers.EventWrapper;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.db.LocationTable;
import com.ibm.sensors.db.WifiTable;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.modifiers.abstracts.AbstractSingleChangingValueModifier;
import com.ibm.sensors.rules.ruleStrategies.ImmidiateStrategy;
import com.ibm.sensors.sensorWrappers.EventCreator;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by thinkPAD on 10/20/2015.
 */
public class CompareWifis extends Rule {
    public CompareWifis(Env env) {
        super(env, new ImmidiateStrategy());
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
    }

    @Override
    public void dispatch() {
        List<WifiTable> wifis = (List<WifiTable>)modifiers.get(EventCreatorFactory.Events.AVAILABLE_WIFI_NETWORKS).modify();
        StringBuilder sb = new StringBuilder(" where ");
        String prefix="";
        for (WifiTable wifi : wifis) {
            sb.append(prefix);
            sb.append(WifiTable.MAC);
            sb.append(" like ");
            sb.append(wifi.getMac());
            prefix = " OR ";
        }
        LocationTable loc = env.getDbHandler().getLocation(sb.toString(),0,0);
        int distance=0;
        for (WifiTable savedWifi :loc.getWifis()) {
            for (WifiTable currWifi : wifis) {
                if (savedWifi.getMac().equals(currWifi.getMac())) {
                    distance += Math.abs(savedWifi.getLevel()-currWifi.getLevel());
                }
            }
        }
        EventWrapper<Integer> event=new EventWrapper<Integer>() {
            public long time;
            EventCreator sensor;
            int distance;

            @Override
            public String toJson(Gson gson) {
                return null;
            }

            @Override
            public int getEventType() {
                return EventCreatorFactory.Events.WIFI_DISTANCE;
            }

            @Override
            public EventCreator getSensor() {
                return sensor;
            }

            @Override
            public Integer getData() {
                return distance;
            }

            @Override
            public long getTime() {
                return time;
            }

            public EventWrapper<Integer> init(EventCreator ev, int d) {
                this.time=System.currentTimeMillis();
                this.distance=d;
                this.sensor=ev;
                return this;
            }
        }.init(this,distance);

    }

    @Override
    public Collection<Integer> getSensorTypes() {
        return null;
    }

    @Override
    public int getType() {
        return EventCreatorFactory.Sensors.TYPE_COMPARE_WIFI_AND_LOCATION;
    }
}
