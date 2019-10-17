package ru.asntl.callsharklibrary.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ru.asntl.callsharklibrary.config.CallSharkConfig;
import ru.asntl.callsharklibrary.utilities.CallSharkUtility;

import static ru.asntl.callsharklibrary.utilities.CallSharkUtility.PERMISSION_REQUEST_CODE_CAMERA;
import static ru.asntl.callsharklibrary.utilities.CallSharkUtility.PERMISSION_REQUEST_CODE_READ_EXTERNAL_STORAGE;
import static ru.asntl.callsharklibrary.utilities.CallSharkUtility.sendVideo;

public class CallSharkVideoCaptureActivity extends AppCompatActivity {

    private String videoPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutResID = CallSharkConfig.getLayoutResIDForVideoCaptureActivity();
        setContentView(layoutResID);
        if (CallSharkUtility.checkPermissionForCamera(this,this)){
            startCameraIntent();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==PERMISSION_REQUEST_CODE_CAMERA && resultCode==RESULT_OK){
            videoPath = getRealPathFromURI(data.getData());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults[0]==-1){
           finish();
        }else {
            switch (requestCode){
                case PERMISSION_REQUEST_CODE_READ_EXTERNAL_STORAGE:
                    sendVideo(videoPath);
                    finish();
                    break;
                case PERMISSION_REQUEST_CODE_CAMERA:
                    startCameraIntent();
                    break;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void startCameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,CallSharkConfig.getVideoDurationLimit());
        // start the Video Capture Intent
        if (intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent, PERMISSION_REQUEST_CODE_CAMERA);
        }
    }

    public void sendVideoWithCheckPermission(View view) {
        if (CallSharkUtility.checkPermissionForStorage(this,this)){
            sendVideo(videoPath);
            finish();
        }
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}
