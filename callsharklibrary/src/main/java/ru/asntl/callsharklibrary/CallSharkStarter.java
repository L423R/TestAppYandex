package ru.asntl.callsharklibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ru.asntl.callsharklibrary.activity.CallSharkActivity;
import ru.asntl.callsharklibrary.activity.CallSharkVideoCaptureActivity;

public class CallSharkStarter extends AsyncTask<String, Integer, String> {

    private Context context;
    private Activity activity;
    private boolean recordVideoIfOperatorsAreNotAvailable;

    public CallSharkStarter(Context context, Activity activity, boolean recordVideoIfOperatorsAreNotAvailable) {
        this.context = context;
        this.activity = activity;
        this.recordVideoIfOperatorsAreNotAvailable = recordVideoIfOperatorsAreNotAvailable;
    }

    public CallSharkStarter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        this.recordVideoIfOperatorsAreNotAvailable = false;
    }

    protected String doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String content = "", line;
            while ((line = rd.readLine()) != null) {
                content += line + "\n";
            }
            return content;
        }catch (Exception e){
            Log.d("doInBackground",e.getMessage());
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
                if (recordVideoIfOperatorsAreNotAvailable){
                    Toast.makeText(activity, "Операторов нет онлайн. Запишите видео.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, CallSharkVideoCaptureActivity.class);
                    activity.startActivity(intent);
                }else {
                    Toast.makeText(activity, "Операторов нет онлайн. Повторите позже.", Toast.LENGTH_SHORT).show();
                }
            }else {
                Intent intent = new Intent(context, CallSharkActivity.class);
                activity.startActivity(intent);
            }
        }
        Log.d("onPostExecute",result);
    }

}
