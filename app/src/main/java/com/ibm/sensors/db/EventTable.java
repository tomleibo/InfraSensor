package com.ibm.sensors.db;

import android.provider.BaseColumns;

import java.util.Arrays;
import java.util.List;

/**
 * Created by thinkPAD on 9/20/2015.
 */
public class EventTable implements Table,BaseColumns {
    public static final String TABLE_NAME = "EVENT";
    public static final String ID = BaseColumns._ID;
    public static final String EVENT_TYPE = "EVENT_TYPE";
    public static final String SENSOR_TYPE = "SENSOR_TYPE";
    public static final String TIMESTAMP = "TIMESTAMP";
    public static final String JSON = "JSON";

    public static final String[] COLUMNS = {ID, EVENT_TYPE,SENSOR_TYPE,TIMESTAMP, JSON};
    public static final List<String> COLUMNS_AS_LIST = Arrays.asList(COLUMNS);
    private static final SqliteColumnTypes[] COLUMN_SQL_TYPES = {
            SqliteColumnTypes.INTEGER,
            SqliteColumnTypes.INTEGER,
            SqliteColumnTypes.INTEGER,
            SqliteColumnTypes.UNSIGNED_BIG_INT,
            SqliteColumnTypes.TEXT
    };
    private static final String[] COLUMN_MODIFIERS = {"PRIMARY KEY","","","","","","","",""};
    private static final int INDEX_OF_ID = COLUMNS_AS_LIST.indexOf(ID);
    private static final int INDEX_OF_ACTION = COLUMNS_AS_LIST.indexOf(EVENT_TYPE);
    private static final int INDEX_OF_ACTION_STR = COLUMNS_AS_LIST.indexOf(SENSOR_TYPE);
    private static final int INDEX_OF_X = COLUMNS_AS_LIST.indexOf(TIMESTAMP);
    private static final int INDEX_OF_Y = COLUMNS_AS_LIST.indexOf(JSON);

    public EventTable() {
    }

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
