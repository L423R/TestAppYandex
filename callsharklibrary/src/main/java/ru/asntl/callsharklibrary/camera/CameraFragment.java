package ru.asntl.callsharklibrary.camera;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Container;
import com.coremedia.iso.boxes.TimeToSampleBox;
import com.coremedia.iso.boxes.TrackBox;
import com.googlecode.mp4parser.DataSource;
import com.googlecode.mp4parser.FileDataSourceImpl;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Mp4TrackImpl;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.asntl.callsharklibrary.R;
import ru.asntl.callsharklibrary.R2;
import ru.asntl.callsharklibrary.camera.utils.AutoFitTextureView;
import ru.asntl.callsharklibrary.camera.utils.CameraVideoFragment;
import ru.asntl.callsharklibrary.config.CallSharkConfig;
import ru.asntl.callsharklibrary.utilities.CallSharkUtility;



public class CameraFragment extends CameraVideoFragment {

    private static final String TAG = "CameraFragment";
    private static final String VIDEO_DIRECTORY_NAME = "AndroidWave";
    @BindView(R2.id.mTextField)
    TextView mTextField;
    @BindView(R2.id.mBackVideo)
    ImageView mBackVideo;
    @BindView(R2.id.mDoneVideo)
    ImageView mDoneVideo;
    @BindView(R2.id.mRepeatVideo)
    ImageView mRepeatVideo;
    @BindView(value = R2.id.mTextureView)
    AutoFitTextureView mTextureView;
    @BindView(R2.id.mRecordVideo)
    ImageView mRecordVideo;
    @BindView(R2.id.mVideoView)
    VideoView mVideoView;
    @BindView(R2.id.mPlayVideo)
    ImageView mPlayVideo;
    Unbinder unbinder;
    private String mOutputFilePath;


    public CameraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */


