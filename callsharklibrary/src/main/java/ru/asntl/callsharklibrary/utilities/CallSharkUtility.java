package ru.asntl.callsharklibrary.utilities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Looper;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import ru.asntl.callsharklibrary.CallSharkStarter;
import ru.asntl.callsharklibrary.config.CallSharkConfig;


public class CallSharkUtility {

    public static final int PERMISSION_REQUEST_CODE_READ_EXTERNAL_STORAGE = 101;
    public static final int PERMISSION_REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 666;
    public static final int PERMISSION_REQUEST_CODE_RECORD_AUDIO = 456;
    public static final int PERMISSION_REQUEST_CODE_CAMERA = 789;


   /* public static boolean checkPermissionForStorage(Context context, Activity activity) {

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE_READ_EXTERNAL_STORAGE);
            return false;
        }

        return true;
    }

    public static boolean checkPermissionForWriteStorage(Context context, Activity activity) {

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
            return false;
        }

        return true;
    }
*/
    public static boolean checkPermissionForCamera(Context context, Activity activity){
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CODE_CAMERA);
            return false;
        }
        return true;
    }

    public static boolean checkPermissionForRecordAudio(Context context, Activity activity) {

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    PERMISSION_REQUEST_CODE_RECORD_AUDIO);
            return false;
        }
        return true;
    }

    public static void sendVideo(String videoPath) {
        if (videoPath==null){
            Toast.makeText(CallSharkStarter.getCurrentContext(), "Вы не записали видео. Повторите запись.", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(CallSharkStarter.getCurrentContext(), "Ожидайте. Видео отправляется.", Toast.LENGTH_LONG).show();
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        final File file = new File(videoPath);
                        MultipartUtility multipartUtility = new MultipartUtility(CallSharkConfig.getURLForSendFileToServer());
                        multipartUtility.addFormField("clientId", String.valueOf(CallSharkConfig.getClientId()));
                        multipartUtility.addFormField("yandexVisitorId", String.valueOf(CallSharkConfig.getYandexVisitorId()));
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

    }
}
