package com.victorsquarecity.app.utils;

/**
 * Created by mujahidmasood on 28.08.17.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.victorsquarecity.app.utils.db.dao.DaoMaster;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

public class VictorUtil {

    private static final String TAG = "VictorUtil";


    public synchronized static boolean isFirstLaunchNonPersistent(Context context) {
        boolean launchFlag = false;
        String sID = null;

        File installation = new File(context.getFilesDir(), VictorConstants.APP_FILE);
        Log.d(TAG, "isFirstLaunchNonPersistent() getFilesDir Flag " + context.getFilesDir() + "  file" + installation);
        try {
            Log.d(TAG, "isFirstLaunchNonPersistent() installation exists " + installation.exists());
            if (!installation.exists()) {
                launchFlag = true;
                String id = UUID.randomUUID().toString();
                writeInstallationFile(installation,id);
            }

            sID = readInstallationFile(installation);
            Log.d(TAG,"isFirstLaunchNonPersistent() sID "+sID);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Log.d(TAG, "isFirstLaunchNonPersistent() Launch Flag " + launchFlag);
        return launchFlag;
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();

        return new String(bytes);
    }

    private static void writeInstallationFile(File installation,String data) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        out.write(data.getBytes());
        out.close();
    }

    public static void clearDatabase(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(
                context.getApplicationContext(), VictorConstants.SQL_DB_NAME, null);
        SQLiteDatabase db = devOpenHelper.getWritableDatabase();
        devOpenHelper.onUpgrade(db, 0, 0);
    }


}

