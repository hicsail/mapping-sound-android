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

    boolean isRecording = false;


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

        attachListeners();
        return view;
    }


    public void attachListeners(){
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isRecording) {
                    recordButton.setText(R.string.record_start);
                    isRecording = false;
                    //stop the recording function save and stuff
                }
                else {
                    recordButton.setText(R.string.record_stop);
                    isRecording = true;
                    //start the recording function save and stuff
                }
            }
        });

        saveRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationHistory frag = ((MainActivity)getActivity()).getNavigationHistoryFragment();
                RecordItem rec = new RecordItem(null,null,null,null,1,null,null
                ,null);
                frag.getmRecordViewModel().insert(rec);
            }
        });
    }
}
