package ru.asntl.callsharklibrary.audio;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import ru.asntl.callsharklibrary.R;
import ru.asntl.callsharklibrary.config.CallSharkConfig;
import ru.asntl.callsharklibrary.utilities.FileUtility;
import ru.asntl.callsharklibrary.utilities.YandexDiskUtility;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class AudioActivity extends AppCompatActivity {

    private ImageView startbtn, stopbtn, playbtn, stopplay, mDoneAudio, mBackAudio, mRepeatAudio;
    private Chronometer chronometer;
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private static final String LOG_TAG = "AudioRecording";
    private static String mFileName = null;
    private String generateFileName;
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRecorder!=null){
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
        if (mPlayer!=null){
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
        if (chronometer!=null){
            chronometer.stop();
            chronometer = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_audio);
        startbtn = findViewById(R.id.btnRecord);
        stopbtn = findViewById(R.id.btnStop);
        playbtn = findViewById(R.id.btnPlay);
        stopplay = findViewById(R.id.btnStopPlay);
        mBackAudio = findViewById(R.id.mBackAudio);
        mDoneAudio = findViewById(R.id.mDoneAudio);
        mRepeatAudio =findViewById(R.id.mRepeatAudio);
        chronometer = findViewById(R.id.chronometerTimer);
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        generateFileName = FileUtility.generateFileName()+".3gp";
        mFileName += "/"+generateFileName;




        mDoneAudio.setOnClickListener(view -> {
            Class activitiesClass = CallSharkConfig.getActivitiesClass();
            if (activitiesClass != null){
                Intent intent = new Intent(getApplicationContext(),activitiesClass);
                intent.putExtra("fileName",generateFileName);
                new YandexDiskUtility().execute(mFileName);
                finish();
                startActivity(intent);
            }
        });

        mBackAudio.setOnClickListener(view -> finish());

        mRepeatAudio.setOnClickListener(view -> {
            stopbtn.setVisibility(View.GONE);
            startbtn.setVisibility(View.VISIBLE);
            playbtn.setVisibility(View.GONE);
            stopplay.setVisibility(View.GONE);
            mDoneAudio.setVisibility(View.GONE);
            mBackAudio.setVisibility(View.GONE);
            mRepeatAudio.setVisibility(View.GONE);
        });



        startbtn.setOnClickListener(v -> {
            if(CheckPermissions()) {

                mRecorder = new MediaRecorder();
                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mRecorder.setMaxDuration(CallSharkConfig.getAudioDurationLimitMs());
                mRecorder.setOutputFile(mFileName);

                stopbtn.setVisibility(View.VISIBLE);
                startbtn.setVisibility(View.GONE);
                playbtn.setVisibility(View.GONE);
                stopplay.setVisibility(View.GONE);
                mDoneAudio.setVisibility(View.GONE);
                mBackAudio.setVisibility(View.GONE);
                mRepeatAudio.setVisibility(View.GONE);

                mRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
                    @Override
                    public void onInfo(MediaRecorder mr, int what, int extra) {
                        if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                            Log.v("VIDEOCAPTURE","Maximum Duration Reached");
                            stopRecord();
                        }
                    }
                });



                try {
                    mRecorder.prepare();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "prepare() failed");
                }
                //starting the chronometer
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                mRecorder.start();

//                Toast.makeText(getApplicationContext(), "Recording Started", Toast.LENGTH_LONG).show();
            }
            else
            {
                RequestPermissions();
            }
        });
        stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecord();
            }
        });
        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer = new MediaPlayer();
                chronometer.setBase(SystemClock.elapsedRealtime());
                try {
                    mPlayer.setDataSource(mFileName);
                    mPlayer.prepare();
                    mPlayer.start();
                    chronometer.start();
//                    Toast.makeText(getApplicationContext(), "Recording Started Playing", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "prepare() failed");
                }

                /** once the audio is complete, timer is stopped here**/
                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopPlay();
                    }
                });

                stopbtn.setVisibility(View.GONE);
                startbtn.setVisibility(View.GONE);
                playbtn.setVisibility(View.GONE);
                stopplay.setVisibility(View.VISIBLE);
                mDoneAudio.setVisibility(View.VISIBLE);
                mBackAudio.setVisibility(View.VISIBLE);
                mRepeatAudio.setVisibility(View.VISIBLE);


            }
        });
        stopplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlay();
//                Toast.makeText(getApplicationContext(),"Playing Audio Stopped", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_AUDIO_PERMISSION_CODE:
                if (grantResults.length> 0) {
                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToStore = grantResults[1] ==  PackageManager.PERMISSION_GRANTED;
                    if (permissionToRecord && permissionToStore) {
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
    public boolean CheckPermissions() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }
    private void RequestPermissions() {
        ActivityCompat.requestPermissions(AudioActivity.this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }

    private void stopPlay(){
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());

        mPlayer.release();
        mPlayer = null;

        stopbtn.setVisibility(View.GONE);
        startbtn.setVisibility(View.GONE);
        playbtn.setVisibility(View.VISIBLE);
        stopplay.setVisibility(View.GONE);
        mDoneAudio.setVisibility(View.VISIBLE);
        mBackAudio.setVisibility(View.VISIBLE);
        mRepeatAudio.setVisibility(View.VISIBLE);
    }

    private void stopRecord(){
        mRecorder.stop();
        chronometer.stop();

        mRecorder.release();
        mRecorder = null;

        chronometer.setBase(SystemClock.elapsedRealtime());

        stopbtn.setVisibility(View.GONE);
        startbtn.setVisibility(View.GONE);
        playbtn.setVisibility(View.VISIBLE);
        stopplay.setVisibility(View.GONE);
        mDoneAudio.setVisibility(View.VISIBLE);
        mBackAudio.setVisibility(View.VISIBLE);
        mRepeatAudio.setVisibility(View.VISIBLE);
    }
}
