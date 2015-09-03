package com.ibm.sensors.com.ibm.sensors.interfaces;

import com.ibm.sensors.com.ibm.utils.MultiGenericObservable;

/**
 * Created by thinkPAD on 8/28/2015.
 */
public interface GenericObserver<T> {
    public void update(MultiGenericObservable<T> object, T data);
}

