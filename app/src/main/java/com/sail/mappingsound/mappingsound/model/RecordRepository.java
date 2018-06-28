package com.sail.mappingsound.mappingsound.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class RecordRepository {
    private RecordItemDao mRecordItemDao;
    private LiveData<List<RecordItem>> mAllRecords;

    public RecordRepository(Application application) {
        RecordRoomDatabase db = RecordRoomDatabase.getDatabase(application);
        mRecordItemDao = db.mRecordItemDao();
        mAllRecords = mRecordItemDao.getAllrecords();
    }

    public LiveData<List<RecordItem>> getAllRecords() {
        return mAllRecords;
    }


    public void insert (RecordItem item) {
        new insertAsyncTask(mRecordItemDao).execute(item);
    }

    private static class insertAsyncTask extends AsyncTask<RecordItem, Void, Void> {

        private RecordItemDao mAsyncTaskDao;

        insertAsyncTask(RecordItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final RecordItem... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
