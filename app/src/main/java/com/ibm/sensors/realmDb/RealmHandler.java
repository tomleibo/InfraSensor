package com.ibm.sensors.realmDb;

import android.content.Context;

import com.ibm.sensors.exceptions.DbInitalizationException;
import com.ibm.sensors.exceptions.DbPersistenceException;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by thinkPAD on 10/23/2015.
 */
public class RealmHandler {
    Realm realm;

    public Realm start(Context context) throws DbInitalizationException {
        this.realm=Realm.getInstance(context);
        if (this.realm==null) {
            throw new DbInitalizationException();
        }
        return realm;
    }

    public RealmObject persist(final RealmObject o) throws NullPointerException,DbPersistenceException {
        if (o==null) {
            throw new NullPointerException();
        }
        RealmObject result=null;
        try {
            realm.beginTransaction();
            result=realm.copyToRealm(o);
            if (result == null) {
                throw new DbPersistenceException();
            }
        }
        finally {
            realm.commitTransaction();
        }
        return result;
    }

    public void delete(RealmObject o) throws NullPointerException{
        o.removeFromRealm();
    }

    public void stop() {
        this.realm.close();
    }


}
