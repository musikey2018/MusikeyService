package com.victorsquarecity.app.services;

import android.app.Application;
import android.util.Log;


import com.victorsquarecity.app.utils.VictorConstants;
import com.victorsquarecity.app.utils.db.dao.DaoMaster;
import com.victorsquarecity.app.utils.db.dao.DaoSession;


import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;


public class VictorApp extends Application {

    private DaoSession daoSession;
    private String email;
    public static final String TAG = "VictorApp";

    @Override
    public void onCreate() {
        super.onCreate();


        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, VictorConstants.SQL_DB_NAME,null);
        Database db = helper.getWritableDb();
        if(daoSession == null){
            Log.d(TAG,"onCreate() Starting new session ");
            daoSession = new DaoMaster(db).newSession();
        }
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
