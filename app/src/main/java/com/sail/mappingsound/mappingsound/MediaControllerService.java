package com.sail.mappingsound.mappingsound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import java.io.IOException;


public class MediaControllerService extends Service {


    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    MediaPlayer mPlayer;
    MediaRecorder mRecorder;
    boolean isRecording = false;
    boolean isPlaying = false;
    public final String LOG_TAG = "media controller";
    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        MediaControllerService getService() {
            // Return this instance of LocalService so clients can call public methods
            return MediaControllerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void startPlaying(String mFilename) {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFilename);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    public void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    public void startRecording(String mFileName) {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
        isRecording = true;
    }

    public void stopRecording() {

        try {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
            isRecording = false;
        } catch(RuntimeException ex){
            //TODO cleanup delete file, this happens when a stop is fired right after a start
        }
    }

    @Override
    public void onDestroy() {

        Log.e("hoho","should not be here");
        super.onDestroy();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
            isRecording = false;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
            isPlaying = false;
        }
    }

    public boolean isPlaying(){
        return isPlaying;
    }
    public boolean isRecording(){
        return isRecording;
    }
}
