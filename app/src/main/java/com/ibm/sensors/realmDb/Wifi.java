package com.ibm.sensors.realmDb;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Wifi extends RealmObject{
    @PrimaryKey
    private String mac;
    @Required
    private String ssid;
    private int distance;
    private int level;


    public Wifi() {
    }

    public Wifi(String ssid, String mac, int distance, int level) {
        this.ssid = ssid;
        this.mac = mac;
        this.distance = distance;
        this.level = level;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
