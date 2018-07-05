package com.sail.mappingsound.mappingsound.model;

import android.app.Activity;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;


@Database(entities = {RecordItem.class}, version = 1)
public abstract class RecordRoomDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "record_database";
    private static RecordRoomDatabase INSTANCE;

    public static RecordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RecordRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RecordRoomDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static String exportDB(final Activity activity) {

        try {
            String currentDBPath = activity.getDatabasePath(DATABASE_NAME).getAbsolutePath();
            String backupDBPath = activity.getExternalFilesDir(null).getAbsolutePath();

            File currentDB = new File("/", currentDBPath);
            File backupDB = new File(backupDBPath, DATABASE_NAME + ".db");

            if (currentDB.exists()) {
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                return backupDB.getAbsolutePath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public abstract RecordItemDao mRecordItemDao();

}
