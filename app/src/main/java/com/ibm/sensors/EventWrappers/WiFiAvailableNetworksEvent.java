package com.ibm.sensors.EventWrappers;

import android.net.wifi.ScanResult;

import com.ibm.sensors.core.SensorAndRuleFactory;
import com.ibm.sensors.sensorWrappers.SensorWrapper;

import java.util.List;

/**
 * Created by nexus on 04/09/2015.
 */
public class WiFiAvailableNetworksEvent extends AbstractEventWrapper<List<ScanResult>> {

    private List<ScanResult> mResults;

    public WiFiAvailableNetworksEvent(List<ScanResult> results, SensorWrapper sw){
        super(System.currentTimeMillis(),sw);
        this.mResults = results;
    }

    @Override
    public int getEventType() {
        return SensorAndRuleFactory.TYPE_AVAILABLE_WIFI_NETWORKS;
    }

    @Override
    public List<ScanResult> getData() {
        return mResults;
    }

    @Override
    public String toString() {
        StringBuilder sb =new StringBuilder();
        for (ScanResult result : mResults) {
            sb.append(result+"\n");
        }
        return sb.toString();
    }
}
