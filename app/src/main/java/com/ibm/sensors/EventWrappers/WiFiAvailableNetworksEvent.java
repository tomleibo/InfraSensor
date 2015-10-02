package com.ibm.sensors.EventWrappers;

import android.net.wifi.ScanResult;
import com.google.gson.Gson;
import com.ibm.sensors.EventWrappers.EventWrapper;
import com.ibm.sensors.core.SensorAndRuleFactory;
import com.ibm.sensors.sensorWrappers.SensorWrapper;


import java.util.List;

/**
 * Created by nexus on 04/09/2015.
 */
public class WiFiAvailableNetworksEvent implements EventWrapper {

    private List<ScanResult> mResults;

    public WiFiAvailableNetworksEvent(List<ScanResult> results){
        this.mResults = results;
    }

    @Override
    public String toJson(Gson gson) {
        return gson.toJson(this);
    }

    @Override
    public int getEventType() {
        return SensorAndRuleFactory.TYPE_AVAILABLE_WIFI_NETWORKS;
    }

    @Override
    public SensorWrapper getSensor() {
        return null;
    }

    @Override
    public Object getData() {
        return null;
    }

    @Override
    public long getTime() {
        return 0;
    }
}
