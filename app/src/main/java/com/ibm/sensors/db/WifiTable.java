package com.ibm.sensors.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    public static final String LOCATION_ID = "LOCATION_ID";

    public static final String[] COLUMNS = {ID, SSID, MAC, DISTANCE, LEVEL,LOCATION_ID};
    public static final List<String> COLUMNS_AS_LIST = Arrays.asList(COLUMNS);
    private static final SqliteColumnTypes[] COLUMN_SQL_TYPES = {
            SqliteColumnTypes.INTEGER,
            SqliteColumnTypes.TEXT,
            SqliteColumnTypes.TEXT,
            SqliteColumnTypes.INTEGER,
            SqliteColumnTypes.INTEGER,
            SqliteColumnTypes.INTEGER
    };
    private static final String[] COLUMN_MODIFIERS = {"PRIMARY KEY","","","","","","","","",""};

    @Override
    public String getTableName() {
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

    private String ssid;
    private String mac;
    private int distance;
    private int level;
    private long locationId=-1;

    public WifiTable(){}

    public WifiTable(String ssid, String mac, int distance, int level, long locationId) {
        this.ssid = ssid;
        this.mac = mac;
        this.distance = distance;
        this.level = level;
        this.locationId = locationId;
    }

    public WifiTable(Cursor c, int offset) {
        this.ssid = c.getString(COLUMNS_AS_LIST.indexOf(SSID)+offset);
        this.mac = c.getString(COLUMNS_AS_LIST.indexOf(MAC) + offset);
        this.distance = c.getInt(COLUMNS_AS_LIST.indexOf(DISTANCE) + offset);
        this.level = c.getInt(COLUMNS_AS_LIST.indexOf(LEVEL) + offset);
    }

    @Override
    public String toString() {
        return "WifiTable{" +
                "ssid='" + ssid + '\'' +
                ", mac='" + mac + '\'' +
                ", distance=" + distance +
                ", level=" + level +
                ", locationId=" + locationId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WifiTable wifiOrm = (WifiTable) o;

        if (distance != wifiOrm.distance) return false;
        if (level != wifiOrm.level) return false;
        if (locationId != wifiOrm.locationId) return false;
        if (ssid != null ? !ssid.equals(wifiOrm.ssid) : wifiOrm.ssid != null) return false;
        return !(mac != null ? !mac.equals(wifiOrm.mac) : wifiOrm.mac != null);

    }

    @Override
    public int hashCode() {
        int result = ssid != null ? ssid.hashCode() : 0;
        result = 31 * result + (mac != null ? mac.hashCode() : 0);
        result = 31 * result + distance;
        result = 31 * result + level;
        result = 31 * result + (int) (locationId ^ (locationId >>> 32));
        return result;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    @Override
    public ContentValues getInsertValues() {
        return getInsertValues(locationId);
    }

    public long insert(SQLiteDatabase writableDb,long locationId) {
        return writableDb.insert(getTableName(),null,getInsertValues());
    }

    public ContentValues getInsertValues(long location) {
        ContentValues values = new ContentValues();
        values.put(WifiTable.DISTANCE,distance);
        values.put(WifiTable.SSID,ssid);
        values.put(WifiTable.MAC,mac);
        values.put(WifiTable.LEVEL,level);
        values.put(WifiTable.LOCATION_ID,location);
        return values;
    }

}
