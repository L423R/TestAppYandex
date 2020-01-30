package ru.asntl.callsharklibrary.utilities;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

import ru.asntl.callsharklibrary.config.CallSharkConfig;

public class YandexDiskUtility extends AsyncTask<String, Integer, String> {
    private static String filePath = null;
    private static String fileHref = null;

    private static String urlRequest = "https://cloud-api.yandex.net:443/v1/disk/resources/upload?path=";



    @Override
    protected String doInBackground(String... strings) {
        String finish = null;
        try {
            File file = new File(strings[0]);
            Log.i("fileName", file.getName());
            filePath = file.getPath();
            URL url = new URL(urlRequest+file.getName());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", CallSharkConfig.getOAuthYandexToken());
            /*connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);*/
            connection.connect();
            BufferedInputStream rd = new BufferedInputStream(connection.getInputStream());
            String content = "";
            byte[] contents = new byte[1024];

            int bytesRead = 0;
            while((bytesRead = rd.read(contents)) != -1) {
                content += new String(contents, 0, bytesRead);
            }

            int responseCode = connection.getResponseCode();
            Log.i("responseCode", String.valueOf(responseCode));
            if (responseCode == 200){
                Log.i("responseCode200", "1weaawwwwwwwwwwwwwwwa");

//                ObjectMapper objectMapper = new ObjectMapper();

                JSONObject jsonObject = new JSONObject(content);
               /* YandexResponseJson yandexResponseJson = objectMapper.readValue(content,YandexResponseJson.class);
                System.out.println(yandexResponseJson.href);*/
                fileHref = jsonObject.getString("href");
//              uploadUrlRequest = yandexResponseJson.href;
/*
                MultipartUtility multipartUtility = new MultipartUtility(yandexResponseJson.href);
                multipartUtility.addFilePart("file",file);
                finish = multipartUtility.finish();*/
            }
            return String.valueOf(responseCode);
        }catch (Exception e){
            Log.d("doInBackground",e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {

        if (s.equals("200")){
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        final File file = new File(filePath);
                        MultipartUtility multipartUtility = new MultipartUtility(fileHref);
                        /*multipartUtility.addFormField("clientId", String.valueOf(CallSharkConfig.getClientId()));
                        multipartUtility.addFormField("yandexVisitorId", String.valueOf(CallSharkConfig.getYandexVisitorId()));*/
                        multipartUtility.addFilePart("file",file);
                        /*String finish = YandexDiskUtility.uploadFileToYandexDisk(file);*/
                        String finish = multipartUtility.finish();
                        /*if (finish.equals("")){
                            Looper.prepare();
                            Toast.makeText(CallSharkStarter.getCurrentContext(), "Ваше видео успешно отправлено.", Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }*/
                    } catch (Exception e) {
                       /* Looper.prepare();
                        Toast.makeText(CallSharkStarter.getCurrentContext(), "Ошибка отправки ведео.", Toast.LENGTH_LONG).show();
                        Looper.loop();*/
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
        }

    }

}
