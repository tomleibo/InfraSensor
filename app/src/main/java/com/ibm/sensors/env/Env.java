package com.ibm.sensors.env;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.PowerManager;

import com.ibm.sensors.core.CommunicationHandler;
import com.ibm.sensors.core.EventHandler;
import com.ibm.sensors.core.EventCreatorFactory;
import com.ibm.sensors.db.DbHandler;

public class Env {
    private static final String URL = "";
    private final EventCreatorFactory sensorFactory;
    private final EventHandler eventHandler;
    private final CommunicationHandler communicationHandler;
    private final DbHandler dbHandler;
    private final Context context;
    private final SensorManager sensorManager;
    private PowerManager powerManager;

    public Env(Context context) {
        this.context = context;
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.eventHandler = EventHandler.build(this);
        this.communicationHandler = CommunicationHandler.build(URL);
        this.dbHandler = new DbHandler(context);
        this.sensorFactory = new EventCreatorFactory(this);
        this.powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
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

    public PowerManager getPowerManager(){return powerManager;}

    public EventCreatorFactory getSensorFactory() {
        return sensorFactory;
    }
}
