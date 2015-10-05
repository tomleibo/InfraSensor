package com.ibm.sensors.utils;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
    For thread safety: change map impls
 */
public class DynamicEventCreatorIdMapping {
    final Map<Integer,Integer> dynamicIdToCoreIds;
    final Map<String,Integer> nameToId;
    final AtomicInteger currId;

    public DynamicEventCreatorIdMapping(int firstId) {
        dynamicIdToCoreIds = new TreeMap<>();
        nameToId = new TreeMap<>();
        currId = new AtomicInteger(firstId);
    }

    public int addMapping(String name, int coreType) {
        if (nameToId.get(name) != null) {
            return -1;
        }
        int newId= currId.getAndIncrement();
        dynamicIdToCoreIds.put(newId,coreType);
        nameToId.put(name,newId);
        return newId;
    }

    public int getCoreType(int id) {
        return (dynamicIdToCoreIds.get(id)!=null?dynamicIdToCoreIds.get(id):-1);
    }

    public int getDynamicIpFromName(String name){
        return nameToId.get(name);
    }
}
