package com.ibm.sensors.utils;

/**
 * Created by thinkPAD on 8/29/2015.
 */
public class Pair<K,V> {
    public K key;
    public V value;

    public Pair(K k, V v) {
        this.key=k;
        this.value=v;
    }
}
