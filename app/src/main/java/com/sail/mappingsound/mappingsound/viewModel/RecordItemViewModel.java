package com.sail.mappingsound.mappingsound.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.sail.mappingsound.mappingsound.model.RecordItem;
import com.sail.mappingsound.mappingsound.model.RecordRepository;

import java.util.List;

public class RecordItemViewModel extends AndroidViewModel {


        private RecordRepository mRepository;

        private LiveData<List<RecordItem>> mAllRecords;

        public RecordItemViewModel (Application application) {
            super(application);
            mRepository = new RecordRepository(application);
            mAllRecords = mRepository.getAllRecords();
        }

        public LiveData<List<RecordItem>> getmAllRecords() { return mAllRecords; }

        public void insert(RecordItem recordItem) { mRepository.insert(recordItem); }
        public void update(RecordItem recordItem) { mRepository.update(recordItem); }

}
