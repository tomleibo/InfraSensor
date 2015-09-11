package com.ibm.sensors.interfaces;

import com.ibm.sensors.utils.MultiGenericObservable;

/**
 * Created by thinkPAD on 8/28/2015.
 */
public interface GenericObserver<T> {
    public void update(MultiGenericObservable<T> object, T data);
}

