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
import android.widget.Button;
import android.widget.LinearLayout;

import com.sail.mappingsound.mappingsound.model.RecordItem;
import com.sail.mappingsound.mappingsound.viewModel.RecordItemViewModel;

import java.util.HashSet;
import java.util.List;

public class NavigationHistory extends Fragment implements OnListFragmentInteractionListener{

    private RecordItemViewModel mRecordViewModel;
    private MyItemRecyclerViewAdapter mAdapter;

    private boolean isPlaying = false;

    public NavigationHistory() {
    }

    @Override
    public void onResume() {
        super.onResume();
        isPlaying = ((MainActivity)getActivity()).isPlaying();
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
    public void onSaveEdit(RecordItem oldItem, RecordItem updatedItem) {

        oldItem.setPlace(updatedItem.getPlace());
        oldItem.setAge(updatedItem.getAge());
        oldItem.setName(updatedItem.getName());
        oldItem.setType(updatedItem.getType());

        mRecordViewModel.update(oldItem);
    }

    @Override
    public void onCollapse(ViewGroup viewGroup, final RecordItem recordItem,
                           final HashSet<Integer> expandables) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        LinearLayout detailedRecView = (LinearLayout)
                inflater.inflate(R.layout.record_item_detailed, null, false);

        final MyItemRecyclerViewAdapter.ViewHolder holder = new
                MyItemRecyclerViewAdapter.ViewHolder(detailedRecView);

        holder.mType.setText(recordItem.getType());
        holder.mPlace.setText(recordItem.getPlace());
        holder.mAge.setText(recordItem.getAge() == null? "": ""+recordItem.getAge());
        holder.mName.setText(recordItem.getName());

        Button save = holder.mView.findViewById(R.id.save_edit);
        Button del = holder.mView.findViewById(R.id.delete);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get all values and
                String type  = holder.mType.getText().toString();
                String name  = holder.mName.getText().toString();
                Integer age      = holder.mAge.getText().toString().equals("") ? null :
                        Integer.parseInt(holder.mAge.getText().toString());
                String place = holder.mPlace.getText().toString();

                onSaveEdit(recordItem, new RecordItem(null,type,place,name,age,
                        null,null));
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecordViewModel.delete(recordItem);
                expandables.remove(recordItem.getId());
            }
        });
        viewGroup.addView(detailedRecView);


    }

    @Override
    public void playPressed(String pathname) {
        if(!isPlaying){
            //MediaControllerService.startPlaying(getContext(), filename);
            ((MainActivity)getActivity()).startPlaying(pathname);
            isPlaying = true;
        } else {
            //MediaControllerService.stopPlaying(getContext());
            ((MainActivity)getActivity()).stopPlaying();
            super.onStop();
            isPlaying = false;
        }
    }


    public RecordItemViewModel getmRecordViewModel() {
        return mRecordViewModel;
    }
}
