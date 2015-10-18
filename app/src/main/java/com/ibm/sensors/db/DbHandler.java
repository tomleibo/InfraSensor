package com.ibm.sensors.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.ibm.sensors.EventWrappers.EventWrapper;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class DbHandler extends SQLiteOpenHelper implements Runnable {
    private static final String DATABASE_NAME = "capturedEvents";
    private static final int DATABASE_VERSION = 1;
    private static final Table[] tables = {new EventTable()};
    private final BlockingQueue<EventWrapper> eventQueue;
    private final Thread runningThread;
    private boolean shouldStop = false;
    private final Gson gson;

    public DbHandler(Context context) {
        this(context, null, null, 0);
    }

    private DbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        runningThread = new Thread(this);
        runningThread.start();
        eventQueue = new LinkedBlockingDeque<>();
        this.gson = new Gson();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        for (Table t : tables) {
            String query = getCreateTableQuery(t);
            db.execSQL(query);
            t.onCreate();
        }
    }

    private static String getCreateTableQuery(Table t) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ");
        //sb.append(DATABASE_NAME + ".");
        sb.append(t.getTableName() + "(\n");
        String[] columnNames = t.getColumnNames();
        SqliteColumnTypes[] columnTypes = t.getColumnTypes();
        String[] modifiers = t.getColumnModifiers();
        for (int i = 0; i < columnNames.length; i++) {
            sb.append(columnNames[i] + " ");
            sb.append(columnTypes[i]+ " ");
            sb.append(modifiers[i]);
            if (i < columnNames.length - 1) {
                sb.append(",\n");
            }
        }
        sb.append(");");
        return sb.toString();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (Table t :tables) {
            t.onUpgrade();
            db.execSQL("DROP TABLE IF EXISTS "+ t.getTableName());
        }
        onCreate(db);
    }

    private long insert(String tableName,ContentValues values) {
        SQLiteDatabase db;
        try {
            db = getWritableDatabase();
        }
        catch(SQLiteException e) {
            return -1;
        }
        // null is a nullable column. this is just in the case that the row is empty and no values inserted. in that case sqlite will fail. this shouldn't happen here.
        return db.insert(tableName, null, values);
    }

    public LocationTable getLocation(String where) {
        SQLiteDatabase db = getReadableDatabase();
        StringBuilder query = new StringBuilder("select * from ");
        query.append(LocationTable.TABLE_NAME);
        query.append(" t1 join ");
        query.append(WifiTable.TABLE_NAME);
        query.append(" t2 on t1.");
        query.append(LocationTable.ID);
        query.append("=t2.");
        query.append(WifiTable.LOCATION_ID);
        query.append(" ");
        query.append(where);
        /*query.append(" order by t1.");
        query.append(LocationTable.ID);
        query.append(",t2.");
        query.append();
        query.append(" limit ");
        query.append(limit);
        query.append(" offset ");
        query.append(offset);*/
        Cursor cursor = db.rawQuery(query.toString(), null);
        LocationTable location = LocationTable.buildFromCursor(cursor,0);
        while (!cursor.isAfterLast()) {
            location.addWifi(new WifiTable(cursor,LocationTable.COLUMNS.length));
            cursor.moveToNext();
        }
        return location;
    }

    /**
     * blocking.
     */
    public boolean put (EventWrapper event)  {
        try {
            eventQueue.put(event);
        }
        catch (InterruptedException e) {
            return false;
        }
        return true;
    }

    private long insertEvent(EventWrapper e) {
        ContentValues values = new ContentValues();
        values.put(EventTable.EVENT_TYPE,e.getEventType());
        values.put(EventTable.SENSOR_TYPE,e.getSensor().getType());
        values.put(EventTable.TIMESTAMP,e.getTime());
        values.put(EventTable.JSON,e.toJson(gson));
        return insert(EventTable.TABLE_NAME, values);
    }

    @Override
    public void run() {
        while(!shouldStop) {
            try {
                EventWrapper event = eventQueue.take();
                insertEvent(event);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopDb() {
        shouldStop = true;
    }
}
