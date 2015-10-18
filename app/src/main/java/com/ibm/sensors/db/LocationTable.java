package com.ibm.sensors.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by thinkPAD on 10/18/2015.
 */
public class LocationTable implements Table,BaseColumns {
    public LocationTable() {
    }

    public static final String TABLE_NAME = "LOCATION";
    public static final String ID = BaseColumns._ID;
    public static final String NAME = "NAME";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String X1 = "X1";
    public static final String X2 = "X2";
    public static final String Y1 = "Y1";
    public static final String Y2 = "Y2";
    public static final String Z1 = "Z1";
    public static final String Z2 = "Z2";

    public static final String[] COLUMNS = {ID, NAME,DESCRIPTION,X1,X2,Y1,Y2,Z1,Z2};
    public static final List<String> COLUMNS_AS_LIST = Arrays.asList(COLUMNS);
    private static final SqliteColumnTypes[] COLUMN_SQL_TYPES = {
            SqliteColumnTypes.INTEGER,
            SqliteColumnTypes.TEXT,
            SqliteColumnTypes.TEXT,
            SqliteColumnTypes.REAL,
            SqliteColumnTypes.REAL,
            SqliteColumnTypes.REAL,
            SqliteColumnTypes.REAL,
            SqliteColumnTypes.REAL,
            SqliteColumnTypes.REAL,

    };
    private static final String[] COLUMN_MODIFIERS = {"PRIMARY KEY","","","","","","","",""};

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

    String name;
    String description;
    int x1;
    int x2;
    int y1;
    int y2;
    int z1;
    int z2;
    List<WifiTable> wifis;

    public LocationTable(String name, String description, int x1, int x2, int y1, int y2, int z1, int z2) {
        this.name = name;
        this.description = description;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.z1 = z1;
        this.z2 = z2;
        wifis=new ArrayList<>();
    }

    public void addWifi(WifiTable wifi){
        wifis.add(wifi);
    }

    public List<WifiTable> getWifis() {
        return wifis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public int getZ1() {
        return z1;
    }

    public void setZ1(int z1) {
        this.z1 = z1;
    }

    public int getZ2() {
        return z2;
    }

    public void setZ2(int z2) {
        this.z2 = z2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationTable that = (LocationTable) o;

        if (x1 != that.x1) return false;
        if (x2 != that.x2) return false;
        if (y1 != that.y1) return false;
        if (y2 != that.y2) return false;
        if (z1 != that.z1) return false;
        if (z2 != that.z2) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return !(description != null ? !description.equals(that.description) : that.description != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + x1;
        result = 31 * result + x2;
        result = 31 * result + y1;
        result = 31 * result + y2;
        result = 31 * result + z1;
        result = 31 * result + z2;
        return result;
    }

    @Override
    public String toString() {
        return "LocationOrm{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", x1=" + x1 +
                ", x2=" + x2 +
                ", y1=" + y1 +
                ", y2=" + y2 +
                ", z1=" + z1 +
                ", z2=" + z2 +
                '}';
    }

    @Override
    public ContentValues getInsertValues() {
        ContentValues values = new ContentValues();
        values.put(LocationTable.NAME,name);
        values.put(LocationTable.DESCRIPTION,description);
        values.put(LocationTable.X1,x1);
        values.put(LocationTable.Y1,y1);
        values.put(LocationTable.Z1,z1);
        values.put(LocationTable.X2,x2);
        values.put(LocationTable.Y2,y2);
        values.put(LocationTable.Z2,z2);
        return values;
    }

    public static LocationTable buildFromCursor(Cursor c,int offset) {
        return new LocationTable(c.getString(COLUMNS_AS_LIST.indexOf(NAME)+offset),c.getString(COLUMNS_AS_LIST.indexOf(DESCRIPTION)+offset),
                c.getInt(COLUMNS_AS_LIST.indexOf(X1)+offset),c.getInt(COLUMNS_AS_LIST.indexOf(X2)+offset),
                c.getInt(COLUMNS_AS_LIST.indexOf(Y1)+offset),c.getInt(COLUMNS_AS_LIST.indexOf(Y2)+offset),
                c.getInt(COLUMNS_AS_LIST.indexOf(Z1)+offset),c.getInt(COLUMNS_AS_LIST.indexOf(Z2)+offset));
    }

    public long insert(SQLiteDatabase writableDb) {
        long id = writableDb.insert(getTableName(),null,getInsertValues());
        for (WifiTable wifi : wifis) {
            wifi.insert(writableDb,id);
        }
        return id;
    }

}
