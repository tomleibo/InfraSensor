package com.ibm.sensors.mainTests;

import com.ibm.sensors.EventWrappers.EventWrapper;
import com.ibm.sensors.MainConfigurations.MainConfInterface;
import com.ibm.sensors.env.Env;
import com.ibm.sensors.realmDb.Location;
import com.ibm.sensors.realmDb.RealmHandler;
import com.ibm.sensors.realmDb.Wifi;
import com.ibm.sensors.utils.MultiGenericObservable;

import io.realm.RealmList;

/**
 * Created by thinkPAD on 10/27/2015.
 */
public class LocationOnRealm implements MainConfInterface {
    Env env;

    @Override
    public void Main(Env env) {
        RealmHandler realm = new RealmHandler();
        try {
            realm.start(env.getContext());
            Location loc = new Location("testLocationName","desc",1,2,3,4,5,6);
            Wifi wifi = new Wifi("ssid1","macmac1",1,1);
            Wifi wifi2 = new Wifi("ssid2","macmac2",2,2);
            RealmList<Wifi> wifis = new RealmList<>();
            loc.setWifis(wifis);
            realm.persist(loc);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(MultiGenericObservable<EventWrapper> object, EventWrapper data) {

    }
}
