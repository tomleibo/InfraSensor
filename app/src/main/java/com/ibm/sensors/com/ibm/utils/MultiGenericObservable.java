package com.ibm.sensors.com.ibm.utils;

/**
 * Created by thinkPAD on 8/28/2015.
 */

import com.ibm.sensors.com.ibm.sensors.interfaces.GenericObserver;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MultiGenericObservable<T> {

    private Map<Integer, LinkedList<GenericObserver<T>>> observers = new TreeMap<>();

    public boolean subscribe(Integer eventType, GenericObserver<T> obs) {
        if (obs == null) {
            throw new IllegalArgumentException("Tried to add a null observer");
        }
        LinkedList<GenericObserver<T>> sameTypeRegisteredObservers = observers.get(eventType);

        if (sameTypeRegisteredObservers == null) {
            sameTypeRegisteredObservers = new LinkedList<>();
            observers.put(eventType,sameTypeRegisteredObservers);
            return true;
        }
        else {
            if (sameTypeRegisteredObservers.contains(obs)) {
                return false;
            }
        }
        sameTypeRegisteredObservers.add(obs);
        return true;
    }

    public boolean unsubscribe(Integer eventType, GenericObserver<T> obs) {
        if (obs == null) {
            return false;
        }
        LinkedList<GenericObserver<T>> sameTypeRegisteredObservers = observers.get(eventType);

        if (sameTypeRegisteredObservers == null) {
            return false;
        } else if (sameTypeRegisteredObservers.contains(obs)) {
            sameTypeRegisteredObservers.remove(obs);
            return true;
        }
        return false;
    }

    protected void notifyObservers(Integer eventType, T data) {
        for (GenericObserver<T> obs : observers.get(eventType)) {
            obs.update(this, data);
        }
    }

    protected int observersCount(Integer eventType) {
        List<GenericObserver<T>> list =observers.get(eventType);
        if (list != null) {
            return list.size();
        }
        else {
            return 0;
        }
    }
}