package com.ibm.sensors.EventWrappers;

import android.net.wifi.ScanResult;

import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.sensorWrappers.EventCreator;

import java.util.List;

/**
 * Created by nexus on 04/09/2015.
 */
public class WiFiAvailableNetworksEvent extends AbstractEventWrapper<List<ScanResult>> {

    private final List<ScanResult> mResults;

    public WiFiAvailableNetworksEvent(List<ScanResult> results, EventCreator sw){
        super(System.currentTimeMillis(),sw);
        this.mResults = results;
    }

    @Override
    public int getEventType() {
        return EventCreatorFactory.Sensors.TYPE_SENSOR_AVAILABLE_WIFI_NETWORKS;
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