    public static CameraFragment newInstance() {
        CameraFragment fragment = new CameraFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public int getTextureResource() {
        return R.id.mTextureView;
    }

    @Override
    protected void setUp(View view) {

    }

    @Override
    public MediaRecorder.OnInfoListener setOnInfoListenerForRecorder() {
        return new MediaRecorder.OnInfoListener() {
            @Override
            public void onInfo(MediaRecorder mr, int what, int extra) {
                if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                    Log.i("VIDEOCAPTURE","Maximum Duration Reached");
                    Toast.makeText(getActivity().getApplicationContext(), "End Recording", Toast.LENGTH_LONG).show();
                    try {
                        stopRecordingVideo();
                        prepareViews();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    @OnClick({R2.id.mRecordVideo, R2.id.mPlayVideo})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.mRecordVideo) {/**
         * If media is not recoding then start recording else stop recording
         */
            if (mIsRecordingVideo) {
                try {
                    mTextField.setVisibility(View.GONE);
                    stopRecordingVideo();
                    prepareViews();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                if (countDownTimer!=null){
                    countDownTimer.cancel();
                }
                countDownTimer = new MyCountDownTimer(CallSharkConfig.getVideoDurationLimitMs(),1000);
                mTextField.setVisibility(View.VISIBLE);
                startRecordingVideo();

                mRecordVideo.setImageResource(R.drawable.ic_stop);
                //Receive out put file here
                mOutputFilePath = getCurrentFile().getAbsolutePath();
            }
        } else if (id == R.id.mPlayVideo) {
            mVideoView.start();
            mPlayVideo.setVisibility(View.GONE);
            /*sendVideo(getCurrentFile().getAbsolutePath());
            getActivity().finish();*/
        }
    }

    @OnClick(R2.id.mDoneVideo)
    public void onDoneClicked(View view) {
        CallSharkUtility.sendVideo(getCurrentFile().getAbsolutePath());
        getActivity().finish();
    }

    @OnClick(R2.id.mBackVideo)
    public void onBackClicked(View view) {
        getActivity().finish();
    }

    @OnClick(R2.id.mRepeatVideo)
    public void onRepeatClicked(View view) {
        mVideoView.setVisibility(View.GONE);
        mTextureView.setVisibility(View.VISIBLE);
        mPlayVideo.setVisibility(View.GONE);
        mDoneVideo.setVisibility(View.GONE);
        mRepeatVideo.setVisibility(View.GONE);
        mBackVideo.setVisibility(View.GONE);
        mRecordVideo.setImageResource(R.drawable.ic_record);
    }

    /*private void sendVideo(String videoPath) {
        if (videoPath==null){
            Toast.makeText(CallSharkStarter.getCurrentContext(), "Вы не записали видео. Повторите запись.", Toast.LENGTH_LONG).show();
        }else {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                       *//* while (!isDone){

                        }
                        isDone = false;*//*
                        final File file = new File(videoPath);
                        MultipartUtility multipartUtility = new MultipartUtility("https://develop.callshark.ru/client/yandexVideo");
                        multipartUtility.addFormField("clientId", String.valueOf(1190));
                        multipartUtility.addFormField("yandexVisitorId", "0");
                        multipartUtility.addFilePart("file",file);
                        String finish = multipartUtility.finish();
                        if (finish.startsWith("OK")){
                            Looper.prepare();
                            Toast.makeText(CallSharkStarter.getCurrentContext(), "Ваше видео успешно отправлено.", Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }
                    } catch (IOException e) {
                        Looper.prepare();
                        Toast.makeText(CallSharkStarter.getCurrentContext(), "Ошибка отправки ведео.", Toast.LENGTH_LONG).show();
                        Looper.loop();
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
        }

    }*/

    private void prepareViews() {
        if (mVideoView.getVisibility() == View.GONE) {
            mVideoView.setVisibility(View.VISIBLE);
            mPlayVideo.setVisibility(View.VISIBLE);
            mDoneVideo.setVisibility(View.VISIBLE);
            mRepeatVideo.setVisibility(View.VISIBLE);
            mBackVideo.setVisibility(View.VISIBLE);
            mTextureView.setVisibility(View.GONE);
            mTextField.setVisibility(View.GONE);
            try {
                setMediaForRecordVideo();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setMediaForRecordVideo() throws IOException {
        mOutputFilePath = parseVideo(mOutputFilePath);
        // Set media controller
        /*mVideoView.setMediaController(new MediaController(getActivity()));*/
        mVideoView.requestFocus();
        mVideoView.setVideoPath(mOutputFilePath);
        mVideoView.seekTo(100);
        mVideoView.setOnCompletionListener(mp -> {
            // Reset player
           /* mVideoView.setVisibility(View.GONE);
            mTextureView.setVisibility(View.VISIBLE);
            mPlayVideo.setVisibility(View.GONE);
            mRecordVideo.setImageResource(R.drawable.ic_record);*/
            mPlayVideo.setVisibility(View.VISIBLE);
            /*mDoneVideo.setVisibility(View.VISIBLE);
            mRepeatVideo.setVisibility(View.VISIBLE);
            mBackVideo.setVisibility(View.VISIBLE);*/
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private String parseVideo(String mFilePath) throws IOException {
        DataSource channel = new FileDataSourceImpl(mFilePath);
        IsoFile isoFile = new IsoFile(channel);
        List<TrackBox> trackBoxes = isoFile.getMovieBox().getBoxes(TrackBox.class);
        boolean isError = false;
        for (TrackBox trackBox : trackBoxes) {
            TimeToSampleBox.Entry firstEntry = trackBox.getMediaBox().getMediaInformationBox().getSampleTableBox().getTimeToSampleBox().getEntries().get(0);
            // Detect if first sample is a problem and fix it in isoFile
            // This is a hack. The audio deltas are 1024 for my files, and video deltas about 3000
            // 10000 seems sufficient since for 30 fps the normal delta is about 3000
            if (firstEntry.getDelta() > 10000) {
                isError = true;
                firstEntry.setDelta(3000);
            }
        }
        File file = getOutputMediaFile();
        String filePath = file.getAbsolutePath();
        if (isError) {
            Movie movie = new Movie();
            for (TrackBox trackBox : trackBoxes) {
                movie.addTrack(new Mp4TrackImpl(channel.toString() + "[" + trackBox.getTrackHeaderBox().getTrackId() + "]", trackBox));
            }
            movie.setMatrix(isoFile.getMovieBox().getMovieHeaderBox().getMatrix());
            Container out = new DefaultMp4Builder().build(movie);

            //delete file first!
            FileChannel fc = new RandomAccessFile(filePath, "rw").getChannel();
            out.writeContainer(fc);
            fc.close();
            Log.d(TAG, "Finished correcting raw video");
            return filePath;
        }
        return mFilePath;
    }

    /**
     * Create directory and return file
     * returning video file
     */
    private File getOutputMediaFile() {
        // External sdcard file location
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(),
                VIDEO_DIRECTORY_NAME);
        // Create storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + VIDEO_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;

        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "VID_" + timeStamp + ".mp4");
        return mediaFile;
    }


    private class MyCountDownTimer extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String sSeconds;
            long allSeconds = millisUntilFinished / 1000;
            long minutes = allSeconds / 60;
            long seconds = allSeconds % 60;

            if (seconds<10){
                sSeconds = "0"+seconds;
            }else {
                sSeconds = String.valueOf(seconds);
            }

            mTextField.setText("0"+minutes+":"+sSeconds);
        }

        @Override
        public void onFinish() {
            mTextField.setText("done!");
        }
    }
}