package com.ibm.sensors.env;

import android.content.Context;
import android.hardware.SensorManager;

import com.ibm.sensors.core.CommunicationHandler;
import com.ibm.sensors.core.EventHandler;
import com.ibm.sensors.core.SensorAndRuleFactory;
import com.ibm.sensors.db.DbHandler;

public class Env {
    private static final String URL = "";
    private SensorAndRuleFactory sensorFactory;
    private EventHandler eventHandler;
    private CommunicationHandler communicationHandler;
    private DbHandler dbHandler;
    private Context context;
    private SensorManager sensorManager;

    public Env(Context context) {
        this.context = context;
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.eventHandler = EventHandler.build(this);
        this.communicationHandler = CommunicationHandler.build(URL);
        this.dbHandler = new DbHandler(context);
        this.sensorFactory = new SensorAndRuleFactory(this);
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    public CommunicationHandler getCommunicationHandler() {
        return communicationHandler;
    }

    public DbHandler getDbHandler() {
        return dbHandler;
    }

    public Context getContext() {
        return context;
    }

    public SensorManager getSensorManager() {
        return sensorManager;
    }

    public SensorAndRuleFactory getSensorFactory() {
        return sensorFactory;
    }
}
