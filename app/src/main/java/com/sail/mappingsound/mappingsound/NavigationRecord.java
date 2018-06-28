package com.sail.mappingsound.mappingsound;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sail.mappingsound.mappingsound.model.RecordItem;


public class NavigationRecord extends Fragment {

    Button recordButton;
    Button saveRecordButton;
    MyItemRecyclerViewAdapter.ViewHolder recordItemView;
    boolean isRecording = false;
    String mFileName;

    public NavigationRecord() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_navigation_record, container, false);
        recordButton = (Button) view.findViewById(R.id.record_button);
        saveRecordButton = (Button) view.findViewById(R.id.save_edit);
        saveRecordButton.setVisibility(View.GONE);

        recordItemView = new MyItemRecyclerViewAdapter.ViewHolder(view);
        attachListeners();
        return view;
    }


    private void addNewRecord(){
        NavigationHistory frag = ((MainActivity)getActivity()).getNavigationHistoryFragment();
        RecordItem rec = new RecordItem(
                null,
                recordItemView.mType.getText().toString(),
                recordItemView.mPlace.getText().toString(),
                recordItemView.mName.getText().toString(),
                recordItemView.mAge.getText().toString().equals("") ?
                        null:Integer.parseInt(recordItemView.mAge.getText().toString()),
                null,
                mFileName);
        frag.getmRecordViewModel().insert(rec);
    }
    private void attachListeners(){
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isRecording) {
                    recordButton.setText(R.string.record_start);
                    isRecording = false;
                    //stop the recording function save and stuff
                    ((MainActivity)getActivity()).stopRecording();
                    //start the recording function save and stuff
                    addNewRecord();
                }
                else {
                    recordButton.setText(R.string.record_stop);
                    isRecording = true;
                    //recording
                    mFileName = getActivity().getExternalFilesDir(null).getAbsolutePath();
                    mFileName += "/audiorecordtest"+(int)(Math.random()*100000)+".3gp";
                    ((MainActivity)getActivity()).startRecording(mFileName);
                }
            }
        });
    }
}
