package com.sail.mappingsound.mappingsound;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sail.mappingsound.mappingsound.model.RecordItem;
import com.sail.mappingsound.mappingsound.viewModel.RecordItemViewModel;

import java.util.List;

public class NavigationHistory extends Fragment implements OnListFragmentInteractionListener{

    private RecordItemViewModel mRecordViewModel;
    private MyItemRecyclerViewAdapter mAdapter;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NavigationHistory() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecordViewModel = ViewModelProviders.of(this).get(RecordItemViewModel.class);
        mAdapter = new MyItemRecyclerViewAdapter(mRecordViewModel.getmAllRecords().getValue(),
                this);
        mRecordViewModel.getmAllRecords().observe(this, new Observer<List<RecordItem>>() {
            @Override
            public void onChanged(@Nullable final List<RecordItem> recordItems) {
                // Update the cached copy of the words in the adapter.
                mAdapter.setRecords(recordItems);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_naviagtion_history, container,
                false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(mAdapter);
        }
        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onListFragmentInteraction(RecordItem item) {

    }

    public RecordItemViewModel getmRecordViewModel() {
        return mRecordViewModel;
    }
}
