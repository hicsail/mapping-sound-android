package com.sail.mappingsound.mappingsound.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = {RecordItem.class}, version = 1)
public abstract class RecordRoomDatabase extends RoomDatabase{

    public abstract RecordItemDao mRecordItemDao();
    private static RecordRoomDatabase INSTANCE;

    public static RecordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RecordRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RecordRoomDatabase.class, "record_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
