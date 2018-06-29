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
        new InsertAsyncTask(mRecordItemDao).execute(item);
    }

    public void update (RecordItem item){
        new EditAsyncTask(mRecordItemDao).execute(item);
    }

    public void delete (RecordItem item) {
        new DelAsyncTask(mRecordItemDao).execute(item);
    }
    private static class InsertAsyncTask extends AsyncTask<RecordItem, Void, Void> {

        private RecordItemDao mAsyncTaskDao;

        InsertAsyncTask(RecordItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final RecordItem... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class EditAsyncTask extends AsyncTask<RecordItem, Void, Void> {

        private RecordItemDao mAsyncTaskDao;

        EditAsyncTask(RecordItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final RecordItem... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class DelAsyncTask extends AsyncTask<RecordItem, Void, Void> {

        private RecordItemDao mAsyncTaskDao;

        DelAsyncTask(RecordItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final RecordItem... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
