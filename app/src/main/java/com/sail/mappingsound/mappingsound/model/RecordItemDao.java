package com.sail.mappingsound.mappingsound.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface RecordItemDao {

    @Insert
    void insert(RecordItem recordItem);

    @Update
    void update(RecordItem recordItem);

    @Delete
    void delete(RecordItem recordItem);

    @Query("DELETE FROM record_table")
    void deleteAll();

    @Query("SELECT * from record_table ORDER BY timestamp DESC")
    LiveData<List<RecordItem>> getAllrecords();

}
