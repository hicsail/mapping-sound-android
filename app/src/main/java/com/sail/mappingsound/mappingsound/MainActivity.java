package com.sail.mappingsound.mappingsound;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Button navigationHistoryButton;
    private Button navigationRecordButton;

    private ViewPager mViewPager;
    private Fragment fragments[];

    private String [] permissions = {Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_FINE_LOCATION};

    private static final int REQUEST_RECORD_AUDIO_AND_FINE_LOCATION_PERMISSION = 200;
    private boolean permissionAccepted = false;

    private static final String LOG_TAG = "MainActivity";

    MediaControllerService mService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, permissions,
                REQUEST_RECORD_AUDIO_AND_FINE_LOCATION_PERMISSION);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        navigationHistoryButton = (Button) findViewById(R.id.navigation_history);
        navigationRecordButton = (Button) findViewById(R.id.navigation_record);
        setupClickListener();

        fragments = new Fragment[2];
        fragments[0] = new NavigationRecord();
        fragments[1] = new NavigationHistory();

        Intent intent = new Intent(this, MediaControllerService.class);
        startService(intent);
        //bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

    }
  
    public void setupClickListener(){
        navigationRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(0);
            }
        });

        navigationHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(1);
            }
        });
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position == 0) return fragments[0];
            else return fragments[1];
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    NavigationHistory getNavigationHistoryFragment(){
        return (NavigationHistory) fragments[1];
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_AND_FINE_LOCATION_PERMISSION:
                permissionAccepted = (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED);

                break;
        }

        if (!permissionAccepted) finish();

    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MediaControllerService.LocalBinder binder = (MediaControllerService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        unbindService(mConnection);
//        mBound = false;
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
        mBound = false;
    }

    public void startRecording(String pathname){
        if (mBound) {
            // Call a method from the LocalService.
            // However, if this call were something that might hang, then this request should
            // occur in a separate thread to avoid slowing down the activity performance.
            mService.startRecording(pathname);
            Toast.makeText(this, "recording", Toast.LENGTH_SHORT).show();
        }

    }

    public void stopRecording(){
        if (mBound) {
            // Call a method from the LocalService.
            // However, if this call were something that might hang, then this request should
            // occur in a separate thread to avoid slowing down the activity performance.
            mService.stopRecording();
            Toast.makeText(this, "stopped recording", Toast.LENGTH_SHORT).show();
        }

    }

    public void startPlaying(String pathname){
        if (mBound) {
            // Call a method from the LocalService.
            // However, if this call were something that might hang, then this request should
            // occur in a separate thread to avoid slowing down the activity performance.
            mService.startPlaying(pathname);
            Toast.makeText(this, "start playing", Toast.LENGTH_SHORT).show();
        }

    }
    public void stopPlaying(){
        if (mBound) {
            // Call a method from the LocalService.
            // However, if this call were something that might hang, then this request should
            // occur in a separate thread to avoid slowing down the activity performance.
            mService.stopPlaying();
            Toast.makeText(this, "stopped playing", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, MediaControllerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    public boolean isPlaying(){
        return mService.isPlaying();
    }

    public boolean isRecording(){
        return mService.isRecording();
    }
}
