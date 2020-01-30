package ru.asntl.testappyandex;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import ru.asntl.callsharklibrary.SelectionDialog;
import ru.asntl.callsharklibrary.config.CallSharkConfig;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onMyButtonClick(View view) {
        /*required setting*/
       /* CallSharkConfig.setCallSharkUrl("https://preprod.callshark.ru");
        CallSharkConfig.setClientId(1190);
        CallSharkConfig.setSiteId(2014);*/
        CallSharkConfig.setActivitiesClass(TestActivity.class);
        CallSharkConfig.setOAuthYandexToken("OAuth YANDEX DISK TOKEN");


        /*not required setting*/
//        CallSharkConfig.setYandexVisitorId(999);
        /*CallSharkConfig.setLang("RUS");*/ //default lang - "RU"

        /*video recording setup*/
        CallSharkConfig.setVideoDurationLimitMs(180000);
        CallSharkConfig.setAudioDurationLimitMs(10000);

        /*start*/
       /* CallSharkStarter starter = new CallSharkStarter(this,this, true);
        starter.execute(CallSharkConfig.getURLForStarter());*/

        SelectionDialog selectionDialog = new SelectionDialog();
        selectionDialog.show(getSupportFragmentManager(), "selection dialog");

    }


}
