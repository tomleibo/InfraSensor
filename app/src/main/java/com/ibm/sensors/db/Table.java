package com.ibm.sensors.db;


import android.content.ContentValues;

public interface Table {
    String getTableName();
    String[] getColumnNames();
    SqliteColumnTypes[] getColumnTypes();
    String[] getColumnModifiers();
    void onCreate();
    void onUpgrade();
    boolean dropOnCreate();
    ContentValues getInsertValues();
}