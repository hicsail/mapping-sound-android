package com.sail.mappingsound.mappingsound;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Navigation_Record extends Fragment {

    Button record_button;
    boolean isRecording = false;

    public Navigation_Record() {
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
        record_button = (Button) view.findViewById(R.id.record_button);

        attachListeners();
        return view;
    }


    public void attachListeners(){
        record_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isRecording) {
                    record_button.setText(R.string.record_start);
                    isRecording = false;
                    //stop the recording function save and stuff
                }
                else {
                    record_button.setText(R.string.record_stop);
                    isRecording = true;
                    //start the recording function save and stuff
                }
            }
        });
    }
}
