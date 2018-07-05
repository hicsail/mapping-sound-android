package com.sail.mappingsound.mappingsound;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.sail.mappingsound.mappingsound.model.RecordItem;
import com.sail.mappingsound.mappingsound.model.RecordRoomDatabase;

public class NavigationRecord extends Fragment implements OnLocationListener {

    Button recordButton;

    MyItemRecyclerViewAdapter.ViewHolder recordItemView;

    boolean isRecording = false;
    String mFileName;
    private String mLocations = "";
    private Button exportDatabase;


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
        View view = inflater.inflate(R.layout.fragment_navigation_record, container, false);
        recordButton = (Button) view.findViewById(R.id.record_button);
        exportDatabase = (Button) view.findViewById(R.id.export_db);

        recordItemView = new MyItemRecyclerViewAdapter.ViewHolder(view);

        attachListeners();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        isRecording = ((MainActivity) getActivity()).isRecording();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void addNewRecord() {
        NavigationHistory frag = ((MainActivity) getActivity()).getNavigationHistoryFragment();
        RecordItem rec = new RecordItem(
                null,
                null,
                null,
                null,
                null,
                mLocations,
                mFileName);
        frag.getmRecordViewModel().insert(rec);
    }

    private void attachListeners() {
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRecording) {
                    recordButton.setText(R.string.record_start);
                    isRecording = false;
                    //stop the recording function save and stuff
                    //MediaControllerService.stopRecording(getContext());

                    //start the recording function save and stuff
                    addNewRecord();
                    ((MainActivity) getActivity()).stopRecording();
                } else {
                    recordButton.setText(R.string.record_stop);
                    isRecording = true;
                    //recording
                    mFileName = getActivity().getExternalFilesDir(null).getAbsolutePath();
                    mFileName += "/audiorecordtest" + (int) (Math.random() * 100000) + ".3gp";
                    //reset locations
                    mLocations = "";
                    ((MainActivity) getActivity()).startRecording(mFileName);
                }
            }
        });

        exportDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ExportAsyncTask().execute();
            }
        });
    }

    @Override
    public void onLocationReceived(double latitude, double longitude) {
        mLocations += latitude + "," + longitude;
    }


    class ExportAsyncTask extends AsyncTask<Void, Void, String> {
        ProgressDialog progDailog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progDailog = new ProgressDialog(getContext());
            progDailog.setMessage("exporting database...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(false);
            progDailog.show();
        }

        @Override
        protected String doInBackground(Void... args) {
            //do something while spinning circling show
            return RecordRoomDatabase.exportDB(getActivity());
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            progDailog.dismiss();
            Toast.makeText(getActivity(), res == null ? "Error exporting db" :
                    "Database Exported to" + res, Toast.LENGTH_LONG).show();
        }
    }
}
