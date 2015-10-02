package com.ibm.sensors.db;


import java.util.List;

public interface Table {
    String getName();
    String[] getColumnNames();
    SqliteColumnTypes[] getColumnTypes();
    String[] getColumnModifiers();
    void onCreate();
    void onUpgrade();
    boolean dropOnCreate();
}