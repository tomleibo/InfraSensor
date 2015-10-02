package com.ibm.sensors.db;

import android.provider.BaseColumns;

import java.util.Arrays;
import java.util.List;

/**
* Created by thinkPAD on 10/2/2015.
*/
public class WifiTable implements Table,BaseColumns{

    public static final String TABLE_NAME = "WIFI";
    public static final String ID = BaseColumns._ID;
    public static final String SSID = "SSID";
    public static final String MAC = "MAC";
    public static final String DISTANCE = "DISTANCE";
    public static final String LEVEL = "LEVEL";

    public static final String[] COLUMNS = {ID, SSID, MAC, DISTANCE, LEVEL};
    public static final List<String> COLUMNS_AS_LIST = Arrays.asList(COLUMNS);
    private static final SqliteColumnTypes[] COLUMN_SQL_TYPES = {
            SqliteColumnTypes.INTEGER,
            SqliteColumnTypes.TEXT,
            SqliteColumnTypes.TEXT,
            SqliteColumnTypes.INTEGER,
            SqliteColumnTypes.INTEGER
    };
    private static final String[] COLUMN_MODIFIERS = {"PRIMARY KEY","","","","","","","",""};

    @Override
    public String getName() {
        return TABLE_NAME;
    }

    @Override
    public String[] getColumnNames() {
        return COLUMNS;
    }

    @Override
    public SqliteColumnTypes[] getColumnTypes() {
        return COLUMN_SQL_TYPES;
    }

    @Override
    public String[] getColumnModifiers() {
        return COLUMN_MODIFIERS;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onUpgrade() {

    }

    @Override
    public boolean dropOnCreate() {
        return true;
    }

}
