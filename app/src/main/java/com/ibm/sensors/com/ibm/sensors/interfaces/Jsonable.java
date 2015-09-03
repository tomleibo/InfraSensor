package com.ibm.sensors.com.ibm.sensors.interfaces;

import com.google.gson.Gson;

/**
 * Created by thinkPAD on 8/18/2015.
 */
public interface Jsonable {
    public String toJson(Gson gson);
}
