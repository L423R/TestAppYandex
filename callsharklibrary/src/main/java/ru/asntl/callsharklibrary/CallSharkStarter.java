package ru.asntl.callsharklibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ru.asntl.callsharklibrary.camera.CameraActivity;
import ru.asntl.callsharklibrary.webview.CallSharkActivity;

public class CallSharkStarter extends AsyncTask<String, Integer, String> {

    private Context context;
    private Activity activity;
    private static Context currentContext = null;
    private boolean isRecordVideoIfOperatorsAreNotAvailable;

    public CallSharkStarter(Context context, Activity activity, boolean isRecordVideoIfOperatorsAreNotAvailable) {
        this.context = context;
        this.activity = activity;
        this.isRecordVideoIfOperatorsAreNotAvailable = isRecordVideoIfOperatorsAreNotAvailable;
        currentContext = context;
    }

    public CallSharkStarter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        this.isRecordVideoIfOperatorsAreNotAvailable = false;
        currentContext = context;
    }

    public static Context getCurrentContext() {
        return currentContext;
    }

    protected String doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
            connection.setRequestProperty("Accept","*/*");
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
//            connection.setDoOutput(true); tol'ko dl9 POST
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            InputStreamReader in = new InputStreamReader(inputStream);
            BufferedReader rd = new BufferedReader(in);
            String content = "", line;
            while ((line = rd.readLine()) != null) {
                content += line + "\n";
            }
            return content;
        }catch (Exception e){
            Log.e("doInBackground",e.getMessage());
        }
        return null;
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(String result) {
        // this is executed on the main thread after the process is over
        // update your UI here
        if (result!=null){
            if (result.startsWith("0")){
                if (isRecordVideoIfOperatorsAreNotAvailable){
                    Toast.makeText(activity, "Операторов нет онлайн. Запишите видео.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, CameraActivity.class);
                    activity.startActivity(intent);
                }else {
                    Toast.makeText(activity, "Операторов нет онлайн. Повторите позже.", Toast.LENGTH_LONG).show();
                }
            }else {
                Intent intent = new Intent(context, CallSharkActivity.class);
                activity.startActivity(intent);
            }
            Log.d("onPostExecute",result);
        }

    }

}
